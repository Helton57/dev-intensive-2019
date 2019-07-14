package ru.skillbranch.devintensive.extensions

import android.app.Activity
import android.content.Context
import android.graphics.Rect
import android.view.inputmethod.InputMethodManager
import android.opengl.ETC1.getHeight
import android.R.attr.top



/**
 * *Activity.hideKeyboard
 * Необходимо реализовать extension для скрытия Software Keyboard
 *
 * Реализуй extension Activity.hideKeyboard(), скрывающую экранную клавиатуру
 */
fun Activity.hideKeyboard(){
    val inputManager:InputMethodManager = getSystemService(Context.INPUT_METHOD_SERVICE) as InputMethodManager
    inputManager.hideSoftInputFromWindow(currentFocus.windowToken, InputMethodManager.SHOW_FORCED)
}

/**
 * **Activity.isKeyboardOpen Activity.isKeyboardClosed
 * Необходимо реализовать extension для проверки, открыта или нет Software Keyboard
 *
 * Реализуй extension для проверки,
 * открыта или нет Software Keyboard с применением метода rootView.getWindowVisibleDisplayFrame(Rect())
 */
fun Activity.isKeyboardOpen() : Boolean{
    val rect = Rect()
    val rootView = this.window.decorView // this = activity
    rootView.getWindowVisibleDisplayFrame(rect)

    val visibleDisplayFrameHeight = rect.bottom - rect.top
    val screenHeight = this.windowManager.defaultDisplay.height
    return (screenHeight - visibleDisplayFrameHeight > 100)
}

fun Activity.isKeyboardClosed() : Boolean = isKeyboardOpen().not()