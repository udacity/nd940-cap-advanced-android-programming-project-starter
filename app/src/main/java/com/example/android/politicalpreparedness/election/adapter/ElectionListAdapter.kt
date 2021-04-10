package com.example.android.politicalpreparedness.election.adapter

import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.RecyclerView
import com.example.android.politicalpreparedness.data.network.models.Election

class ElectionListAdapter(private val clickListener: ElectionListener): ListAdapter<Election, ElectionViewHolder>(ElectionDiffCallback()) {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ElectionViewHolder {
        return ElectionViewHolder.from(parent)
    }



    //TODO: Bind ViewHolder
    override fun onBindViewHolder(holder: ElectionViewHolder, position: Int) {
        holder.bind(getItem(position))
    }
}

//TODO: Create ElectionViewHolder

class ElectionViewHolder(view: View): RecyclerView.ViewHolder(view) {
    fun bind(item: Election) {

    }

    companion object {
        //TODO: Add companion object to inflate ViewHolder (from)

        fun from(parent: ViewGroup): ElectionViewHolder {
            return ElectionViewHolder(parent)
        }
    }
}

//TODO: Create ElectionDiffCallback
class ElectionDiffCallback: DiffUtil.ItemCallback<Election>() {
    override fun areItemsTheSame(oldItem: Election, newItem: Election): Boolean {
        return oldItem == newItem
    }

    override fun areContentsTheSame(oldItem: Election, newItem: Election): Boolean {
        return oldItem.id == newItem.id
    }

}

//TODO: Create ElectionListener
class ElectionListener(val clickListener: (election:Election) -> Unit) {
    fun onClick(election: Election) = clickListener(election)
}