package com.tuandi.architecture.extensions

import android.app.Activity
import androidx.annotation.LayoutRes
import androidx.databinding.DataBindingUtil
import androidx.databinding.ViewDataBinding

fun <T : ViewDataBinding> Activity.setDataBindingContentView(@LayoutRes res: Int): T {
    return DataBindingUtil.setContentView(this, res)
}

//fun Activity?.addFragment(
//    @IdRes id: Int = R.id.container,
//    fragment: Fragment,
//    tag: String? = null,
//    addToBackStack: Boolean = true
//) {
//    val compatActivity = this as? AppCompatActivity ?: return
//    compatActivity.supportFragmentManager.beginTransaction()
//            .apply {
//                add(id, fragment, tag)
//                setTransition(FragmentTransaction.TRANSIT_FRAGMENT_OPEN)
//                if (addToBackStack) {
//                    addToBackStack(null)
//                }
//                commit()
//            }
//}
//
//fun Activity?.replaceFragment(
//    @IdRes id: Int = R.id.container,
//    fragment: Fragment,
//    tag: String? = null,
//    addToBackStack: Boolean = false
//) {
//    val compatActivity = this as? AppCompatActivity ?: return
//    compatActivity.supportFragmentManager.beginTransaction()
//            .apply {
//                replace(id, fragment, tag)
//                if (addToBackStack) {
//                    addToBackStack(null)
//                }
//                commit()
//            }
//}
