package com.example.android.politicalpreparedness.election.adapter

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.RecyclerView
import com.example.android.politicalpreparedness.data.network.models.Election
import com.example.android.politicalpreparedness.databinding.ItemElectionViewBinding

class ElectionListAdapter(private val clickListener: ElectionListener): ListAdapter<Election, ElectionViewHolder>(ElectionDiffCallback()) {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ElectionViewHolder {
        return ElectionViewHolder.from(ItemElectionViewBinding.inflate(LayoutInflater.from(parent.context), parent, false))
    }

    //TODO: Bind ViewHolder
    override fun onBindViewHolder(holder: ElectionViewHolder, position: Int) {
        holder.itemView.setOnClickListener { clickListener.onClick(getItem(position)) }
        holder.bind(getItem(position))
    }
}

//TODO: Create ElectionViewHolder

class ElectionViewHolder(private val binding: ItemElectionViewBinding): RecyclerView.ViewHolder(binding.root) {
    fun bind(item: Election) {
        binding.election = item
        binding.executePendingBindings()
    }

    companion object {
        //TODO: Add companion object to inflate ViewHolder (from)

        fun from(binding: ItemElectionViewBinding): ElectionViewHolder {
            return ElectionViewHolder(binding)
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