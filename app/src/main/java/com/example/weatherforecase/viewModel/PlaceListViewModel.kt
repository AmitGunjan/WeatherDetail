package com.example.weatherforecase.viewModel

import android.widget.ImageView
import androidx.fragment.app.FragmentActivity
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.bumptech.glide.Glide
import com.c2info.liveconnect.productlist.interfaces.CallBackInterface
import com.example.weatherforecase.Constant
import com.example.weatherforecase.apiCall.APICall
import com.example.weatherforecase.model.PlaceList
import com.example.weatherforecase.model.weatherDetail.WeatherDetail

class PlaceListViewModel : ViewModel(), CallBackInterface {

    var placeListResponse = MutableLiveData<PlaceList>()
    var weatherDetail = MutableLiveData<WeatherDetail>()

    fun fetchPlaceListApiCall(searchKey: String) {
        APICall.getInstance(this).callApi(
            APICall.initApiCall(APICall.baseURL).getPlaceList(searchKey, 5, Constant.apiKey), 0
        )
    }

    override fun <T> handleResponse(t: T?, resultCode: Int) {
        when (resultCode) {
            0 -> placeListResponse.postValue(t as PlaceList)
            1 -> weatherDetail.postValue(t as WeatherDetail)
        }
    }

    override fun handleError(error: Throwable, resultCode: Int) {
        var error = error.message
    }

    fun fetchPlaceWeatherDetail(placeName: String) {
        APICall.getInstance(this).callApi(
            APICall.initApiCall(APICall.baseURL).getWeatherDetail(placeName, Constant.apiKey), 1
        )

    }

    fun setImage(weatherIcon: ImageView, activity: FragmentActivity?, icon: String) {
        Glide.with(activity)
            .load("https://openweathermap.org/img/wn/${icon}@2x.png")
            .into(weatherIcon)

    }
}