package com.example.rickmorty.ui

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import androidx.navigation.NavController
import androidx.navigation.findNavController
import androidx.navigation.fragment.navArgs
import androidx.navigation.navArgs
import androidx.navigation.ui.AppBarConfiguration
import androidx.navigation.ui.setupActionBarWithNavController
import androidx.navigation.ui.setupWithNavController
import com.example.rickmorty.R
import com.example.rickmorty.databinding.ActivityMainBinding
import com.example.rickmorty.ui.details.CharacterDetailsFragmentArgs
import com.google.android.material.bottomnavigation.BottomNavigationView
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class MainActivity : AppCompatActivity() {

    lateinit var binding:ActivityMainBinding
    lateinit var navController:NavController

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityMainBinding.inflate(layoutInflater)
        //setTheme(R.style.AppTheme)

        setContentView(binding.root)


        val navView: BottomNavigationView = binding.navView

        navController = findNavController(R.id.nav_host_fragment)

        val appBarConfiguration = AppBarConfiguration(
            setOf(
                R.id.charactersFragment, R.id.favoritesFragment, R.id.episodesFragment
            )
        )
        navView.setupWithNavController(navController)
        setupActionBarWithNavController(navController, appBarConfiguration)

    }
    override fun onSupportNavigateUp(): Boolean {
        return navController.navigateUp() || super.onSupportNavigateUp()
    }
}