package com.example.rickmorty.adapter

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.RecyclerView
import com.example.rickmorty.data.entities.Character
import com.example.rickmorty.data.entities.CharacterList
import com.example.rickmorty.databinding.ItemCharacterBinding
import com.example.rickmorty.utils.CharactersDiffUtil

class CharactersRowAdapter : RecyclerView.Adapter<CharactersRowAdapter.MyViewHolder>() {

    private var characters = emptyList<Character>()

    class MyViewHolder(private val binding: ItemCharacterBinding) : RecyclerView.ViewHolder(binding.root) {

        fun bind (result: Character){
            binding.result= result
            binding.executePendingBindings()
        }

        companion object {
            fun from(parent: ViewGroup): MyViewHolder {
                val layoutInflater = LayoutInflater.from(parent.context)
                val binding = ItemCharacterBinding.inflate(layoutInflater, parent, false)
                return MyViewHolder(binding)
            }
        }

    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): MyViewHolder {
        return MyViewHolder.from(parent)
    }

    override fun onBindViewHolder(holder: MyViewHolder, position: Int) {
        val currentCharacter = characters[position]
        holder.bind(currentCharacter)
    }

    override fun getItemCount(): Int {
        return characters.size
    }

    fun setData(newData: CharacterList){
        val charactersDiffUtil =
            CharactersDiffUtil(characters, newData.results)
        val diffUtilResult = DiffUtil.calculateDiff(charactersDiffUtil)
        characters = newData.results
        diffUtilResult.dispatchUpdatesTo(this)
    }
}
