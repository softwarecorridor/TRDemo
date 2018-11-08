package com.castro.andres.trdemo.network

import android.content.Context
import android.net.Uri
import android.os.AsyncTask
import java.io.File
import java.io.FileOutputStream
import java.io.InputStream
import java.net.HttpURLConnection
import java.net.URL

class JSONDownloader(val context: Context, val listener: DoneDownloadingListener) :
    AsyncTask<String, Void, DownloadResult>() {

    override fun doInBackground(vararg params: String?): DownloadResult {

        val result = DownloadResult(false, ERROR_PATH)

        var connection: HttpURLConnection? = null

        try {
            val url = URL(params[0]);
            connection = url.openConnection() as HttpURLConnection?;
            connection?.connect()

            if (connection != null) {
                result.path = writeResultToDisk(context, url.path, connection.getInputStream())
                result.isSuccessful = true
            }
        } catch (e: Exception) {
            result.path = e.message.toString()
            result.isSuccessful = false
        } finally {
            connection?.disconnect()
        }

        return result

    }

    override fun onPostExecute(result: DownloadResult) {
        super.onPostExecute(result)

        listener.downloaded(result);
    }


    private fun writeResultToDisk(context: Context, url: String, data: InputStream): String {

        val file = getTempFile(context, url)

        if (file != null) {
            val outputStream = FileOutputStream(file)

            outputStream.use {
                data.copyTo(it);
            }

            return file.path
        }

        return ERROR_PATH
    }

    private fun getTempFile(context: Context, url: String): File? =
        Uri.parse(url)?.lastPathSegment?.let { filename ->
            File.createTempFile(filename, null, context.cacheDir)
        }

    companion object {
        val ERROR_PATH = "problemo"
    }
}