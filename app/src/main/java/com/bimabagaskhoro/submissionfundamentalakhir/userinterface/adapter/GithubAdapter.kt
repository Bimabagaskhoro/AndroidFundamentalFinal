package com.bimabagaskhoro.submissionfundamentalakhir.userinterface.adapter

import android.content.Intent
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.bimabagaskhoro.submissionfundamentalakhir.data.model.EntityUser
import com.bimabagaskhoro.submissionfundamentalakhir.databinding.ItemUserGithubBinding
import com.bimabagaskhoro.submissionfundamentalakhir.userinterface.detailactivity.DetailActivity
import com.bumptech.glide.Glide
import com.bumptech.glide.request.RequestOptions

class GithubAdapter : RecyclerView.Adapter<GithubAdapter.GithubViewHolder>() {
    private val list = ArrayList<EntityUser>()

    fun setList(entityUser: ArrayList<EntityUser>) {
        list.clear()
        list.addAll(entityUser)
        notifyDataSetChanged()
    }

    override fun onCreateViewHolder(
        parent: ViewGroup,
        viewType: Int
    ): GithubViewHolder {
        val view = ItemUserGithubBinding.inflate(LayoutInflater.from(parent.context), parent, false)
        return GithubViewHolder((view))
    }

    override fun onBindViewHolder(holder: GithubViewHolder, position: Int) {
        holder.bind(list[position])
    }

    override fun getItemCount(): Int = list.size

    class GithubViewHolder(private val binding: ItemUserGithubBinding) :
        RecyclerView.ViewHolder(binding.root) {
        fun bind(github: EntityUser) {
            binding.apply {
                Glide.with(itemView)
                    .load(github.avatar_url)
                    .apply(RequestOptions().override(55, 55))
                    .into(imgUser)
                tvUsername.text = github.login
            }
            itemView.setOnClickListener {
                val intent = Intent(itemView.context, DetailActivity::class.java)
                intent.putExtra(DetailActivity.EXTRA_USERNAME, github.login)
                intent.putExtra(DetailActivity.EXTRA_ID, github.id)
                intent.putExtra(DetailActivity.EXTRA_URL, github.avatar_url)
                itemView.context.startActivity(intent)
            }

        }

    }
}