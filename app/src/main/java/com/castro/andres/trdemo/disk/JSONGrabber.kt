package com.castro.andres.trdemo.disk

import android.os.AsyncTask
import android.util.Log
import org.json.JSONArray
import java.io.File
import java.io.IOException


class JSONGrabber(val listener: DoneGrabbingListener) : AsyncTask<String, Void, JSONArray>() {
    override fun doInBackground(vararg params: String?): JSONArray {
        return getData(params[0])
    }

    override fun onPostExecute(result: JSONArray) {
        super.onPostExecute(result)
        listener.grabbed(result);
    }

    private fun getData(path: String?): JSONArray {

        if (path == null || path == "") {
            return JSONArray()
        }
        var result = ""
        try {


            val file = File(path)
            val bufferedReader = file.bufferedReader()
            val text: List<String> = bufferedReader.readLines()
            for (line in text) {
                result += line
            }

        } catch (e: IOException) {
            return JSONArray()
        }

        return JSONArray(result);

    }

}