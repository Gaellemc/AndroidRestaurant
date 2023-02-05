package com.example.androidrestaurant.Network

import com.google.gson.annotations.SerializedName
import java.io.Serializable

class MenuResult(@SerializedName("data") val data: List<Category>): Serializable {   //Va piocher la variable data dans le json (serialized)

}

