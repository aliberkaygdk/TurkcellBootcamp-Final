package com.aliberkaygedikoglu.finalproject.model

import java.io.Serializable

data class UserLogin(
    var username: String,
    var password: String
)
data class User (
    val id: Long,
    val username: String,
    val email: String,
    val firstName: String,
    val lastName: String,
    val gender: String,
    val image: String,
    val token: String,
    val refreshToken: String
): Serializable

