package br.com.mobileti.cryptonews.bindingadapter

import android.view.ViewGroup
import androidx.databinding.BindingAdapter
import androidx.lifecycle.LiveData
import com.google.android.material.snackbar.Snackbar

@BindingAdapter("android:snackbarError")
fun setSnackbarError(viewGroup: ViewGroup, errorLiveData: LiveData<Int>) {
    errorLiveData.value?.let { error ->
        if (error != -1) {
            Snackbar.make(viewGroup, viewGroup.context.getText(error), Snackbar.LENGTH_LONG).show()
        }
    }
}