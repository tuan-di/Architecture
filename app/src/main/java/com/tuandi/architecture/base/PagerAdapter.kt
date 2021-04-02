package com.tuandi.architecture.base

import androidx.fragment.app.Fragment
import androidx.viewpager2.adapter.FragmentStateAdapter

class PagerAdapter constructor(parentFragment: Fragment, private val fragments: List<Fragment>) :
    FragmentStateAdapter(parentFragment) {
    override fun getItemCount(): Int = fragments.size

    override fun createFragment(position: Int): Fragment {
        return fragments[position]
    }
}