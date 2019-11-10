package com.petarmarijanovic.revolut.coreui.util

import android.content.Context
import android.text.Editable
import android.text.TextWatcher
import android.view.View
import android.view.inputmethod.InputMethodManager
import android.widget.EditText

fun EditText.addOnSimpleTextChangedListener(listener: (s: CharSequence?) -> Unit) =
        addTextChangedListener(object : TextWatcher {

            override fun afterTextChanged(s: Editable?) {}

            override fun beforeTextChanged(s: CharSequence?, start: Int, count: Int, after: Int) {}

            override fun onTextChanged(s: CharSequence?, start: Int, before: Int, count: Int) = listener(s)

        })

/** Clears focus from view and calls [InputMethodManager.hideSoftInputFromWindow] */
fun View.hideKeyboard() {
    clearFocus()
    (context!!.getSystemService(Context.INPUT_METHOD_SERVICE) as InputMethodManager).hideSoftInputFromWindow(windowToken, 0)
}

fun View.show(show: Boolean = true) {
    if (visibility == View.VISIBLE && show) return
    if (visibility == View.GONE && !show) return
    visibility = if (show) View.VISIBLE else View.GONE
}
