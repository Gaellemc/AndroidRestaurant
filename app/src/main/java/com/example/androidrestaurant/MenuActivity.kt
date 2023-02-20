package com.example.androidrestaurant

import android.content.Intent
import android.os.Bundle
import android.util.Log
import androidx.appcompat.app.AppCompatActivity
import androidx.recyclerview.widget.LinearLayoutManager
import com.android.volley.Request
import com.android.volley.toolbox.JsonObjectRequest
import com.android.volley.toolbox.Volley
import com.example.androidrestaurant.Network.MenuResult
import com.example.androidrestaurant.Network.NetworkConstants
import com.example.androidrestaurant.databinding.ActivityMenuBinding
import com.google.gson.GsonBuilder
import org.json.JSONObject


enum class Category { STARTER, MAIN, DESSERT } //prend nombre de variable fini

class MenuActivity : AppCompatActivity() {

    companion object {                      //Permet de créer des constantes statiques accessibles partout
        val extraKey = "extraKey"   //val immuable
    }

    lateinit var binding: ActivityMenuBinding   //Variable pas initialisé dans l'initialiseur mais plus tard
    lateinit var currentCategory: Category

    override fun onCreate(savedInstanceState: Bundle?) {

        super.onCreate(savedInstanceState)
        binding = ActivityMenuBinding.inflate(layoutInflater)
        setContentView(binding.root)

        val category = intent.getSerializableExtra(extraKey) as? Category     //Category?.let //as? type optionnel

        currentCategory = category ?: Category.STARTER   //trier par catégorie (entrée-plat-dessert)

        supportActionBar?.title = categoryName()
          // ?: = valeur par défaut
        //if category == null { category = STARTER }

        makeRequest()

    }

    private fun makeRequest() {
        val queue = Volley.newRequestQueue(this)
        val params = JSONObject()
        params.put(NetworkConstants.idShopKey,1)
        val request = JsonObjectRequest(
            Request.Method.POST,
            NetworkConstants.url,
            params,
            { result ->
                // Success of request
                Log.d("request", result.toString(2))
                parseData(result.toString())
            },
            { error ->
                // Error when request
                Log.e("request", error.toString())
            }
        )
        queue.add(request)
        //showDatas()
    }

    private fun parseData(data: String) {
        val result = GsonBuilder().create().fromJson(data, MenuResult::class.java)
        val category = result.data.first {
            it.name == categoryFilterKey()
        }
        showDatas(category)
    }

    private fun showDatas(category: com.example.androidrestaurant.Network.Category) {
        binding.recyclerView.layoutManager = LinearLayoutManager(this)
        binding.recyclerView.adapter = CustomAdapter(category.items) {
            val intent = Intent( this, Activity::class.java) //Commencer une activité
            intent.putExtra(Activity.PLATE_EXTRA, it)
            startActivity(intent)
        }
    }

    private fun categoryName(): String {
        return when(currentCategory) {
            Category.STARTER -> getString(R.string.starter)
            Category.MAIN -> getString(R.string.main)
            Category.DESSERT -> getString(R.string.finish)
        }
    }

    private fun categoryFilterKey(): String {
        return when(currentCategory) {
            Category.STARTER -> getString(R.string.starter)
            Category.MAIN -> getString(R.string.main)
            Category.DESSERT -> getString(R.string.finish)
        }
    }



    override fun onStart() {
        super.onStart()
        Log.d("LifeCycle", "MenuActivity onStart")
    }

    override fun onResume() {
        super.onResume()
        Log.d("LifeCycle", "MenuActivity onResume")
    }

    override fun onPause() {
        super.onPause()
        Log.d("LifeCycle", "MenuActivity onPause")
    }

    override fun onDestroy() {
        super.onDestroy()
        Log.d("LifeCycle", "MenuActivity onDestroy")
    }
}

