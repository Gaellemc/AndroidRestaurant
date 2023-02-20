package com.example.androidrestaurant

import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import androidx.constraintlayout.widget.ConstraintLayout
import androidx.recyclerview.widget.RecyclerView
import com.example.androidrestaurant.Network.Plate
import com.example.androidrestaurant.Network.Price
import com.example.androidrestaurant.databinding.CellCustomBinding
import com.squareup.picasso.Callback
import com.squareup.picasso.Picasso
import java.lang.Exception

class CustomAdapter(val items: List<Plate>, val clickListener: (Plate) -> Unit): RecyclerView.Adapter<CustomAdapter.CellViewHolder>(), Callback {
    class CellViewHolder(binding: CellCustomBinding) : RecyclerView.ViewHolder(binding.root) {
        val textView: TextView = binding.textView
        val textView2: TextView = binding.textView2
        val imageView = binding.imageView
        val root: ConstraintLayout = binding.root // détecte le clique sur toute la cellule
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): CellViewHolder {
        val binding = CellCustomBinding.inflate(LayoutInflater.from(parent.context), parent, false)
        return CellViewHolder(binding)
    }

    override fun getItemCount(): Int {
        return items.count()
    }

    override fun onBindViewHolder(holder: CellViewHolder, position: Int) {
        val plate = items[position]
        holder.textView.text = plate.name
        holder.textView2.text = plate.prices[0].price + '€'
        Picasso.get().load(getThumbnail(plate)).into(holder.imageView, this)
        holder.root.setOnClickListener{
            Log.d("click", "click on ${position}")
            clickListener(plate)
        }
    }

    private fun getThumbnail(plate: Plate): String? {
        return if (plate.images.isNotEmpty() && plate.images.firstOrNull()?.isNotEmpty() == true) {
            plate.images.firstOrNull()
        } else {
            null
        }
    }

    override fun onSuccess() {
        Log.d("picasso", "success")
    }

    override fun onError(e: Exception?) {
        Log.e("picasso", e.toString())
    }
}
