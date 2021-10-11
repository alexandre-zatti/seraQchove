package com.example.seraqchove.fragments

import android.content.DialogInterface
import android.os.Bundle
import android.util.Log
import android.view.*
import androidx.fragment.app.Fragment
import androidx.activity.OnBackPressedCallback
import androidx.appcompat.app.AlertDialog
import androidx.appcompat.app.AppCompatActivity
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider
import androidx.navigation.fragment.findNavController
import androidx.navigation.fragment.navArgs
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.seraqchove.R
import com.example.seraqchove.adapters.LocationsAdapter
import com.example.seraqchove.data.viewModels.LocationViewModel
import com.example.seraqchove.data.viewModels.UserViewModel
import kotlinx.android.synthetic.main.fragment_locations.view.*
import kotlinx.coroutines.runBlocking

class LocationsFragment : Fragment() {

    private lateinit var instanceLocationViewModel: LocationViewModel
    private val args by navArgs<LocationsFragmentArgs>()

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        val view = inflater.inflate(R.layout.fragment_locations, container, false)
        val recyclerView = view.location_list
        val adapter = LocationsAdapter()

        recyclerView.adapter = adapter
        recyclerView.layoutManager = LinearLayoutManager(requireContext())

        instanceLocationViewModel = ViewModelProvider(this).get(LocationViewModel::class.java)
        instanceLocationViewModel.getLocationByUser(args.currentUser.id).observe(viewLifecycleOwner, Observer { location ->
            adapter.setData(location)
        })

        val callback = object: OnBackPressedCallback(true){
            override fun handleOnBackPressed() {
                handleLogout()
            }
        }

        view.add_location_btn.setOnClickListener {
            findNavController().navigate(R.id.action_locationsFragment_to_createLocationFragment)
        }

        requireActivity().onBackPressedDispatcher.addCallback(viewLifecycleOwner,callback)

        (activity as AppCompatActivity).supportActionBar?.setDisplayHomeAsUpEnabled(false)
        setHasOptionsMenu(true)

        return view
    }

    override fun onCreateOptionsMenu(menu: Menu, inflater: MenuInflater) {
        inflater.inflate(R.menu.logout_menu,menu)
    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        if(item.itemId == R.id.logout_menu){
            handleLogout()
        }
        return super.onOptionsItemSelected(item)
    }

    private fun handleLogout() {
        val builder = AlertDialog.Builder(requireContext())
        builder.setPositiveButton("Sim", DialogInterface.OnClickListener { _, _ ->
            val instanceUserViewModel: UserViewModel = ViewModelProvider(this).get(UserViewModel::class.java)
            instanceUserViewModel.updateUserLoggedStatus(args.currentUser.id, false)
            findNavController().navigate(LocationsFragmentDirections.actionLocationsFragmentToLoginFragment())
        })
        builder.setNegativeButton("Nao"){_, _ -> }
        builder.setTitle("Tem certeza que quer fazer logout?")
        builder.create().show()
    }

}