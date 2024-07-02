package com.aliberkaygedikoglu.finalproject.model

import java.io.Serializable

data class Category (
    val slug: String,
    val name: String,
    val url: String
):Serializable