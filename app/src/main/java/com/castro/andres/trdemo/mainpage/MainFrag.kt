package com.castro.andres.trdemo.mainpage

import android.os.Bundle
import android.support.v4.app.Fragment
import android.support.v7.widget.LinearLayoutManager
import android.support.v7.widget.RecyclerView
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import com.castro.andres.trdemo.R
import com.castro.andres.trdemo.datamanipulator.APIParser
import com.castro.andres.trdemo.disk.DoneGrabbingListener
import com.castro.andres.trdemo.disk.JSONGrabber
import org.json.JSONArray

class MainFrag : Fragment() {
    private lateinit var viewAdapter: MainAdapter
    private lateinit var viewManager: RecyclerView.LayoutManager

    val listener = object : DoneGrabbingListener {
        override fun grabbed(jsonObject: JSONArray) {
            val parser = APIParser()
            val users = parser.parse(jsonObject)

            viewAdapter.updateList(users)
        }
    }

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        JSONGrabber(listener).execute(arguments?.getString(FILE_PATH_KEY, ""))

        viewAdapter = MainAdapter(context!!, arguments?.getString(FILE_PATH_KEY, ""))
        viewManager = LinearLayoutManager(context)

        val root = inflater.inflate(R.layout.main_rv, container, false)


        root.findViewById<RecyclerView>(R.id.main_recycler_view).apply {
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

    companion object {
        val FILE_PATH_KEY = "file_path"
    }
}