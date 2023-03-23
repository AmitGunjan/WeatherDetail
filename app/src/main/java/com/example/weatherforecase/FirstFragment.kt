package com.example.weatherforecase

import android.os.Bundle
import android.os.CountDownTimer
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.lifecycle.ViewModelProvider
import com.example.weatherforecase.adapter.PlaceListAdapter
import com.example.weatherforecase.databinding.FragmentFirstBinding
import com.example.weatherforecase.viewModel.PlaceListViewModel


/**
 * A simple [Fragment] subclass as the default destination in the navigation.
 */

typealias PlaceClickListner = (placeName : String) -> Unit
class FirstFragment : Fragment() {

    private lateinit var viewModel: PlaceListViewModel
    private var _binding: FragmentFirstBinding? = null
    private val binding get() = _binding!!
    private val onPlaceClickListner : PlaceClickListner = {
        viewModel.fetchPlaceWeatherDetail(it)
    }
    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        viewModel = ViewModelProvider(this)[PlaceListViewModel::class.java]
        _binding = FragmentFirstBinding.inflate(inflater, container, false)
        return binding.root

    }

    var timer: CountDownTimer? = null
    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        binding.searchIcon.setOnClickListener(){
            viewModel.fetchPlaceListApiCall(binding.searchText.text.toString())
        }
        viewModel.placeListResponse.observe(this.viewLifecycleOwner) {
            var adapterList =
                context?.let { it1 -> PlaceListAdapter(it1, R.layout.place_list_layout, it,onPlaceClickListner) }
            binding.searchText.setAdapter(adapterList)
            adapterList?.notifyDataSetChanged()
        }
        viewModel.weatherDetail.observe(this.viewLifecycleOwner){

            viewModel.setImage(binding.weatherIcon,activity,it.weather.get(0).icon)
            binding.tvWeatherDetail1.text = it.name
            binding.tvWeatherDetail2.text = it.main.tempMax.toString()
            binding.tvWeatherDetail3.text = "Feels like " + it.main.feelsLike.toString()
            binding.tvWeatherDetail4.text = "Visibility " + it.visibility
        }

    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }
}