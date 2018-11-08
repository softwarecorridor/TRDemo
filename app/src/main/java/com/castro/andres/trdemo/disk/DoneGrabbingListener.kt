package com.castro.andres.trdemo.disk

import org.json.JSONArray

interface DoneGrabbingListener {
    fun grabbed(jsonObject: JSONArray)
}