package com.tuandi.architecture.example.ui.binding

import android.graphics.drawable.Drawable
import android.net.Uri
import android.view.View
import android.widget.ImageView
import androidx.databinding.BindingAdapter
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.bumptech.glide.request.RequestOptions
import com.google.android.material.floatingactionbutton.FloatingActionButton
import com.scwang.smart.refresh.layout.SmartRefreshLayout
import com.skydoves.whatif.whatIfNotNullAs
import timber.log.Timber

@BindingAdapter("submitList")
fun bindSubmitList(view: RecyclerView, itemList: List<Any>?) {
    view.adapter.whatIfNotNullAs<ListAdapter<Any, *>> { adapter ->
        adapter.submitList(itemList)
    }
}

@BindingAdapter("refreshing")
fun bindRefreshing(view: SmartRefreshLayout, loading: Boolean) {
    if (!loading)
        view.finishRefresh()
}

@BindingAdapter("goneUnless")
fun goneUnless(view: View, visible: Boolean) {
    view.visibility = if (visible) View.VISIBLE else View.GONE
}

@BindingAdapter("fabVisibility")
fun fabVisibility(fab: FloatingActionButton, visible: Boolean) {
    if (visible) fab.show() else fab.hide()
}

@BindingAdapter(value = ["imageUri", "placeholder"], requireAll = false)
fun imageUri(imageView: ImageView, imageUri: Uri?, placeholder: Drawable?) {
    when (imageUri) {
        null -> {
            Timber.d("Unsetting image url")
            Glide.with(imageView)
                .load(placeholder)
                .into(imageView)
        }
        else -> {
            Glide.with(imageView)
                .load(imageUri)
                .apply(RequestOptions().placeholder(placeholder))
                .into(imageView)
        }
    }
}
