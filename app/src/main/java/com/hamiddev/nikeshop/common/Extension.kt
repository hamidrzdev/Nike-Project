package com.hamiddev.nikeshop.common

import android.app.Activity
import android.content.Context
import android.graphics.Color
import android.graphics.Typeface
import android.os.Build
import android.text.Editable
import android.text.TextWatcher
import android.view.View
import android.view.WindowInsets
import android.view.WindowInsetsController
import android.view.WindowManager
import android.view.animation.Animation
import android.view.inputmethod.InputMethodManager
import android.widget.TextView
import android.widget.Toast
import androidx.annotation.IdRes
import androidx.core.content.ContextCompat
import androidx.core.view.WindowInsetsControllerCompat
import androidx.fragment.app.Fragment
import androidx.navigation.NavDirections
import androidx.navigation.fragment.findNavController
import com.google.android.material.snackbar.Snackbar
import com.google.android.material.textfield.TextInputEditText
import com.hamiddev.nikeshop.R

fun Fragment.showSnack(message: String) {
    val snack = Snackbar.make(requireView(), message, Snackbar.LENGTH_SHORT)
    snack.view.layoutDirection = View.LAYOUT_DIRECTION_RTL
    snack.show()
}

fun Fragment.showErrorSnack(message: String) {
    val snack = Snackbar.make(requireView(), message, Snackbar.LENGTH_SHORT)
    snack.view.layoutDirection = View.LAYOUT_DIRECTION_RTL
    snack.setBackgroundTint(ContextCompat.getColor(requireContext(), R.color.red))

    val snackText =
        snack.view.findViewById<TextView>(com.google.android.material.R.id.snackbar_text)

    snackText.setTextColor(ContextCompat.getColor(requireContext(), R.color.white))
    snackText.setTypeface(snackText.typeface,Typeface.BOLD)

    snack.show()
}

fun Fragment.navigate(@IdRes action: Int) {
    findNavController().navigate(action)
}

fun Fragment.navigate(direction: NavDirections) {
    findNavController().navigate(direction)
}

fun Fragment.navigateUp(){
    findNavController().navigateUp()
}

fun Fragment.hideKeyboard() {
    requireActivity().currentFocus?.let { view ->
        val imm =
            requireActivity().getSystemService(Context.INPUT_METHOD_SERVICE) as? InputMethodManager
        imm?.hideSoftInputFromWindow(view.windowToken, 0)

    }
}

fun Activity.light() {
    val insetsControllerCompat = WindowInsetsControllerCompat(window, window.decorView)
    insetsControllerCompat.isAppearanceLightStatusBars = true
}

fun Activity.dark() {
    val insetsControllerCompat = WindowInsetsControllerCompat(window, window.decorView)
    insetsControllerCompat.isAppearanceLightStatusBars = false
}


fun Activity.hideStatusBar() {
    if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.R)
        window.decorView.windowInsetsController?.hide(WindowInsets.Type.statusBars())
    else {
//        window.addFlags(WindowManager.LayoutParams.FLAG_LAYOUT_NO_LIMITS)
        window.decorView.systemUiVisibility = View.SYSTEM_UI_FLAG_LAYOUT_STABLE or View.SYSTEM_UI_FLAG_LAYOUT_FULLSCREEN
        window.statusBarColor = Color.TRANSPARENT
    }
}

fun Activity.showStatusBar() {
    if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.R)
        window.decorView.windowInsetsController?.show(WindowInsets.Type.statusBars())
    else {
//        window.clearFlags(WindowManager.LayoutParams.FLAG_LAYOUT_NO_LIMITS)
        window.decorView.systemUiVisibility = View.STATUS_BAR_VISIBLE
    }
}


fun TextInputEditText.changeListener(listener: (text: String) -> Unit) {
    addTextChangedListener(object : ChangeListener() {
        override fun onTextChanged(p0: CharSequence?, p1: Int, p2: Int, p3: Int) {
            listener(p0.toString())
        }

    })
}

abstract class ChangeListener : TextWatcher {
    override fun beforeTextChanged(p0: CharSequence?, p1: Int, p2: Int, p3: Int) {

    }

    override fun afterTextChanged(p0: Editable?) {
    }

}

fun Animation.onAnimationEnd(animEnded: (animation: Animation?) -> Unit) {
    setAnimationListener(object : Animation.AnimationListener {
        override fun onAnimationStart(p0: Animation?) {

        }

        override fun onAnimationEnd(p0: Animation?) {
            animEnded.invoke(p0)
        }

        override fun onAnimationRepeat(p0: Animation?) {

        }

    })
}