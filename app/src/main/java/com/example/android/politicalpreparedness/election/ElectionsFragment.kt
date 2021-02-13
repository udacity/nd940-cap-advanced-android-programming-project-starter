package com.example.android.politicalpreparedness.election

import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.fragment.app.Fragment
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.example.android.politicalpreparedness.databinding.FragmentElectionBinding
import com.example.android.politicalpreparedness.election.adapter.ElectionListAdapter
import com.example.android.politicalpreparedness.election.adapter.ElectionListener
import com.example.android.politicalpreparedness.network.models.Division
import com.example.android.politicalpreparedness.network.models.Election
import kotlinx.android.synthetic.main.fragment_election.*
import java.text.DateFormat
import java.text.SimpleDateFormat
import java.time.Instant
import java.util.*

class ElectionsFragment: Fragment() {

    //TODO: Declare ViewModel

    private lateinit var upcomingElectionsListAdapter: ElectionListAdapter
    private lateinit var savedElectionsListAdapter: ElectionListAdapter

    override fun onCreateView(inflater: LayoutInflater,
                              container: ViewGroup?,
                              savedInstanceState: Bundle?): View? {

        //TODO: Add ViewModel values and create ViewModel

        //TODO: Add binding values
        val binding = FragmentElectionBinding.inflate(inflater, container, false)
        binding.lifecycleOwner = this

        //TODO: Link elections to voter info

        //TODO: Initiate recycler adapters
        upcomingElectionsListAdapter = ElectionListAdapter(ElectionListener {
            Toast.makeText(requireContext(), it.toString(), Toast.LENGTH_LONG).show()
        })
        savedElectionsListAdapter = ElectionListAdapter(ElectionListener {
            Toast.makeText(requireContext(), it.toString(), Toast.LENGTH_LONG).show()
        })
        binding.upcomingElectionsRecycler.adapter = upcomingElectionsListAdapter
        binding.savedElectionsRecycler.adapter = savedElectionsListAdapter

        //TODO: Populate recycler adapters

        val division = Division("1", "BlaBla", "Bla")

        val upcomingElectionsList = mutableListOf<Election>()
        val savedElectionsList = mutableListOf<Election>()
        repeat(10) {
            upcomingElectionsList += Election(it, "VIP Test Election $it", Date.from(Instant.now()), division)
        }
        repeat(5) {
            savedElectionsList += Election(it, "VIP Test Election $it", Date.from(Instant.now()), division)
        }

        upcomingElectionsListAdapter.submitList(upcomingElectionsList)
        savedElectionsListAdapter.submitList(savedElectionsList)

        return binding.root
    }

    //TODO: Refresh adapters when fragment loads

}