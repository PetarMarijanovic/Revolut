package com.petarmarijanovic.revolut.coreui.base

import androidx.annotation.MainThread
import androidx.annotation.WorkerThread
import com.petarmarijanovic.revolut.core.util.Either
import com.petarmarijanovic.revolut.navigation.routing.Router
import com.petarmarijanovic.revolut.navigation.routing.RoutingActionsDispatcher
import io.reactivex.Completable
import io.reactivex.Flowable
import io.reactivex.FlowableTransformer
import io.reactivex.Scheduler
import io.reactivex.disposables.CompositeDisposable
import io.reactivex.disposables.Disposable
import io.reactivex.disposables.Disposables
import io.reactivex.functions.Consumer
import io.reactivex.internal.functions.Functions
import io.reactivex.processors.BehaviorProcessor
import io.reactivex.processors.FlowableProcessor
import timber.log.Timber

abstract class BasePresenter<in View, ViewState : Any, RouterType : Router> :
    ViewPresenter<View, ViewState> {

    lateinit var mainThreadScheduler: Scheduler
    lateinit var backgroundScheduler: Scheduler

    lateinit var routingActionsDispatcher: RoutingActionsDispatcher<RouterType>

    private lateinit var viewState: ViewState
    private val viewStateProcessor: FlowableProcessor<ViewState> =
        BehaviorProcessor.create<ViewState>().toSerialized()

    private val disposables: CompositeDisposable = CompositeDisposable()
    private var viewObservingDisposable: Disposable = Disposables.disposed()

    /**
     * Avoid expensive allocation because this method is run on the Main Thread
     *
     * @return Initial view state to be rendered
     */
    @MainThread
    protected abstract fun initialViewState(): ViewState

    /**
     * Called only once when a presenter is created, after dependency injection.
     *
     * Override this method to implement initial presenter setup
     */
    final override fun start() {
        pushInitialViewState()
        onStart()
    }

    private fun pushInitialViewState() {
        viewState = initialViewState()
        viewStateProcessor.onNext(viewState)
    }

    /**
     * Called only once when a presenter is created, after dependency injection and initial view state is set.
     *
     * Override this method to implement initial presenter setup and queries
     */
    protected open fun onStart() {
        // Template
    }

    final override fun viewAttached(view: View) {
        if (!viewObservingDisposable.isDisposed) {
            throw IllegalStateException("Another's view disposable is not disposed")
        }

        viewObservingDisposable = observeView(view)
    }

    /**
     * Override to observe view.
     * DO NOT keep a direct reference to the passed view
     *
     * @return Disposable to be disposed when the view is gone
     */
    protected open fun observeView(view: View): Disposable = Disposables.disposed()

    final override fun viewState(): Flowable<ViewState> =
        viewStateProcessor
            .compose(viewStateThrottle())
            .onBackpressureLatest()
            .observeOn(mainThreadScheduler)
            .subscribeOn(backgroundScheduler)

    protected open fun viewStateThrottle() = FlowableTransformer<ViewState, ViewState> { it }

    final override fun viewDetached() {
        viewObservingDisposable.dispose()
        viewObservingDisposable = Disposables.disposed()
    }

    /**
     * Called only once, when a presenter is about to be destroyed
     */
    final override fun destroy() {
        onDestroy()
        disposables.clear()
    }

    /**
     * Called only once, when a presenter is about to be destroyed.
     *
     * Override this method to clear resources used by the presenter.
     *
     * Internal disposables will be disposed after this method completes.
     */
    protected open fun onDestroy() {
        // Template
    }

    /**
     * Invoke to route to another screen
     *
     * @param routingAction RoutingAction to dispatch
     */
    protected fun dispatchRoutingAction(routingAction: (RouterType) -> Unit) =
        routingActionsDispatcher.dispatch(routingAction)

    /**
     * Invoke to route to another screen. If another routing action with the same [actionId] is already queued, the old one will be removed.
     *
     * @param actionId RoutingAction identifier
     * @param routingAction RoutingAction to dispatch
     */
    protected fun dispatchDistinctRoutingAction(
        actionId: String,
        routingAction: (RouterType) -> Unit
    ) = routingActionsDispatcher.dispatchDistinct(actionId, routingAction)

    override fun back() = dispatchRoutingAction { router -> router.goBack() }

    protected fun addDisposable(disposable: Disposable) {
        disposables.add(disposable)
    }

    /**
     * Call this method to mutate current view state and to publish the new, mutated view state via [viewState] method.
     * This method should be called from a background thread since only rendering to the UI should be on the main thread.
     *
     * @param viewStateMutator to be called on the ViewState
     */
    @WorkerThread
    protected fun mutateViewState(viewStateMutator: (ViewState) -> Unit) {
        viewStateMutator(viewState)
        viewStateProcessor.onNext(viewState)
    }

    @WorkerThread
    protected fun refreshView() =
        backgroundScheduler.scheduleDirect { viewStateProcessor.onNext(viewState) }

    protected fun <T> fromState(consumer: ViewState.() -> T): T = viewState.consumer()

    protected fun logWarn(throwable: Throwable) = Timber.w(throwable)

    protected fun logError(throwable: Throwable) = Timber.e(throwable)

    protected fun runCommand(completable: Completable) = buildCommand(completable).assemble()

    private fun buildCommand(completable: Completable) = QueryBuilder(completable)

    protected fun query(flowable: Flowable<(ViewState) -> Unit>) = buildQuery(flowable).assemble()

    private fun buildQuery(flowable: Flowable<(ViewState) -> Unit>) = QueryBuilder(flowable)

    inner class QueryBuilder {

        private val either: Either<Flowable<(ViewState) -> Unit>, Completable>

        constructor(viewStateMutator: Flowable<(ViewState) -> Unit>) {
            this.either = Either.Left(viewStateMutator)
        }

        constructor(command: Completable) {
            this.either = Either.Right(command)
        }

        fun assemble() {
            disposables.add(subscribe())
        }

        private fun subscribe() =
            either.fold({
                it.subscribeOn(backgroundScheduler)
                    .subscribe(this@BasePresenter::mutateViewState, this@BasePresenter::logError)
            }, {
                it.subscribeOn(backgroundScheduler)
                    .subscribe(Functions.EMPTY_ACTION, Consumer(this@BasePresenter::logWarn))
            })
    }
}
