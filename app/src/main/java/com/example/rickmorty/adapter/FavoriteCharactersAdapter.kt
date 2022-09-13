package com.example.rickmorty.adapter

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.navigation.findNavController
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.RecyclerView
import com.example.rickmorty.data.local.entities.FavoritesEntity
import com.example.rickmorty.databinding.FavoriteItemBinding
import com.example.rickmorty.ui.favorites.FavoritesFragmentDirections
import com.example.rickmorty.utils.CharactersDiffUtil
import dagger.hilt.android.HiltAndroidApp

class FavoriteCharactersAdapter: RecyclerView.Adapter<FavoriteCharactersAdapter.MyViewHolder>() {

    var favoriteEntityList = emptyList<FavoritesEntity>()

    class MyViewHolder(val binding: FavoriteItemBinding) :
        RecyclerView.ViewHolder(binding.root) {

        fun bind(favoritesEntity: FavoritesEntity) {
            binding.favoritesEntity = favoritesEntity
            binding.executePendingBindings()
        }

        companion object {
            fun from(parent: ViewGroup): MyViewHolder {
                val layoutInflater = LayoutInflater.from(parent.context)
                val binding = FavoriteItemBinding.inflate(layoutInflater, parent, false)
                return MyViewHolder(binding)
            }
        }

    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): MyViewHolder {
        return MyViewHolder.from(parent)
    }

    override fun onBindViewHolder(holder: MyViewHolder, position: Int) {
        val selectedFavorite = favoriteEntityList[position]
        holder.bind(selectedFavorite)

        holder.binding.favoriteRowLayout.setOnClickListener {
            val action =
                FavoritesFragmentDirections.actionFavoritesFragmentToCharacterDetailsFragment(
                    selectedFavorite.character
                )
            holder.itemView.findNavController().navigate(action)
        }
    }

    override fun getItemCount(): Int {
        return favoriteEntityList.size
    }

    fun setData(newFavoriteRecipes: List<FavoritesEntity>) {
        val favoriteRecipesDiffUtil =
            CharactersDiffUtil(favoriteEntityList, newFavoriteRecipes)
        val diffUtilResult = DiffUtil.calculateDiff(favoriteRecipesDiffUtil)
        favoriteEntityList = newFavoriteRecipes
        diffUtilResult.dispatchUpdatesTo(this)
    }

}