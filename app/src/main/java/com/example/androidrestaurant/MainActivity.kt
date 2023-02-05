package com.example.androidrestaurant

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import android.widget.Toast
import com.example.androidrestaurant.databinding.ActivityMainBinding

class MainActivity : AppCompatActivity() {

    lateinit var binding: ActivityMainBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)

        buttonsListener()

    }


    private fun buttonsListener() {
        binding.starterButton.setOnClickListener{
            showCategory(Category.STARTER)
        }

        binding.mainButton.setOnClickListener{
            showCategory(Category.MAIN)
        }

        binding.finishButton.setOnClickListener{
            showCategory(Category.DESSERT)
        }

    }

    private fun showCategory (category: Category) {
        val intent = Intent( this, MenuActivity::class.java)
        intent.putExtra(MenuActivity.extraKey, category)  // put Extra passer les paramètres d'une activité à une autre
        startActivity(intent)
    }
}



