package com.example.android.politicalpreparedness.utils

import android.view.View
import android.widget.*
import androidx.core.net.toUri
import androidx.core.view.isVisible
import androidx.databinding.BindingAdapter
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.bumptech.glide.request.RequestOptions
import com.example.android.politicalpreparedness.R
import com.example.android.politicalpreparedness.data.network.models.Election
import com.example.android.politicalpreparedness.data.network.models.State
import com.example.android.politicalpreparedness.data.network.models.getCorrespondenceAddress
import com.example.android.politicalpreparedness.election.adapter.ElectionListAdapter
import com.example.android.politicalpreparedness.representative.adapter.RepresentativeListAdapter
import com.example.android.politicalpreparedness.representative.model.Representative

/**
 * When there is no data (data is null), hide the [RecyclerView], otherwise show it.
 */
@BindingAdapter("listData")
fun bindRecyclerView(recyclerView: RecyclerView, data: List<Election>?) {
    val adapter = recyclerView.adapter as ElectionListAdapter
    adapter.submitList(data ?: listOf())
}

/**
 * When there is no data (data is null), hide the [RecyclerView], otherwise show it.
 */
@BindingAdapter("representativeListData")
fun bindRecyclerViewForRepresentative(recyclerView: RecyclerView, data: List<Representative>?) {
    val adapter = recyclerView.adapter as RepresentativeListAdapter
    adapter.submitList(data ?: listOf())
}

@BindingAdapter("addressFromState")
fun getAddressFromState(textView: TextView, state: State?) {
    state?.getCorrespondenceAddress().also {
        textView.text = it ?: textView.context.getString(R.string.no_mailing_address)
    }
}

@BindingAdapter("followElection")
fun updateFollowButton(button: Button, follow: Boolean?) {
    button.setText(if (follow == true) R.string.follow_election else R.string.unfollow_election)
}

@BindingAdapter("isVisible")
fun updateVisibilityByStatus(view: View, visible: Boolean?) {
   view.isVisible = visible ?: false
}

@Suppress("UNCHECKED_CAST")
@BindingAdapter("positionByValue")
fun setItemByValue(spinner: Spinner, value: String?) {
    if (value == null) return
    val adapter = spinner.adapter as ArrayAdapter<String>
    val position = adapter.getPosition(value)
    spinner.setSelection(position)
}

/**
 * Uses the Glide library to load an image by URL into an [ImageView]
 */
@BindingAdapter("imageUrl")
fun bindImage(imgView: ImageView, imgUrl: String?) {
    imgUrl?.let {
        val imgUri = imgUrl.toUri().buildUpon().scheme("https").build()
        Glide.with(imgView.context)
                .load(imgUri)
                .apply(RequestOptions()
                        .placeholder(R.drawable.ic_profile)
                        .error(R.drawable.ic_broken_image))
                .into(imgView)
    }
}