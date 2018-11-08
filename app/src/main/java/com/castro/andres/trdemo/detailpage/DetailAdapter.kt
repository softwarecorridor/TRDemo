package com.castro.andres.trdemo.detailpage

import android.support.v7.widget.RecyclerView
import android.view.LayoutInflater
import android.view.ViewGroup
import android.widget.TextView
import com.castro.andres.trdemo.R
import com.castro.andres.trdemo.data.User

class DetailAdapter : RecyclerView.Adapter<DetailAdapter.ViewHolder>() {
    var user = User(-1)

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        val textView = LayoutInflater.from(parent.context)
            .inflate(R.layout.todo_rv_list_item, parent, false) as TextView
        return ViewHolder(textView)
    }

    override fun getItemCount(): Int {
        return user.todos.size
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        holder.view.text = user.todos[position].title
    }

    fun updateUser(item: User) {
        user = item

        notifyDataSetChanged()
    }

    class ViewHolder(val view: TextView) : RecyclerView.ViewHolder(view)
}