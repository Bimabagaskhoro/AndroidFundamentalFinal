package com.bimabagaskhoro.consumerapp.adapter

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.bimabagaskhoro.consumerapp.data.EntityUser
import com.bimabagaskhoro.consumerapp.databinding.ItemUserBinding
import com.bumptech.glide.Glide
import com.bumptech.glide.request.RequestOptions

class GithubAdapter : RecyclerView.Adapter<GithubAdapter.GithubViewHolder>() {
    private val list = ArrayList<EntityUser>()

    fun setList(entityUser: ArrayList<EntityUser>) {
        list.clear()
        list.addAll(entityUser)
        notifyDataSetChanged()
    }

    inner class GithubViewHolder(private val binding: ItemUserBinding) :
        RecyclerView.ViewHolder(binding.root) {
        fun bind(github: EntityUser) {
            binding.apply {
                Glide.with(itemView)
                    .load(github.avatar_url)
                    .apply(RequestOptions().override(55, 55))
                    .into(imgUser)
                tvUsername.text = github.login
            }
        }

    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): GithubViewHolder {
        val view = ItemUserBinding.inflate(LayoutInflater.from(parent.context), parent, false)
        return GithubViewHolder((view))
    }

    override fun onBindViewHolder(holder: GithubViewHolder, position: Int) {
        holder.bind(list[position])
    }

    override fun getItemCount(): Int = list.size
}