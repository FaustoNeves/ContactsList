package br.com.fausto.contactslist.ui.activities

import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import androidx.navigation.findNavController
import androidx.navigation.ui.setupWithNavController
import br.com.fausto.contactslist.R
import com.google.android.material.bottomnavigation.BottomNavigationView
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class MainActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        val bottomNavBar: BottomNavigationView = findViewById(R.id.bottomNavBar)
        val navController = findNavController(R.id.fragmentHost)
        bottomNavBar.setupWithNavController(navController)

    }
}