package com.castro.andres.trdemo.data

class User(val id: Int) : Comparable<User> {
    var todos = ArrayList<ToDo>()

    fun incompleteCount(): Int {
        var result = 0;
        for (todo in todos) {
            if (!todo.isCompleted) {
                result++
            }
        }

        return result
    }

    override fun compareTo(other: User): Int {
        if (this.incompleteCount() > other.incompleteCount()) {
            return 1
        }

        if (this.incompleteCount() < other.incompleteCount()) {
            return -1
        }

        return 0

    }
}