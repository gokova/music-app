package com.gokmen.musicapp.api.models

data class Result<T>(
    val status: Status,
    val data: T? = null
)
