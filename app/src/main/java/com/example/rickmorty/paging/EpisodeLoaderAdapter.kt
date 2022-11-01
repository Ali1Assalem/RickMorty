package com.example.rickmorty.paging

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ProgressBar
import android.widget.TextView
import androidx.core.view.isVisible
import androidx.paging.LoadState
import androidx.paging.LoadStateAdapter
import androidx.recyclerview.widget.RecyclerView
import com.example.rickmorty.databinding.LoaderEpisodesItemBinding

class EpisodeLoaderAdapter(private val retry: () -> Unit) : LoadStateAdapter<EpisodeLoaderAdapter.LoaderViewHolder>() {

    override fun onCreateViewHolder(parent: ViewGroup, loadState: LoadState): LoaderViewHolder {
        val binding = LoaderEpisodesItemBinding.inflate(
            LayoutInflater.from(parent.context),
            parent,
            false
        )

        return LoaderViewHolder(binding)
    }

    override fun onBindViewHolder(holder: LoaderViewHolder, loadState: LoadState) {
        holder.bind(loadState)
    }


    inner class LoaderViewHolder(private val binding: LoaderEpisodesItemBinding) : RecyclerView.ViewHolder(binding.root){

        init {
            binding.retry.setOnClickListener {
                retry.invoke()
            }
        }

        fun bind(loadState: LoadState) {
            with(binding) {
                if(loadState is LoadState.Loading ){
                    loadEpisodesShimmer.visibility = View.VISIBLE
                    loadEpisodesShimmer.showShimmer()
                    error.visibility = View.GONE
                    retry.visibility = View.GONE
                }
                if(loadState !is LoadState.Loading ){
                    loadEpisodesShimmer.visibility = View.GONE
                    error.visibility = View.VISIBLE
                    retry.visibility = View.VISIBLE
                }
            }
        }

    }
}