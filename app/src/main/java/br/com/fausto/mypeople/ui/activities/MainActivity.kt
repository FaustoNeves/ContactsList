package br.com.fausto.mypeople.ui.activities

import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import androidx.navigation.findNavController
import androidx.navigation.ui.setupWithNavController
import br.com.fausto.mypeople.R
import com.google.android.material.bottomnavigation.BottomNavigationView

//https://developer.android.com/jetpack/guide?hl=en-us

class MainActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        val bottomNavBar: BottomNavigationView = findViewById(R.id.bottomNavBar)
        val navController = findNavController(R.id.fragmentHost)
        bottomNavBar.setupWithNavController(navController)

    }

    override fun onBackPressed() {
        super.onBackPressed()
    }
}