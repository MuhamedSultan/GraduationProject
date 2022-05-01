package models

import com.google.gson.annotations.SerializedName




class Pojo(var id:String,var title:String) {

    @SerializedName("image")
    private val Image: String? = null
}