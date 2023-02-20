package com.example.androidrestaurant

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.Toast
import com.example.androidrestaurant.Network.Plate
import com.example.androidrestaurant.databinding.ActivityBinding
import com.google.android.material.snackbar.Snackbar
import org.json.JSONObject

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

        val name = plate?.name
        binding.textView.text = name

        val ingredients = plate?.ingredients?.map { it.name }?.joinToString(",\n") ?: ""
        binding.textView3.text = ingredients

        if (plate?.images?.isNotEmpty() == true && plate?.images?.firstOrNull()?.isNotEmpty() == true) {
            plate?.let {
                binding.viewPager2.adapter = PhotoAdapter(it.images, this)
            }
        }

        val price = plate?.prices?.get(0)?.price
        binding.priceButton.text = getString(R.string.priceButton)

        var numberClick = 0
        var priceIncrement = 0
        binding.floatingActionButton2.setOnClickListener{
            numberClick++
            binding.textView4.text = numberClick.toString()
            if (price != null) {
                priceIncrement = priceIncrement.toInt() + price.toInt()
            }
            binding.priceButton.text = getString(R.string.priceButton) + priceIncrement.toString() + "€"

        }
        binding.floatingActionButton3.setOnClickListener{
            numberClick--
            if (numberClick >= 0) {
                binding.textView4.text = numberClick.toString()
                if (price != null) {
                    priceIncrement = priceIncrement.toInt() - price.toInt()
                }
                binding.priceButton.text = getString(R.string.priceButton) + priceIncrement.toString() + "€"

            }
        }

        binding.priceButton.setOnClickListener {
            val panier = JSONObject()
            panier.put("Nom", plate?.name)
            panier.put("Quantité", numberClick)
            panier.put("Total", priceIncrement)

            //Toast.makeText(this, "Panier modifié", Toast.LENGTH_LONG).show()
            Snackbar.make(it, "Panier modifié", Snackbar.LENGTH_SHORT).show()
        }

    }
}

