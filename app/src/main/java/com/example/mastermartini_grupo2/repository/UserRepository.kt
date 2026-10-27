package com.example.mastermartini_grupo2.repository

import com.example.mastermartini_grupo2.model.User

class UserRepository {
    private val users = mutableListOf<User>()

    fun addUser(user: User) {
        users.add(user)
    }

    fun getUsers(): List<User> {
        return users
    }
}
