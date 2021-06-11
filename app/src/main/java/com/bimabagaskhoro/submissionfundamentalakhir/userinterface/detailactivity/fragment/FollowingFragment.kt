package com.bimabagaskhoro.submissionfundamentalakhir.userinterface.detailactivity.fragment

import android.os.Bundle
import android.view.View
import androidx.fragment.app.Fragment
import androidx.lifecycle.ViewModelProvider
import androidx.recyclerview.widget.LinearLayoutManager
import com.bimabagaskhoro.submissionfundamentalakhir.R
import com.bimabagaskhoro.submissionfundamentalakhir.databinding.FragmentFollowBinding
import com.bimabagaskhoro.submissionfundamentalakhir.userinterface.adapter.GithubAdapter
import com.bimabagaskhoro.submissionfundamentalakhir.userinterface.detailactivity.DetailActivity
import com.bimabagaskhoro.submissionfundamentalakhir.viewmodel.FollowingViewModel

class FollowingFragment : Fragment(R.layout.fragment_follow) {
    private var fragmentFollowBinding: FragmentFollowBinding? = null
    private val binding get() = fragmentFollowBinding!!
    private lateinit var followingViewModel: FollowingViewModel
    private lateinit var githubAdapter: GithubAdapter
    private lateinit var username: String

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        val args = arguments
        username = args?.getString(DetailActivity.EXTRA_USERNAME).toString()
        fragmentFollowBinding = FragmentFollowBinding.bind(view)

        githubAdapter = GithubAdapter()
        githubAdapter.notifyDataSetChanged()

        binding.apply {
            rvFollow.setHasFixedSize(true)
            rvFollow.layoutManager = LinearLayoutManager(activity)
            rvFollow.adapter = githubAdapter
        }

        showLoading(true)

        followingViewModel = ViewModelProvider(this, ViewModelProvider.NewInstanceFactory())
            .get(FollowingViewModel::class.java)
        followingViewModel.setListFollowing(username)
        followingViewModel.getListFollowing().observe(viewLifecycleOwner, {
            if (it != null) {
                githubAdapter.setList(it)
                showLoading(false)
            }
        })
    }

    override fun onDestroyView() {
        super.onDestroyView()
        fragmentFollowBinding = null
    }

    private fun showLoading(state: Boolean) {
        if (state) {
            binding.progressBar.visibility = View.VISIBLE
        } else {
            binding.progressBar.visibility = View.GONE
        }
    }
}