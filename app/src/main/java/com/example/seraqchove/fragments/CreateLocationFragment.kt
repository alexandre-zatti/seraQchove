package com.example.seraqchove.fragments

import android.database.sqlite.SQLiteException
import android.os.Bundle
import android.text.TextUtils
import android.view.*
import android.widget.ArrayAdapter
import android.widget.Toast
import androidx.fragment.app.Fragment
import androidx.appcompat.app.AppCompatActivity
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider
import androidx.navigation.fragment.findNavController
import androidx.navigation.fragment.navArgs
import com.example.seraqchove.R
import com.example.seraqchove.data.entities.Location
import com.example.seraqchove.data.viewModels.LocationViewModel
import kotlinx.android.synthetic.main.fragment_create_location.*
import kotlinx.android.synthetic.main.fragment_create_location.view.*

class CreateLocationFragment : Fragment() {
    private lateinit var instanceLocationViewModel: LocationViewModel

    private val args by navArgs<CreateLocationFragmentArgs>()

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        val view = inflater.inflate(R.layout.fragment_create_location, container, false)

        (activity as AppCompatActivity).supportActionBar?.setDisplayHomeAsUpEnabled(false)
        setHasOptionsMenu(true)

        view.add_city_btn.setOnClickListener {
            try {
                if(!TextUtils.isEmpty(city_field.text.toString())){
                    val location = Location(0,args.currentUser.id,city_field.text.toString())
                    instanceLocationViewModel.createLocation(location)
                    Toast.makeText(requireContext(), "Localidade adicionada com sucesso!", Toast.LENGTH_LONG).show()
                    val action = CreateLocationFragmentDirections.actionCreateLocationFragmentToLocationsFragment(args.currentUser)
                    findNavController().navigate(action)
                }
            }catch (e: SQLiteException){
                e.printStackTrace()
            }

        }

        return view
    }

    override fun onResume() {
        super.onResume()
        val cities = arrayListOf<String>()

        instanceLocationViewModel = ViewModelProvider(this).get(LocationViewModel::class.java)
        instanceLocationViewModel.getCountries()
        instanceLocationViewModel.countrieSnowResponse.observe(viewLifecycleOwner, Observer { response ->
            for(country in response.data){
                for(citie in country.cities){
                    cities.add(citie + ", " + country.country)
                }
            }
        })

        val arrayAdapter = ArrayAdapter(activity as AppCompatActivity,android.R.layout.simple_list_item_1,cities)
        city_field.setAdapter(arrayAdapter)
    }
}