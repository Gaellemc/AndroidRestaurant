package com.example.androidrestaurant.Network

import com.google.gson.annotations.SerializedName
import java.io.Serializable

class Basket(
    @SerializedName("Nom") val name: String,
    @SerializedName("Quantité") val number: Int,
    @SerializedName("Total") val total: String
): Serializable

