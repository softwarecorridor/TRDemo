package com.castro.andres.trdemo.detailpage

import android.os.Bundle
import android.provider.SyncStateContract.Helpers.update
import android.support.v4.app.Fragment
import android.support.v7.widget.LinearLayoutManager
import android.support.v7.widget.RecyclerView
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import com.castro.andres.trdemo.R
import com.castro.andres.trdemo.data.User
import com.castro.andres.trdemo.datamanipulator.APIParser
import com.castro.andres.trdemo.disk.DoneGrabbingListener
import com.castro.andres.trdemo.disk.JSONGrabber
import com.castro.andres.trdemo.mainpage.MainFrag.Companion.FILE_PATH_KEY
import org.json.JSONArray

class DetailFrag : Fragment() {


    private lateinit var viewAdapter: DetailAdapter
    private lateinit var viewManager: RecyclerView.LayoutManager
    private var position: Int = -1

    val listener = object : DoneGrabbingListener {
        override fun grabbed(jsonObject: JSONArray) {
            val parser = APIParser()
            val users = parser.parse(jsonObject)

            update(users)
        }
    }

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {

        position = arguments!!.getInt(USER_ID_KEY)
        JSONGrabber(listener).execute(arguments?.getString(FILE_PATH_KEY, ""))

        viewAdapter = DetailAdapter()
        viewManager = LinearLayoutManager(context)

        val root = inflater.inflate(R.layout.detail_layout, container, false)

        val idTv = root.findViewById<TextView>(R.id.user_id_text)
        idTv.text = position.toString()

        root.findViewById<RecyclerView>(R.id.todolist_rv).apply {
            // use this setting to improve performance if you know that changes
            // in content do not change the layout size of the RecyclerView
            setHasFixedSize(true)

            // use a linear layout manager
            layoutManager = viewManager

            // specify an viewAdapter (see also next example)
            adapter = viewAdapter


        }

        return root
    }

    fun update(users: ArrayList<User>) {
        if (position > -1) {
            for (user: User in users) {
                if (user.id.equals(position)) {
                    viewAdapter.updateUser(user)
                    break
                }
            }
        }
    }

    companion object {
        val USER_ID_KEY = "id_key"
        val FILE_PATH_KEY = "file_path"
    }

}