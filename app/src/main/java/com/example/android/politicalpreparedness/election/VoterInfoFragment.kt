package com.example.android.politicalpreparedness.election

import android.content.Intent
import android.net.Uri
import android.os.Bundle
import android.view.*
import androidx.databinding.DataBindingUtil
import androidx.fragment.app.Fragment
import androidx.lifecycle.Observer
import com.example.android.politicalpreparedness.R
import com.example.android.politicalpreparedness.databinding.FragmentVoterInfoBinding
import org.koin.android.ext.android.inject
import org.koin.androidx.viewmodel.ext.android.viewModel

class VoterInfoFragment : Fragment() {

    private val viewModel: VoterInfoViewModel by viewModel()

    private lateinit var binding: FragmentVoterInfoBinding

    override fun onCreateView(inflater: LayoutInflater,
                              container: ViewGroup?,
                              savedInstanceState: Bundle?): View? {

        binding = DataBindingUtil.inflate(
                inflater,
                R.layout.fragment_voter_info,
                container,
                false
        )

        //TODO: Add ViewModel values and create ViewModel

        //TODO: Add binding values
        binding.lifecycleOwner = this
        binding.viewModel = viewModel

        //TODO: Populate voter info -- hide views without provided data.
        /**
        Hint: You will need to ensure proper data is provided from previous fragment.
        */
        val args = VoterInfoFragmentArgs.fromBundle(requireArguments())
        val division = args.argDivision
        val electionId = args.argElectionId

        viewModel.getVoterInfo(division, electionId)

        //TODO: Handle loading of URLs
        viewModel.urlString.observe(viewLifecycleOwner, { url ->
            url?.let {
                loadUrl(url)
                viewModel.loadUrlComplete()
            }
        })

        //TODO: Handle save button UI state
        //TODO: cont'd Handle save button clicks

        return binding.root
    }

    //TODO: Create method to load URL intents
    private fun loadUrl(url: String) {
        val intent = Intent(Intent.ACTION_VIEW)
        intent.data = Uri.parse(url)
        startActivity(intent)
    }

}