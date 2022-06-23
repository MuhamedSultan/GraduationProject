package com.example.we_care

import api.Api
import android.os.Bundle
import android.widget.EditText
import android.widget.TextView
import androidx.appcompat.app.AppCompatActivity
import com.example.we_care.databinding.ActivityIdentifyGetDataBinding
import models.GetChiledData
import models.ParentModel
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory




class IdentifyGetData : AppCompatActivity() {

      var binding : ActivityIdentifyGetDataBinding?=null
    var list :ArrayList<ParentModel>?=null

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding= ActivityIdentifyGetDataBinding.inflate(layoutInflater)
        setContentView(binding!!.root)
        list=ArrayList()
            var retrofit =
                Retrofit.Builder()
                    .baseUrl("https://wecare5.000webhostapp.com/api/")
                    // .baseUrl("https://jsonplaceholder.typicode.com/")
                    .addConverterFactory(GsonConverterFactory.create()).build()
        var getPersonName = Face_recognition.Val
            var apiInterface = retrofit.create(Api::class.java)
        var call: Call<List<ParentModel>> = apiInterface.getHomeless(getPersonName)

        //   var call: Call<ParentModel> = apiInterface.getHomeless(get)
            //   var call: Call<ParentModel> = apiInterface.getChildData()

            call.enqueue(object : Callback<List<ParentModel>> {
                override fun onResponse(
                    call: Call<List<ParentModel>>,
                    response: Response<List<ParentModel>>
                ) {
                 // if (getPersonName==response.body()?.get(0)?.name){
                    //  }else{
                //      binding!!.namePostData.text="Nothing"

                //  }

//                  namePostData.setText(response.body()?.id.toString())
//                    addressPostData.setText(response.body()?.title)
                }

                override fun onFailure(call: Call<List<ParentModel>>, t: Throwable) {
                    binding!!.addressPostData.text=t.message
                }

            })
        }
    
}