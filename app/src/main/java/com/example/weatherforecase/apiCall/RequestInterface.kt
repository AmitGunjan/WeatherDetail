package com.example.weatherforecase.apiCall

import com.example.weatherforecase.Constant.Companion.appId
import com.example.weatherforecase.Constant.Companion.header
import com.example.weatherforecase.Constant.Companion.limit
import com.example.weatherforecase.Constant.Companion.searchPlace
import com.example.weatherforecase.model.PlaceList
import com.example.weatherforecase.model.weatherDetail.WeatherDetail
import retrofit2.Call
import retrofit2.http.*


interface RequestInterface {

    @Headers(header)
    @GET("geo/1.0/direct?")
    fun getPlaceList(@Query(searchPlace) idx: String, @Query(limit) size: Int, @Query(appId) appID: String):Call<PlaceList>

    @Headers(header)
    @GET("/data/2.5/weather?")
    fun getWeatherDetail(@Query(searchPlace) idx: String, @Query(appId) appID: String):Call<WeatherDetail>
}

