package com.example.android.politicalpreparedness.representative

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.android.politicalpreparedness.data.Result
import com.example.android.politicalpreparedness.data.network.models.Address
import com.example.android.politicalpreparedness.data.repository.representative.RepresentativeRepository
import com.example.android.politicalpreparedness.representative.model.Representative
import com.example.android.politicalpreparedness.utils.BaseViewModel
import com.example.android.politicalpreparedness.utils.SingleLiveEvent
import kotlinx.coroutines.launch

class RepresentativeViewModel(
    private val representativeRepository: RepresentativeRepository
): BaseViewModel() {

    //TODO: Establish live data for representatives and address
    private val _address = SingleLiveEvent<Address>()
    val address: LiveData<Address>
        get() = _address

    private val _representatives = SingleLiveEvent<List<Representative>>()
    val representatives: LiveData<List<Representative>>
        get() = _representatives

    //TODO: Create function to fetch representatives from API from a provided address
    //TODO: Create function get address from geo location
    fun getRepresentativesByAddress(address: Address) {
        viewModelScope.launch {
            showLoading.value = true
            _address.value = address
            when(val representativesResponse = representativeRepository.getRepresentatives(address.toFormattedString())) {
                is Result.Success -> {
                    /**
                     *  The following code will prove helpful in constructing a representative from the API. This code combines the two nodes of the RepresentativeResponse into a single official :

                    val (offices, officials) = getRepresentativesDeferred.await()
                    _representatives.value = offices.flatMap { office -> office.getRepresentatives(officials) }

                    Note: getRepresentatives in the above code represents the method used to fetch data from the API
                    Note: _representatives in the above code represents the established mutable live data housing representatives

                     */
                    val (offices, officials) = representativesResponse.data
                    showLoading.value = false
                    _representatives.value = offices.flatMap { office -> office.getRepresentatives(officials) }
                }
                is Result.Error -> {
                    showLoading.value = false
                    _representatives.value = listOf()
                }
            }
        }
    }

    //TODO: Create function to get address from individual fields

}
