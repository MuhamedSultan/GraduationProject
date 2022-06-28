package com.example.we_care

import api.Api
import android.os.Bundle
import android.util.Log
import android.widget.EditText
import android.widget.TextView
import androidx.appcompat.app.AppCompatActivity
import api.APIUtils
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
    var fileService: Api? = null

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding= ActivityIdentifyGetDataBinding.inflate(layoutInflater)
        setContentView(binding!!.root)

//            var retrofit =
//                Retrofit.Builder()
//                    .baseUrl("https://wecare5.000webhostapp.com/api/")
//                    // .baseUrl("https://jsonplaceholder.typicode.com/")
//                    .addConverterFactory(GsonConverterFactory.create()).build()

        var getPersonName = Face_recognition.Val
          //  var apiInterface = retrofit.create(Api::class.java)

        fileService=APIUtils.getFileService()
        val call: Call<List<ParentModel>> = fileService!!.getHomeless(getPersonName)

        //   var call: Call<ParentModel> = apiInterface.getHomeless(get)
            //   var call: Call<ParentModel> = apiInterface.getChildData()

            call.enqueue(object : Callback<List<ParentModel>> {
                override fun onResponse(
                    call: Call<List<ParentModel>>,
                    response: Response<List<ParentModel>>
                ) {
                    Log.e("response", response.body().toString())

                  if (getPersonName==response.body()?.getOrNull(0)?.name){
binding!!.namePostData.text=response.body()?.getOrNull(0)?.name
binding!!.addressPostData.text=response.body()?.getOrNull(0)?.address
binding!!.agePostData.text=response.body()?.getOrNull(0)?.age.toString()
                      binding!!.detailsPostData.text=response.body()?.getOrNull(0)?.gender

                      }else{
                      binding!!.namePostData.text="Nothing"

                  }

//                  namePostData.setText(response.body()?.id.toString())
//                    addressPostData.setText(response.body()?.title)
                }

                override fun onFailure(call: Call<List<ParentModel>>, t: Throwable) {
                    binding!!.addressPostData.text=t.message
                }

            })
        }
    
}