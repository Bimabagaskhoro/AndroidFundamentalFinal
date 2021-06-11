package com.bimabagaskhoro.submissionfundamentalakhir.userinterface.adapter

import android.content.Context
import android.os.Bundle
import androidx.annotation.StringRes
import androidx.fragment.app.Fragment
import androidx.fragment.app.FragmentManager
import androidx.fragment.app.FragmentPagerAdapter
import com.bimabagaskhoro.submissionfundamentalakhir.R
import com.bimabagaskhoro.submissionfundamentalakhir.userinterface.detailactivity.fragment.FollowersFragment
import com.bimabagaskhoro.submissionfundamentalakhir.userinterface.detailactivity.fragment.FollowingFragment

class SectionPagerAdapter(private val mContext: Context, fragmentManager: FragmentManager, data: Bundle) : FragmentPagerAdapter(fragmentManager, BEHAVIOR_RESUME_ONLY_CURRENT_FRAGMENT) {
    private var fragmentBundle: Bundle = data

    init {
        fragmentBundle = data
    }

    @StringRes
    private val tabName = intArrayOf(R.string.following, R.string.followers)

    override fun getCount(): Int = 2

    override fun getItem(position: Int): Fragment {
        var fragment: Fragment? = null
        when (position) {
            0 -> fragment = FollowingFragment()
            1 -> fragment = FollowersFragment()
        }

        fragment?.arguments = this.fragmentBundle
        return fragment as Fragment
    }

    override fun getPageTitle(position: Int): CharSequence? {
        return mContext.resources.getString(tabName[position])
    }
}