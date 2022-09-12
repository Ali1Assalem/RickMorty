package com.example.rickmorty.ui.favorites

import android.graphics.Canvas
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.lifecycle.ViewModelProvider
import androidx.recyclerview.widget.ItemTouchHelper
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.example.rickmorty.R
import com.example.rickmorty.adapter.CharactersRowAdapter
import com.example.rickmorty.adapter.FavoriteCharactersAdapter
import com.example.rickmorty.databinding.FragmentCharactersBinding
import com.example.rickmorty.databinding.FragmentFavoritesBinding
import com.example.rickmorty.ui.characters.CharactersViewModel
import dagger.hilt.android.AndroidEntryPoint
import it.xabaras.android.recyclerview.swipedecorator.RecyclerViewSwipeDecorator

@AndroidEntryPoint
class FavoritesFragment : Fragment() {

    private lateinit var charactersViewModel: CharactersViewModel
    private val mAdapter by lazy { FavoriteCharactersAdapter() }

    private lateinit var binding: FragmentFavoritesBinding

    private val swipeCallback = object : ItemTouchHelper.SimpleCallback(0, ItemTouchHelper.LEFT) {
        override fun onMove(
            recyclerView: RecyclerView,
            viewHolder: RecyclerView.ViewHolder,
            target: RecyclerView.ViewHolder
        ): Boolean {
            return true
        }
        override fun onSwiped(viewHolder: RecyclerView.ViewHolder, direction: Int) {
            val character = mAdapter.currentListItem()
            charactersViewModel.deleteFavorite(character)
        }


        override fun onChildDraw(
            c: Canvas,
            recyclerView: RecyclerView,
            viewHolder: RecyclerView.ViewHolder,
            dX: Float,
            dY: Float,
            actionState: Int,
            isCurrentlyActive: Boolean
        ) {

            RecyclerViewSwipeDecorator.Builder(
                c,
                recyclerView,
                viewHolder,
                dX,
                dY,
                actionState,
                isCurrentlyActive
            )
                .addSwipeLeftLabel(getString(R.string.swipe_delete))
                .addSwipeLeftActionIcon(R.drawable.ic_delete_24)
                .setSwipeLeftActionIconTint(resources.getColor(R.color.white))
                .setSwipeLeftLabelColor(resources.getColor(R.color.white))
                .addSwipeLeftBackgroundColor(resources.getColor(R.color.red))
                .create()
                .decorate()

            super.onChildDraw(c, recyclerView, viewHolder, dX, dY, actionState, isCurrentlyActive)
        }

    }


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        charactersViewModel = ViewModelProvider(requireActivity()).get(CharactersViewModel::class.java)
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        binding = FragmentFavoritesBinding.inflate(layoutInflater)

        binding.lifecycleOwner = this
        binding.characterViewModel = charactersViewModel

        setHasOptionsMenu(true)

        charactersViewModel.readFavorites.observe(viewLifecycleOwner,{favoriteEntity ->
            mAdapter.setData(favoriteEntity)
            hideShimmerEffect()
        })
        setupRecyclerView()

        return binding.root
    }



    private fun setupRecyclerView() {
        binding.recyclerviewFavorites.adapter = mAdapter
        binding.recyclerviewFavorites.layoutManager = LinearLayoutManager(requireContext())
        ItemTouchHelper(swipeCallback).attachToRecyclerView(binding.recyclerviewFavorites)
        showShimmerEffect()
    }

    private fun showShimmerEffect() {
        binding.recyclerviewFavorites.showShimmer()
    }

    private fun hideShimmerEffect() {
        binding.recyclerviewFavorites.hideShimmer()
    }

}