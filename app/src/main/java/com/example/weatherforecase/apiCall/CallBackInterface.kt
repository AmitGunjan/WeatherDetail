package com.c2info.liveconnect.productlist.interfaces


interface CallBackInterface {
    fun<T> handleResponse(t:T?,resultCode:Int)
    fun handleError(error: Throwable,resultCode: Int)
}