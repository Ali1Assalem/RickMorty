package com.example.rickmorty.ui.details

import android.os.Bundle
import android.util.Log
import android.view.*
import androidx.core.content.ContextCompat
import androidx.fragment.app.Fragment
import androidx.fragment.app.FragmentManager
import androidx.fragment.app.FragmentTransaction
import androidx.fragment.app.viewModels
import androidx.navigation.fragment.findNavController
import androidx.navigation.fragment.navArgs
import coil.load
import com.example.rickmorty.R
import com.example.rickmorty.data.entities.Character
import com.example.rickmorty.data.local.entities.FavoritesEntity
import com.example.rickmorty.databinding.FragmentCharacterDetailsBinding
import com.example.rickmorty.ui.characters.CharactersViewModel
import com.google.android.material.snackbar.Snackbar
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class CharacterDetailsFragment : Fragment() {

    private val args : CharacterDetailsFragmentArgs by navArgs()

    lateinit var binding: FragmentCharacterDetailsBinding
    private val charaViewModel: CharactersViewModel by viewModels()

    private lateinit var menuItem: MenuItem
    private var savedFavoriteId = 0
    private var favoriteSaved = false

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment

        binding =  FragmentCharacterDetailsBinding.inflate(layoutInflater)

        setHasOptionsMenu(true)

        val myByndle : Character = args.result

        setUpDetail(myByndle)

        return binding.root
    }

    fun setUpDetail(character : Character){
        binding.image.load(character.image){
            error(R.drawable.ic_error_placeholder)
            placeholder(R.drawable.ic_error_placeholder)
        }
        binding.name.text = character.name
        binding.status.text = character.status
        binding.type.text = character.type
        binding.species.text = character.species
        binding.gender.text = character.gender
        binding.created.text = character.created
    }

    override fun onCreateOptionsMenu(menu: Menu, inflater: MenuInflater) {
        inflater.inflate(R.menu.details_menu,menu)
        menuItem = menu.findItem(R.id.save_to_favorites_menu)

        checkSaveFavorite(menuItem)
        super.onCreateOptionsMenu(menu, inflater)
    }

    private fun checkSaveFavorite(menuItem: MenuItem) {
        charaViewModel.readFavorites.observe(this,{ favoritesEntity ->
            try {
                for (savedFavorite in favoritesEntity){
                    if (savedFavorite.character.id == args.result.id){
                        changeMenuItemColor(menuItem,R.color.yellow)
                        favoriteSaved = true
                        savedFavoriteId = savedFavorite.id
                    }
                }
            }catch (e:Exception){
                Log.d("CharacterDetailsFragment",e.message.toString())
            }
        })
    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        when(item.itemId){
            R.id.save_to_favorites_menu ->{
                if (favoriteSaved) deleteFavorite(item)
                else saveToFavorites(item)

                return true
            }
        }
        return super.onOptionsItemSelected(item)
    }

    private fun saveToFavorites(item: MenuItem) {
        val favoritesEntity = FavoritesEntity(
            0,
            args.result
        )
        charaViewModel.insertFavorite(favoritesEntity)
        changeMenuItemColor(item , R.color.yellow)
        showSnackbar("Added To Favorite ;)")

        favoriteSaved = true
    }

    private fun deleteFavorite(item:MenuItem){
        val favoritesEntity = FavoritesEntity(
            savedFavoriteId
            ,args.result
        )
        charaViewModel.deleteFavorite(favoritesEntity)
        changeMenuItemColor(item , R.color.white)
        showSnackbar("Removed From Favorite :(")
        favoriteSaved = false
    }

    private fun showSnackbar(message: String) {
        Snackbar.make(
            binding.characterCl,
            message,
            Snackbar.LENGTH_LONG
        ).setAction("Okay"){}
            .show()
    }

    private fun changeMenuItemColor(item: MenuItem, color: Int) {
        item.icon?.setTint(ContextCompat.getColor(requireActivity(),color))
    }

    override fun onDestroy() {
        super.onDestroy()
        changeMenuItemColor(menuItem , R.color.white)
    }



}