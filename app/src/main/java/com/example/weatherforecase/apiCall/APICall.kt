package com.example.weatherforecase.apiCall

import com.c2info.liveconnect.productlist.interfaces.CallBackInterface
import com.google.gson.GsonBuilder
import com.jakewharton.retrofit2.adapter.rxjava2.RxJava2CallAdapterFactory
import okhttp3.OkHttpClient
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import retrofit2.converter.scalars.ScalarsConverterFactory
import java.util.concurrent.TimeUnit

class APICall {

    companion object {
        var callBackInterface: CallBackInterface? = null
        const val baseURL="https://api.openweathermap.org/"
        private var apiCall: APICall? = null

        fun getInstance(callBackInterface: CallBackInterface?): APICall {
            this.callBackInterface = callBackInterface
            if (apiCall == null) {
                apiCall = APICall()
            }
            return apiCall!!
        }

        fun initApiCall(baseUrl: String): RequestInterface {
            val gson = GsonBuilder()
                .setLenient()
                .create()
            val okHttpClient = OkHttpClient.Builder()
                .connectTimeout(30, TimeUnit.SECONDS)
                .readTimeout(30, TimeUnit.SECONDS)
                .writeTimeout(15, TimeUnit.SECONDS)
                .build()

            val retrofit = Retrofit.Builder()
                .baseUrl(baseUrl)
                .client(okHttpClient)
                .addCallAdapterFactory(RxJava2CallAdapterFactory.create())
                .addConverterFactory(ScalarsConverterFactory.create())
                .addConverterFactory(GsonConverterFactory.create(gson))
                .build()
            return retrofit.create(RequestInterface::class.java)
        }

    }
    fun <T> callApi(t: Call<T>, resulCode: Int) {
        t.enqueue(object : Callback<T> {
            override fun onResponse(call: Call<T>?, response: Response<T>?) {
                try {
                    callBackInterface!!.handleResponse(response?.body(), resulCode)
                } catch (e: Exception) {
                    callBackInterface!!.handleError(Throwable(), resulCode)
                }

            }

            override fun onFailure(call: Call<T>?, t: Throwable?) {
                try {
                    callBackInterface!!.handleError(t!!, resulCode)
                } catch (e: Exception) {
                }
            }
        })
    }

}