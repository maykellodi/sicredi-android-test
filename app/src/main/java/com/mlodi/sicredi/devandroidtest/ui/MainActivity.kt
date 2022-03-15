package com.mlodi.sicredi.devandroidtest.ui

import android.os.Bundle
import android.view.MenuItem
import androidx.appcompat.app.AppCompatActivity
import androidx.navigation.fragment.NavHostFragment
import androidx.navigation.ui.setupActionBarWithNavController
import com.mlodi.sicredi.devandroidtest.R

class MainActivity : AppCompatActivity() {
    private lateinit var navHostFragment: NavHostFragment

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        navHostFragment = (supportFragmentManager.findFragmentById(R.id.nav_host) as NavHostFragment)
        setupActionBarWithNavController(navHostFragment.navController)
    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        navHostFragment.navController.navigateUp()
        return super.onOptionsItemSelected(item)
    }
}