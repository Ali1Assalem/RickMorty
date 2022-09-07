package com.example.rickmorty.utils

import androidx.recyclerview.widget.DiffUtil
import com.example.rickmorty.data.entities.Character

class CharactersDiffUtil(
    private val oldList: List<Character>,
    private val newList: List<Character>
): DiffUtil.Callback() {
    override fun getOldListSize(): Int {
        return oldList.size
    }

    override fun getNewListSize(): Int {
        return newList.size
    }

    override fun areItemsTheSame(oldItemPosition: Int, newItemPosition: Int): Boolean {
        return oldList[oldItemPosition] === newList[newItemPosition]
    }

    override fun areContentsTheSame(oldItemPosition: Int, newItemPosition: Int): Boolean {
        return oldList[oldItemPosition] == newList[newItemPosition]
    }
}