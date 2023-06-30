package com.asthana.radius

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import com.asthana.radius.Fragment.Home
import com.asthana.radius.databinding.ActivityMainBinding

class MainActivity : AppCompatActivity() {

        lateinit var binding: ActivityMainBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)

        val homescreen = Home()
        supportFragmentManager.beginTransaction().apply {
            replace(binding.frameFragment.id, homescreen)
            commit()
        }

    }
}