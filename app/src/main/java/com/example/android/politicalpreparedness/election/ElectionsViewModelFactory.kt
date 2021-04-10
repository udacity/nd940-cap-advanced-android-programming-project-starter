package com.example.android.politicalpreparedness.election

import android.app.Application
import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import com.example.android.politicalpreparedness.data.repository.ElectionRepository

//TODO: Create Factory to generate ElectionViewModel with provided election datasource
@Suppress("UNCHECKED_CAST")
class ElectionsViewModelFactory(private val repository: ElectionRepository, private val application: Application): ViewModelProvider.Factory {
    override fun <T : ViewModel?> create(modelClass: Class<T>): T {
        if (modelClass.isAssignableFrom(ElectionsViewModel::class.java)) {
            return ElectionsViewModel(repository, application) as T
        }
        throw IllegalArgumentException("Unknown ViewModel class")
    }

}