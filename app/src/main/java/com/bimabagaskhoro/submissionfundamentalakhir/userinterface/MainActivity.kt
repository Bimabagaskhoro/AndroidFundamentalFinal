package com.bimabagaskhoro.submissionfundamentalakhir.userinterface

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.KeyEvent
import android.view.Menu
import android.view.MenuItem
import android.view.View
import android.view.animation.Animation
import android.view.animation.AnimationUtils
import androidx.lifecycle.ViewModelProvider
import androidx.recyclerview.widget.LinearLayoutManager
import com.bimabagaskhoro.submissionfundamentalakhir.R
import com.bimabagaskhoro.submissionfundamentalakhir.databinding.ActivityMainBinding
import com.bimabagaskhoro.submissionfundamentalakhir.userinterface.adapter.GithubAdapter
import com.bimabagaskhoro.submissionfundamentalakhir.userinterface.favorite.FavoriteActivity
import com.bimabagaskhoro.submissionfundamentalakhir.userinterface.setting.SettingActivity
import com.bimabagaskhoro.submissionfundamentalakhir.viewmodel.MainViewModel

class MainActivity : AppCompatActivity() {

    private lateinit var mainBinding: ActivityMainBinding
    private lateinit var mainViewModel: MainViewModel
    private lateinit var adapter: GithubAdapter

    private val rotateOpen: Animation by lazy { AnimationUtils.loadAnimation(this, R.anim.rotate_open_anim) }
    private val rotateClose: Animation by lazy { AnimationUtils.loadAnimation(this, R.anim.rotate_close_anim) }
    private val fromBottom: Animation by lazy { AnimationUtils.loadAnimation(this, R.anim.from_botton_anim) }
    private val toBottom: Animation by lazy { AnimationUtils.loadAnimation(this, R.anim.to_botton_anim) }

    private var clicked = false

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        mainBinding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(mainBinding.root)

        val actionbar = supportActionBar
        actionbar!!.title = getString(R.string.home)

        adapter = GithubAdapter()
        adapter.notifyDataSetChanged()

        mainViewModel = ViewModelProvider(
            this,
            ViewModelProvider.NewInstanceFactory()
        ).get(MainViewModel::class.java)

        mainBinding.apply {
            rvGithubUser.layoutManager = LinearLayoutManager(this@MainActivity)
            rvGithubUser.setHasFixedSize(true)
            rvGithubUser.adapter = adapter

            btnSearch.setOnClickListener {
                searchUsers()
            }

            edtSearch.setOnKeyListener { _, keyCode, event ->
                if (event.action == KeyEvent.ACTION_DOWN && keyCode == KeyEvent.KEYCODE_ENTER) {
                    searchUsers()
                    return@setOnKeyListener true
                }
                return@setOnKeyListener false
            }

            floatingActionButton.setOnClickListener {
                onAddButtomClicked()
            }

            floatingButtonFavorite.setOnClickListener {
                val intent = Intent(this@MainActivity, FavoriteActivity::class.java)
                startActivity(intent)
            }

            floatingButtonAbout.setOnClickListener {
                val intent = Intent(this@MainActivity, AboutActivity::class.java)
                startActivity(intent)
            }
        }

        mainViewModel.getSearchUsers().observe(this, {
            if (it != null) {
                adapter.setList(it)
                showLoading(false)
            }
        })
    }

    private fun onAddButtomClicked() {
        setVisibility(clicked)
        setAnimation(clicked)
        clicked = !clicked
    }

    private fun setVisibility(clicked: Boolean) {
        if(!clicked){
            mainBinding.apply {
                floatingButtonFavorite.visibility = View.VISIBLE
                floatingButtonAbout.visibility = View.VISIBLE
            }
        } else {
            mainBinding.apply {
                floatingButtonFavorite.visibility = View.INVISIBLE
                floatingButtonAbout.visibility = View.INVISIBLE
            }
        }
    }

    private fun setAnimation(clicked: Boolean) {
        if (!clicked){
            mainBinding.apply {
                floatingButtonFavorite.startAnimation(fromBottom)
                floatingButtonAbout.startAnimation(fromBottom)
                floatingActionButton.startAnimation(rotateOpen)
            }
        } else {
            mainBinding.apply {
                floatingButtonFavorite.startAnimation(toBottom)
                floatingButtonAbout.startAnimation(toBottom)
                floatingActionButton.startAnimation(rotateClose)
            }
        }
    }

    private fun searchUsers() {
        mainBinding.apply {
            val search = edtSearch.text.toString()
            if (search.isEmpty()) return
            showLoading(true)
            mainViewModel.setSearchUsers(search)
        }
    }

    private fun showLoading(state: Boolean) {
        if (state) {
            mainBinding.apply {
                progressBar.visibility = View.VISIBLE
                imgNull.visibility = View.VISIBLE
                tvNull.visibility = View.VISIBLE
                rvGithubUser.visibility = View.INVISIBLE
            }
        } else {
            mainBinding.apply {
                progressBar.visibility = View.INVISIBLE
                imgNull.visibility = View.INVISIBLE
                tvNull.visibility = View.INVISIBLE
                rvGithubUser.visibility = View.VISIBLE
            }
        }
    }

    override fun onCreateOptionsMenu(menu: Menu?): Boolean {
        menuInflater.inflate(R.menu.menu, menu)
        return super.onCreateOptionsMenu(menu)
    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        when (item.itemId) {
            R.id.settings -> {
                val alarm = Intent(this, SettingActivity::class.java)
                startActivity(alarm)
            }
        }
        return super.onOptionsItemSelected(item)
    }
}