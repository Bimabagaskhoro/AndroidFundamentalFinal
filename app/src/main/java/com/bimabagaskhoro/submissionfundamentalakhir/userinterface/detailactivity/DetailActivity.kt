package com.bimabagaskhoro.submissionfundamentalakhir.userinterface.detailactivity

import android.annotation.SuppressLint
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.Toast
import androidx.lifecycle.ViewModelProvider
import com.bimabagaskhoro.submissionfundamentalakhir.R
import com.bimabagaskhoro.submissionfundamentalakhir.databinding.ActivityDetailBinding
import com.bimabagaskhoro.submissionfundamentalakhir.userinterface.adapter.SectionPagerAdapter
import com.bimabagaskhoro.submissionfundamentalakhir.viewmodel.DetailViewModel
import com.bumptech.glide.Glide
import com.bumptech.glide.request.RequestOptions
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext

class DetailActivity : AppCompatActivity() {

    companion object {
        const val EXTRA_USERNAME = "extra_username"
        const val EXTRA_ID = "extra_id"
        const val EXTRA_URL = "extra_url"
    }

    private lateinit var detailBinding: ActivityDetailBinding
    private lateinit var detailViewModel: DetailViewModel

    @SuppressLint("SetTextI18n")
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        detailBinding = ActivityDetailBinding.inflate(layoutInflater)
        setContentView(detailBinding.root)

        val username = intent.getStringExtra(EXTRA_USERNAME)
        val id = intent.getIntExtra(EXTRA_ID, 0)
        val avatarUrl = intent.getStringExtra(EXTRA_URL)

        val bundle = Bundle()
        bundle.putString(EXTRA_USERNAME, username)

        val actionbar = supportActionBar
        actionbar!!.title = username

        detailViewModel = ViewModelProvider(this).get(DetailViewModel::class.java)

        if (username != null) {
            detailViewModel.setUserDetail(username)
        }

        detailViewModel.getUserDetail().observe(this, {
            if (it != null) {
                detailBinding.apply {
                    Glide.with(this@DetailActivity)
                        .load(it.avatar_url)
                        .apply(RequestOptions().override(55, 55))
                        .into(imgUserDetail)
                    tvName.text = it.name
                    tvBio.text = it.bio
                    tvLocation.text = it.location
                    tvCompany.text = it.company
                    tvFollowingNumber.text = "${it.followers}Following"
                    tvFollowersNumber.text = "${it.followers}Followers"
                }
            }
        })

        var checkeds = false
        CoroutineScope(Dispatchers.IO).launch {
            val count = detailViewModel.checkUser(id)
            withContext(Dispatchers.Main) {
                if (count != null) {
                    if (count > 0) {
                        detailBinding.toggleFav.isChecked = true
                        checkeds = true
                    } else {
                        detailBinding.toggleFav.isChecked = false
                        checkeds = false
                    }
                }
            }
        }

        detailBinding.toggleFav.setOnClickListener {
            checkeds = !checkeds
            if (checkeds) {
                detailViewModel.addFavorite(username.toString(), id, avatarUrl.toString())
                Toast.makeText(
                    applicationContext,
                    getString(R.string.Add_toFavorite),
                    Toast.LENGTH_SHORT
                ).show()
            } else {
                detailViewModel.removeFavorite(id)
                Toast.makeText(
                    applicationContext,
                    getString(R.string.Remove_fromFavorite),
                    Toast.LENGTH_SHORT
                )
                    .show()
            }
            detailBinding.toggleFav.isChecked = checkeds
        }

        val sectionPagerAdapter = SectionPagerAdapter(this, supportFragmentManager, bundle)
        detailBinding.apply {
            viewPager.adapter = sectionPagerAdapter
            tabMode.setupWithViewPager(viewPager)
        }
    }
}