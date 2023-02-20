package com.example.androidrestaurant

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import com.example.androidrestaurant.Network.Plate
import com.example.androidrestaurant.databinding.ActivityBinding

class Activity : AppCompatActivity() {
    companion object {
        val PLATE_EXTRA = "PLATE_EXTRA"
    }

    lateinit var binding: ActivityBinding
    var plate: Plate? = null

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityBinding.inflate(layoutInflater)
        setContentView(binding.root)

        plate = intent.getSerializableExtra(PLATE_EXTRA) as? Plate

        val ingredients = plate?.ingredients?.map { it.name }?.joinToString(", ") ?: ""
        binding.textView.text = ingredients

        plate?.let {
            binding.viewPager2.adapter = PhotoAdapter(it.images, this)
        }
    }
}