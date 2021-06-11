package com.bimabagaskhoro.consumerapp

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import androidx.lifecycle.ViewModelProvider
import androidx.recyclerview.widget.LinearLayoutManager
import com.bimabagaskhoro.consumerapp.adapter.GithubAdapter
import com.bimabagaskhoro.consumerapp.databinding.ActivityMainBinding
import com.bimabagaskhoro.consumerapp.viewmodel.FavoriteViewModel

class MainActivity : AppCompatActivity() {

    private lateinit var binding: ActivityMainBinding
    private lateinit var githubAdapter: GithubAdapter
    private lateinit var favoriteViewModel: FavoriteViewModel

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)

        githubAdapter = GithubAdapter()
        githubAdapter.notifyDataSetChanged()

        binding.apply {
            rvFavorite.setHasFixedSize(true)
            rvFavorite.layoutManager = LinearLayoutManager(this@MainActivity)
            rvFavorite.adapter = githubAdapter
        }

        favoriteViewModel = ViewModelProvider(this).get(FavoriteViewModel::class.java)
        favoriteViewModel.setFavoriteUser(this)
        favoriteViewModel.getFavoriteUser().observe(this, {
            if (it != null) {
                githubAdapter.setList(it)
            }
        })
    }
}