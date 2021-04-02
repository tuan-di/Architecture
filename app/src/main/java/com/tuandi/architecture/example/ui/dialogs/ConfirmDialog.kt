package com.tuandi.architecture.example.ui.dialogs

import android.app.Dialog
import android.os.Bundle
import androidx.annotation.NonNull
import androidx.annotation.StringRes
import androidx.fragment.app.DialogFragment
import androidx.fragment.app.FragmentActivity
import com.afollestad.materialdialogs.MaterialDialog
import com.afollestad.materialdialogs.callbacks.onDismiss

class ConfirmDialog(private val titleRes: Int, private val contentRes: Int) : DialogFragment() {
    companion object {
        private const val TAG = "ConfirmDialog"

        fun show(activity: FragmentActivity, @StringRes title: Int, @StringRes content: Int) =
            ConfirmDialog(title, content).show(
                activity.supportFragmentManager,
                TAG
            )
    }

    @NonNull
    override fun onCreateDialog(savedInstanceState: Bundle?): Dialog {
        return MaterialDialog(requireContext()).show {
            title(titleRes)
            message(contentRes)
            positiveButton(text = "positiveButton")
            onDismiss {
                // Make sure the DialogFragment dismisses as well
                this@ConfirmDialog.dismiss()
            }
        }
    }
}
