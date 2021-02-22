package com.example.android.politicalpreparedness.utils

import android.util.Log
import android.view.View
import android.widget.TextView
import androidx.databinding.BindingAdapter
import com.example.android.politicalpreparedness.network.models.Election
import com.example.android.politicalpreparedness.network.models.State
import org.w3c.dom.Text

@BindingAdapter("electionDateText")
fun TextView.setElectionDate(item: Election) {
    item.let {
        text = item.electionDay.toSimpleString()
    }
}

@BindingAdapter("fieldVisibility")
fun TextView.setVisibility(isVisible: Boolean) {
    visibility = if(isVisible) View.VISIBLE else View.INVISIBLE
}