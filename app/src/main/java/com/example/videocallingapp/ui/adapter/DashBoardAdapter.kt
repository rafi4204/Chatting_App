package com.example.videocallingapp.ui.adapter

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.example.videocallingapp.databinding.UserItemLayoutBinding
import com.example.videocallingapp.model.User

class DashBoardAdapter(private val list: List<User>, val onClickListener: (User) -> Unit) :
    RecyclerView.Adapter<DashBoardAdapter.CustomViewHolder>() {
    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): CustomViewHolder {
        val binding =
            UserItemLayoutBinding.inflate(LayoutInflater.from(parent.context), parent, false)
        return CustomViewHolder(binding)
    }

    override fun onBindViewHolder(holder: CustomViewHolder, position: Int) {
        holder.bind(list[position])

    }

    override fun getItemCount() = list.size

    inner class CustomViewHolder(private val bind: UserItemLayoutBinding) :
        RecyclerView.ViewHolder(bind.root) {
        fun bind(user: User) {
            bind.tvUserName.text = user.name
            bind.rootLayout.setOnClickListener { onClickListener(user) }
        }
    }
}