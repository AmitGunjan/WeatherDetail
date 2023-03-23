package com.example.weatherforecase.model.weatherDetail


import com.google.gson.annotations.SerializedName

data class Clouds(
    @SerializedName("all")
    val all: Int
)