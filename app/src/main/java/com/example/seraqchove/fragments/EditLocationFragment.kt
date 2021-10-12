package com.example.seraqchove.fragments

import android.content.DialogInterface
import android.database.sqlite.SQLiteException
import android.os.Bundle
import android.text.TextUtils
import android.util.Log
import android.view.*
import android.widget.ArrayAdapter
import android.widget.Toast
import androidx.appcompat.app.AlertDialog
import androidx.appcompat.app.AppCompatActivity
import androidx.fragment.app.Fragment
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider
import androidx.navigation.fragment.findNavController
import androidx.navigation.fragment.navArgs
import com.example.seraqchove.R
import com.example.seraqchove.data.viewModels.LocationViewModel
import com.example.seraqchove.data.viewModels.UserViewModel
import kotlinx.android.synthetic.main.fragment_edit_location.*
import kotlinx.android.synthetic.main.fragment_edit_location.view.*

class EditLocationFragment : Fragment() {

    private lateinit var instanceLocationViewModel: LocationViewModel
    private val args by navArgs<EditLocationFragmentArgs>()

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        val view = inflater.inflate(R.layout.fragment_edit_location, container, false)

        view.city_edit_field.setText(args.currentLocation)

        view.edit_city_btn.setOnClickListener {
            try {
                if(!TextUtils.isEmpty(city_edit_field.toString())){
                    instanceLocationViewModel.updateLocation(args.currentUser.id,args.currentLocation,view.city_edit_field.text.toString())
                    val action = EditLocationFragmentDirections.actionEditLocationFragmentToLocationsFragment(args.currentUser)
                    Toast.makeText(requireContext(), "Local alterado com sucesso!", Toast.LENGTH_LONG).show()
                    findNavController().navigate(action)
                }
            }catch (e: SQLiteException){
                e.printStackTrace()
            }
        }

        (activity as AppCompatActivity).supportActionBar?.setDisplayHomeAsUpEnabled(false)
        setHasOptionsMenu(true)

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
        city_edit_field.setAdapter(arrayAdapter)
    }

    override fun onCreateOptionsMenu(menu: Menu, inflater: MenuInflater) {
        inflater.inflate(R.menu.delete_menu,menu)
    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        if(item.itemId == R.id.delete_menu){
            handleDelete()
        }
        return super.onOptionsItemSelected(item)
    }

    private fun handleDelete() {
        try {
            val builder = AlertDialog.Builder(requireContext())
            builder.setPositiveButton("Sim", DialogInterface.OnClickListener { _, _ ->
                Log.d("teste",args.currentLocation)
                instanceLocationViewModel.deleteLocation(args.currentUser.id,args.currentLocation)
                val action = EditLocationFragmentDirections.actionEditLocationFragmentToLocationsFragment(args.currentUser)
                Toast.makeText(requireContext(), "Local deletado com sucesso!", Toast.LENGTH_LONG).show()
                findNavController().navigate(action)
            })
            builder.setNegativeButton("Nao"){_, _ -> }
            builder.setTitle("Tem certeza que deseja excluir o local?")
            builder.create().show()

        }catch (e: SQLiteException){
            e.printStackTrace()
        }
    }
}