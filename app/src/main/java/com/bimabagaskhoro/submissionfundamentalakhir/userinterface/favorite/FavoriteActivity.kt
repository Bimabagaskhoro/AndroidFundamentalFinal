package com.bimabagaskhoro.submissionfundamentalakhir.userinterface.favorite

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import androidx.lifecycle.ViewModelProvider
import androidx.recyclerview.widget.LinearLayoutManager
import com.bimabagaskhoro.submissionfundamentalakhir.R
import com.bimabagaskhoro.submissionfundamentalakhir.data.model.EntityUser
import com.bimabagaskhoro.submissionfundamentalakhir.data.local.EntityFavoriteUser
import com.bimabagaskhoro.submissionfundamentalakhir.databinding.ActivityFavoriteBinding
import com.bimabagaskhoro.submissionfundamentalakhir.userinterface.adapter.GithubAdapter
import com.bimabagaskhoro.submissionfundamentalakhir.viewmodel.FavoriteViewModel

class FavoriteActivity : AppCompatActivity() {

    private lateinit var activityFavoriteBinding: ActivityFavoriteBinding
    private lateinit var githubAdapter: GithubAdapter
    private lateinit var favoriteViewModel: FavoriteViewModel

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        activityFavoriteBinding = ActivityFavoriteBinding.inflate(layoutInflater)
        setContentView(activityFavoriteBinding.root)

        val actionbar = supportActionBar
        actionbar!!.title = getString(R.string.favorite)

        githubAdapter = GithubAdapter()
        githubAdapter.notifyDataSetChanged()

        favoriteViewModel = ViewModelProvider(this).get(FavoriteViewModel::class.java)

        activityFavoriteBinding.apply {
            rvFavorite.setHasFixedSize(true)
            rvFavorite.layoutManager = LinearLayoutManager(this@FavoriteActivity)
            rvFavorite.adapter = githubAdapter
        }

        favoriteViewModel.getFavoriteUser()?.observe(this, {
            if (it != null) {
                val list = mapList(it)
                githubAdapter.setList(list)
            }
        })
    }

    private fun mapList(it: List<EntityFavoriteUser>): ArrayList<EntityUser> {
        val listUser = ArrayList<EntityUser>()
        for (user in it) {
            val userMapped = EntityUser(
                user.login,
                user.id,
                user.avatar_url
            )
            listUser.add(userMapped)
        }
        return listUser
    }
}