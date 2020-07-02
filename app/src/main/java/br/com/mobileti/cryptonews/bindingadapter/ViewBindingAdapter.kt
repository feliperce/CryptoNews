package br.com.mobileti.cryptonews.bindingadapter

import android.view.View
import androidx.databinding.BindingAdapter

@BindingAdapter("android:visibilityBoolean")
fun setVisibilityBoolean(view: View, isVisible: Boolean) {
    if (isVisible) {
        view.visibility = View.VISIBLE
    } else {
        view.visibility = View.INVISIBLE
    }
}