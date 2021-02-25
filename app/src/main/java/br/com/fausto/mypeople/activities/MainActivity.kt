package br.com.fausto.mypeople.activities

import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import br.com.fausto.mypeople.R

//https://developer.android.com/jetpack/guide?hl=en-us

class MainActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
    }
}