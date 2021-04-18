package com.example.android.politicalpreparedness.representative

import android.Manifest
import android.annotation.SuppressLint
import android.content.Context
import android.content.pm.PackageManager
import android.location.Geocoder
import android.location.Location
import android.os.Bundle
import android.util.Log
import android.view.*
import android.view.inputmethod.InputMethodManager
import androidx.core.app.ActivityCompat
import androidx.core.content.ContextCompat
import androidx.databinding.DataBindingUtil
import androidx.fragment.app.Fragment
import com.example.android.politicalpreparedness.R
import com.example.android.politicalpreparedness.databinding.FragmentRepresentativeBinding
import com.example.android.politicalpreparedness.data.network.models.Address
import com.example.android.politicalpreparedness.representative.adapter.RepresentativeListAdapter
import com.google.android.gms.location.FusedLocationProviderClient
import com.google.android.gms.location.LocationServices
import com.google.android.material.snackbar.Snackbar
import org.koin.android.ext.android.bind
import org.koin.androidx.viewmodel.ext.android.viewModel
import java.util.Locale

private const val REQUEST_LOCATION_PERMISSION = 1

class DetailFragment : Fragment() {

    private val viewModel: RepresentativeViewModel by viewModel()
    private lateinit var binding: FragmentRepresentativeBinding
    private var fusedLocationClient: FusedLocationProviderClient? = null

    companion object {
        //TODO: Add Constant for Location request
    }

    //TODO: Declare ViewModel

    override fun onCreateView(inflater: LayoutInflater,
                              container: ViewGroup?,
                              savedInstanceState: Bundle?): View {

        //TODO: Establish bindings

        //TODO: Define and assign Representative adapter

        //TODO: Populate Representative adapter

        //TODO: Establish button listeners for field and location search

        binding = DataBindingUtil.inflate(
                inflater,
                R.layout.fragment_representative,
                container,
                false
        )

        binding.lifecycleOwner = this
        binding.viewModel = viewModel

        val adapter = RepresentativeListAdapter()
        binding.representativeRecyclerView.adapter = adapter

        binding.buttonLocation.setOnClickListener {
            hideKeyboard()

            checkLocationPermissions()
        }

        binding.buttonSearch.setOnClickListener {
            hideKeyboard()

            val address = getAddressFromEditTexts()
            viewModel.getRepresentativesByAddress(address)
        }

        viewModel.showSnackBarInt.observe(viewLifecycleOwner, {
            Snackbar.make(binding.root, it, Snackbar.LENGTH_LONG).show()
        })

        return binding.root
    }

    override fun onRequestPermissionsResult(
            requestCode: Int,
            permissions: Array<out String>,
            grantResults: IntArray
    ) {
        if (requestCode == REQUEST_LOCATION_PERMISSION) {
            if (grantResults.isNotEmpty() && (grantResults[0] == PackageManager.PERMISSION_GRANTED)) {
                getLocation()
            }
        }
    }

    private fun checkLocationPermissions(): Boolean {
        return if (isPermissionGranted()) {
            getLocation()
            true
        } else {
            requestPermissions(
                    arrayOf(Manifest.permission.ACCESS_FINE_LOCATION),
                    REQUEST_LOCATION_PERMISSION
            )
            false
        }
    }

    private fun isPermissionGranted() : Boolean {
        return ContextCompat.checkSelfPermission(
                requireContext(),
                Manifest.permission.ACCESS_FINE_LOCATION) == PackageManager.PERMISSION_GRANTED
    }

    @SuppressLint("MissingPermission")
    private fun getLocation() {
        //TODO: Get location from LocationServices
        //TODO: The geoCodeLocation method is a helper function to change the lat/long location to a human readable street address
        fusedLocationClient = LocationServices.getFusedLocationProviderClient(requireContext())

        fusedLocationClient?.lastLocation?.addOnCompleteListener(requireActivity()) { task ->
            if (task.isSuccessful && task.result != null) {
                Log.i("TAG", "IS SUCCESSFUL")
                val address = geoCodeLocation(task.result)
                viewModel.getRepresentativesByAddress(address)
            }
            else {
                Snackbar.make(binding.root, R.string.no_address_found, Snackbar.LENGTH_LONG).show()
                Log.w("TAG", "getLastLocation:exception: ${ task.exception}")
            }
        }
    }

    private fun geoCodeLocation(location: Location): Address {
        val geocoder = Geocoder(context, Locale.getDefault())
        return geocoder.getFromLocation(location.latitude, location.longitude, 1)
                .map { address ->
                    Address(
                            address.thoroughfare ?: "",
                            address.subThoroughfare ?: "",
                            address.locality ?: "",
                            address.adminArea ?: "",
                            address.postalCode ?: "")
                }
                .first()
    }

    private fun hideKeyboard() {
        val imm = activity?.getSystemService(Context.INPUT_METHOD_SERVICE) as InputMethodManager
        imm.hideSoftInputFromWindow(requireView().windowToken, 0)
    }

    private fun getAddressFromEditTexts(): Address {
        return Address(
                line1 = binding.addressLine1.text.toString(),
                line2 = binding.addressLine2.text.toString(),
                city = binding.city.text.toString(),
                state = binding.state.selectedItem as String,
                zip = binding.zip.text.toString()
        )
    }

}