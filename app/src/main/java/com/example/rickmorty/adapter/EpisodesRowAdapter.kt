package com.example.rickmorty.adapter

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.RecyclerView
import com.example.rickmorty.data.entities.Character
import com.example.rickmorty.data.entities.CharacterList
import com.example.rickmorty.data.entities.Episode
import com.example.rickmorty.data.entities.EpisodesList
import com.example.rickmorty.databinding.EpisodeItemRowBinding
import com.example.rickmorty.databinding.ItemCharacterBinding
import com.example.rickmorty.utils.CharactersDiffUtil

class EpisodesRowAdapter : RecyclerView.Adapter<EpisodesRowAdapter.MyViewHolder>() {

    private var episodes = emptyList<Episode>()

    class MyViewHolder(private val binding: EpisodeItemRowBinding) : RecyclerView.ViewHolder(binding.root) {

        fun bind (result: Episode){
            binding.result= result
            binding.executePendingBindings()
        }

        companion object {
            fun from(parent: ViewGroup): MyViewHolder {
                val layoutInflater = LayoutInflater.from(parent.context)
                val binding = EpisodeItemRowBinding.inflate(layoutInflater, parent, false)
                return MyViewHolder(binding)
            }
        }

    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): MyViewHolder {
        return MyViewHolder.from(parent)
    }

    override fun onBindViewHolder(holder: MyViewHolder, position: Int) {
        val currentEpisode = episodes[position]
        holder.bind(currentEpisode)


    }

    override fun getItemCount(): Int {
        return episodes.size
    }

    fun setData(newData: EpisodesList){
        val charactersDiffUtil =
            CharactersDiffUtil(episodes, newData.results)
        val diffUtilResult = DiffUtil.calculateDiff(charactersDiffUtil)
        episodes = newData.results
        diffUtilResult.dispatchUpdatesTo(this)
    }

}