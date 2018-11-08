package com.castro.andres.trdemo.mainpage

import android.content.Context
import android.os.Bundle
import android.support.v7.app.AppCompatActivity
import android.support.v7.widget.RecyclerView
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import com.castro.andres.trdemo.R
import com.castro.andres.trdemo.data.User
import com.castro.andres.trdemo.detailpage.DetailFrag


class MainAdapter(val context: Context, val filePath: String?) : RecyclerView.Adapter<MainAdapter.ViewHolder>() {

    var sortedData = ArrayList<User>()


    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        holder.idTV.text = "ID: " + sortedData[position].id
        holder.countTv.text = sortedData[position].incompleteCount().toString()

        holder.view.setOnClickListener {
            replaceFragTransaction(
                context as AppCompatActivity,
                sortedData[position].id,
                filePath
            )
        }
    }

    override fun getItemCount(): Int {
        return sortedData.size
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        val view = LayoutInflater.from(parent.context).inflate(R.layout.main_rv_list_item, parent, false)
        return ViewHolder(view)
    }

    class ViewHolder(val view: View) : RecyclerView.ViewHolder(view) {
        val idTV = view.findViewById<TextView>(R.id.id_field)
        val countTv = view.findViewById<TextView>(R.id.count_field)
    }

    private fun replaceFragTransaction(context: AppCompatActivity, position: Int, filepath: String?) {
        val newFragment = DetailFrag()
        val args = Bundle()
        args.putInt(DetailFrag.USER_ID_KEY, position)
        args.putString(DetailFrag.FILE_PATH_KEY, filepath)
        newFragment.arguments = args

        val transaction = context.supportFragmentManager.beginTransaction()

        transaction.replace(R.id.fragment_container, newFragment)
        transaction.addToBackStack(null)

        transaction.commit()
    }

    fun updateList(list: ArrayList<User>) {
        if (list.size > 0) {
            sortedData = ArrayList(list.sortedDescending())

            notifyDataSetChanged()
        }
    }

}