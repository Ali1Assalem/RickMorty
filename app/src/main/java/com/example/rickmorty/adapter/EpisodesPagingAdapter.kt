package com.example.rickmorty.adapter

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.paging.PagingDataAdapter
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.RecyclerView
import com.example.rickmorty.data.entities.Episode
import com.example.rickmorty.data.entities.EpisodesList
import com.example.rickmorty.databinding.EpisodeItemRowBinding
import com.example.rickmorty.utils.CharactersDiffUtil

class EpisodesPagingAdapter : PagingDataAdapter<Episode, EpisodesPagingAdapter.EpisodeViewHolder>(COMPARATOR) {

    class EpisodeViewHolder(private val binding: EpisodeItemRowBinding) :
        RecyclerView.ViewHolder(binding.root) {
        fun bind(result: Episode) {
            binding.apply {
                binding.result = result
                binding.executePendingBindings()
            }
        }
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): EpisodeViewHolder {
        val binding =
            EpisodeItemRowBinding.inflate(LayoutInflater.from(parent.context), parent, false)

        return EpisodeViewHolder(binding)
    }


    override fun onBindViewHolder(holder: EpisodeViewHolder, position: Int) {
        val currentItem = getItem(position)

        if (currentItem != null) {
            holder.bind(currentItem)
        }
    }


    companion object {
        private val COMPARATOR = object : DiffUtil.ItemCallback<Episode>() {
            override fun areItemsTheSame(oldItem: Episode, newItem: Episode): Boolean {
                return oldItem.id == newItem.id
            }

            override fun areContentsTheSame(oldItem: Episode, newItem: Episode): Boolean {
                return oldItem == newItem
            }
        }
    }

}