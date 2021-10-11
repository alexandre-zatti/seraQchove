package com.example.seraqchove.fragments

import android.os.Bundle
import android.util.Log
import android.view.*
import androidx.fragment.app.Fragment
import androidx.appcompat.app.AppCompatActivity
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider
import com.example.seraqchove.R
import com.example.seraqchove.data.viewModels.LocationViewModel

class CreateLocationFragment : Fragment() {
    private lateinit var instanceLocationViewModel: LocationViewModel

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        val view = inflater.inflate(R.layout.fragment_create_location, container, false)

        (activity as AppCompatActivity).supportActionBar?.setDisplayHomeAsUpEnabled(false)
        setHasOptionsMenu(true)

        instanceLocationViewModel = ViewModelProvider(this).get(LocationViewModel::class.java)
        instanceLocationViewModel.getCountries()
        instanceLocationViewModel.countrieSnowResponse.observe(viewLifecycleOwner, Observer { response ->
            for(country in response.data){
                Log.d("testePais", country.country)
            }

        })

        return view
    }

}