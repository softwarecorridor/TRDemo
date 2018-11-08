package com.castro.andres.trdemo.datamanipulator

import com.castro.andres.trdemo.data.ToDo
import com.castro.andres.trdemo.data.User
import org.json.JSONArray

class APIParser() {

    fun parse(json: JSONArray): ArrayList<User> {
        val result = ArrayList<User>()


        /*
         {
    "userId": 1,
    "id": 1,
    "title": "delectus aut autem",
    "completed": false
  }
         */

        for (i in 0..(json.length() - 1)) {
            val jsonObject = json.getJSONObject(i)
            val userId = jsonObject.getInt(USER_ID)
            val isComplete = jsonObject.getBoolean(COMPLETED)
            val title = jsonObject.getString(TITLE)

            val toDo = ToDo(isComplete, title)

            var added = false
            for (user: User in result) {
                if (user.id.equals(userId)) {
                    user.todos.add(toDo)
                    added = true
                    break;
                }
            }

            if (!added) {
                val user = User(userId)
                user.todos.add(toDo)
                result.add(user)
            }
        }
        return result
    }

    companion object {
        private val USER_ID = "userId"
        private val TITLE = "title"
        private val COMPLETED = "completed"
    }

}