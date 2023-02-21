package com.example.androidrestaurant

import android.annotation.SuppressLint
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import com.example.androidrestaurant.Network.Basket
import com.example.androidrestaurant.databinding.ActivityShoppingBasketBinding
import org.json.JSONObject

lateinit var binding: ActivityShoppingBasketBinding

class ShoppingBasket : AppCompatActivity() {
    @SuppressLint("AppCompatMethod")
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityShoppingBasketBinding.inflate(layoutInflater)
        setContentView(binding.root)

        supportActionBar?.title = "Panier"
        //binding.PanierNom.text = Basket.name
    }
}

