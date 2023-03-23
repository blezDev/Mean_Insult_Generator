package com.blez.evil_insilt.util

import android.app.Activity
import android.view.View
import android.view.inputmethod.InputMethodManager
import androidx.appcompat.app.AppCompatActivity
import com.google.android.material.snackbar.Snackbar

fun Activity.snakeBar(text: String, view: View) {
    Snackbar.make(
        view ,
        text,
        Snackbar.LENGTH_LONG
    ).show()
}

fun Activity.hideKeyboard(root : View){
    val windowToken = root.windowToken
    val imm =   getSystemService(AppCompatActivity.INPUT_METHOD_SERVICE)  as InputMethodManager // input Method Manager
    windowToken?.let {
        imm.hideSoftInputFromWindow(it,0)
    } ?: return
}



