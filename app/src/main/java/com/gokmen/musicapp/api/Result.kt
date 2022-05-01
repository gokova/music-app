package com.gokmen.musicapp.api

data class Result<T>(
    val status: Status,
    val data: T? = null
)
