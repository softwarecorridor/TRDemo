package com.castro.andres.trdemo

import android.app.Activity
import android.content.Context
import android.os.Bundle
import android.support.v7.app.AppCompatActivity
import android.util.Log
import android.view.View
import com.castro.andres.trdemo.R
import com.castro.andres.trdemo.detailpage.DetailFrag
import com.castro.andres.trdemo.emptypage.EmptyFrag
import com.castro.andres.trdemo.mainpage.MainFrag
import com.castro.andres.trdemo.network.DoneDownloadingListener
import com.castro.andres.trdemo.network.DownloadResult
import com.castro.andres.trdemo.network.JSONDownloader

class MainActivity : AppCompatActivity() {


    val downloadPath = "http://jsonplaceholder.typicode.com/todos"

    val downloadListener = object : DoneDownloadingListener {
        override fun downloaded(result: DownloadResult) {

            if (result.isSuccessful) {
                displayToDoFragment(result.path)
            } else {
                Thread.sleep(1000)
                JSONDownloader(baseContext, this).execute(downloadPath)
            }
        }
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)


        if (findViewById<View>(R.id.fragment_container) != null) {
            if (savedInstanceState != null) {
                return
            }

            val firstFragment = EmptyFrag()
            supportFragmentManager.beginTransaction().add(R.id.fragment_container, firstFragment, "main_frag").commit()
        }

        startDownload()
    }

    private fun startDownload() {
        JSONDownloader(baseContext, downloadListener).execute(downloadPath)
    }

    private fun displayToDoFragment( filename: String) {

        val newFragment = MainFrag()
        val args = Bundle()
        args.putString(MainFrag.FILE_PATH_KEY, filename)
        newFragment.arguments = args

        supportFragmentManager.beginTransaction().replace(R.id.fragment_container, newFragment, "main_frag").commit()
    }
}
