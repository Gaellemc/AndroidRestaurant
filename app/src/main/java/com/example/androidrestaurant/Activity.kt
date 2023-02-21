package com.example.androidrestaurant

import android.annotation.SuppressLint
import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import android.view.Menu
import android.view.MenuItem
import android.widget.Toast
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.androidrestaurant.Network.MenuResult
import com.example.androidrestaurant.Network.Plate
import com.example.androidrestaurant.databinding.ActivityBinding
import com.google.android.material.snackbar.Snackbar
import com.google.gson.GsonBuilder
import org.json.JSONObject
import java.io.File

class Activity : AppCompatActivity() {
    companion object {
        val PLATE_EXTRA = "PLATE_EXTRA"
    }

    lateinit var binding: ActivityBinding
    var plate: Plate? = null

    @SuppressLint("SetTextI18n")
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityBinding.inflate(layoutInflater)
        setContentView(binding.root)

        plate = intent.getSerializableExtra(PLATE_EXTRA) as? Plate

        supportActionBar?.title = plate?.name

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
                priceIncrement += price.toInt()
            }
            binding.priceButton.text = getString(R.string.priceButton) + priceIncrement.toString() + "€"

        }
        binding.floatingActionButton3.setOnClickListener{
            numberClick--
            if (numberClick >= 0) {
                binding.textView4.text = numberClick.toString()
                if (price != null) {
                    priceIncrement -= price.toInt()
                }
                binding.priceButton.text = getString(R.string.priceButton) + priceIncrement.toString() + "€"

            }
        }

        binding.priceButton.setOnClickListener {
            var panier = JSONObject()
            panier.put("Nom", plate?.name)
            panier.put("Quantité", numberClick)
            panier.put("Total", priceIncrement)

            val basket = GsonBuilder().setPrettyPrinting().create()
            val basketString: String = basket.toJson(panier)
            //File("basket.json").writeText(basketString)



            //Toast.makeText(this, "Panier modifié", Toast.LENGTH_LONG).show()
            Snackbar.make(it, "Panier modifié", Snackbar.LENGTH_SHORT).show()


        }


    }

    override fun onCreateOptionsMenu(menu: Menu?): Boolean {
        menuInflater.inflate(R.menu.menu_main,menu)
        return true
    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        when(item.itemId){
            R.id.action_panier->{
                Log.d("request", "clicked")
                val intent = Intent( this, ShoppingBasket::class.java)
                startActivity(intent)
            }
        }

        return super.onOptionsItemSelected(item)
    }



}

