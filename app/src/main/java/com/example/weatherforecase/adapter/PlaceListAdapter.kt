package com.example.weatherforecase.adapter

/*
class PlaceListAdapter {
}*/


import android.app.Activity
import android.content.Context
import android.view.View
import android.view.ViewGroup
import android.widget.ArrayAdapter
import android.widget.TextView
import com.example.weatherforecase.PlaceClickListner
import com.example.weatherforecase.R
import com.example.weatherforecase.model.PlaceList
import com.example.weatherforecase.model.PlaceListItem


class PlaceListAdapter(
    val mContext: Context,
    val resId: Int,
    var data: PlaceList,
    val onPlaceClickListner: PlaceClickListner
) :
    ArrayAdapter<PlaceListItem>(mContext, resId, data) {

    override fun getCount(): Int {
        return data.size
    }

    override fun getItem(position: Int): PlaceListItem? {
        return data.get(position)
    }

    override fun getView(position: Int, convertView: View?, parent: ViewGroup): View {
        var convertView = convertView
        if (convertView == null) {
            val inflater = (mContext as Activity).layoutInflater
            convertView = inflater.inflate(resId, parent, false)
        }
        val name = data.get(position)
        val placeName = convertView?.findViewById<View>(R.id.tv_place_name) as TextView
        placeName.text = name.name
        placeName.setOnClickListener(){
            onPlaceClickListner(name.name)
        }
        return convertView
    }
}