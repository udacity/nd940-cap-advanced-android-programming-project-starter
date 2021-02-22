package com.example.android.politicalpreparedness.election

import android.content.Intent
import android.net.Uri
import android.os.Bundle
import android.view.*
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import androidx.fragment.app.Fragment
import androidx.lifecycle.ViewModelProvider
import androidx.navigation.fragment.navArgs
import com.example.android.politicalpreparedness.R
import com.example.android.politicalpreparedness.database.ElectionDatabase
import com.example.android.politicalpreparedness.databinding.FragmentVoterInfoBinding
import com.example.android.politicalpreparedness.repository.Repository

class VoterInfoFragment : Fragment() {
    private lateinit var voterInfoViewModel: VoterInfoViewModel
    private val args by navArgs<VoterInfoFragmentArgs>()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        (requireActivity() as AppCompatActivity).supportActionBar!!.title = args.argElection.name
    }

    override fun onCreateView(inflater: LayoutInflater,
                              container: ViewGroup?,
                              savedInstanceState: Bundle?): View? {

        val election = args.argElection

        val database = ElectionDatabase.getInstance(requireContext())
        val repository = Repository(database)

        val voterInfoViewModelFactory = VoterInfoViewModelFactory(repository, getString(R.string.follow_election), getString(R.string.unfollow_election), election)
        voterInfoViewModel = ViewModelProvider(this, voterInfoViewModelFactory).get(VoterInfoViewModel::class.java)

        val binding = FragmentVoterInfoBinding.inflate(inflater, container, false)
        binding.lifecycleOwner = this
        binding.voterInfoViewModel = voterInfoViewModel
        binding.election = election

        voterInfoViewModel.errorOnFetchingNetworkData.observe(viewLifecycleOwner, {
            if(it) {
                Toast.makeText(
                        activity,
                        R.string.network_error,
                        Toast.LENGTH_LONG
                ).show()
                voterInfoViewModel.displayNetworkErrorComplete()
            }
        })

        voterInfoViewModel.ballotInfoUrl.observe(viewLifecycleOwner, { url ->
            url?.let {
                startActivityWithUrl(url)
                voterInfoViewModel.onOpenBallotInfoUrlComplete()
            }
        })

        voterInfoViewModel.votingLocationFinderUrl.observe(viewLifecycleOwner, { url ->
            url?.let {
                startActivityWithUrl(url)
                voterInfoViewModel.onOpenVotingLocationsUrlComplete()
            }
        })

        voterInfoViewModel.electionInfoUrl.observe(viewLifecycleOwner, { url ->
            url?.let {
                startActivityWithUrl(url)
                voterInfoViewModel.onOpenElectionInformationUrlComplete()
            }
        })

        return binding.root
    }

    private fun startActivityWithUrl(url: String?) {
        val uri = Uri.parse(url)
        val intent = Intent(Intent.ACTION_VIEW, uri)
        startActivity(intent)
    }
}