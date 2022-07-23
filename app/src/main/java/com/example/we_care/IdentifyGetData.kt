package com.example.we_care

import android.os.Bundle
import android.util.Log
import androidx.appcompat.app.AppCompatActivity
import api.APIUtils
import api.Api
import com.bumptech.glide.Glide
import com.example.we_care.databinding.ActivityIdentifyGetDataBinding
import models.ParentModel
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response


class IdentifyGetData : AppCompatActivity() {

    var binding: ActivityIdentifyGetDataBinding? = null
    var fileService: Api? = null

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityIdentifyGetDataBinding.inflate(layoutInflater)
        setContentView(binding!!.root)

        val getPersonName = Face_recognition.Val

        fileService = APIUtils.getFileService()
        val call: Call<List<ParentModel>> = fileService!!.getHomeless(getPersonName)



        call.enqueue(object : Callback<List<ParentModel>> {
            override fun onResponse(
                call: Call<List<ParentModel>>,
                response: Response<List<ParentModel>>
            ) {
                Log.e("response", response.body().toString())

                if (getPersonName == response.body()?.getOrNull(0)?.name) {
                    binding!!.namePostData.text = response.body()?.getOrNull(0)?.name
                    binding!!.addressPostData.text = response.body()?.getOrNull(0)?.address
                    binding!!.agePostData.text = response.body()?.getOrNull(0)?.age.toString()
                    binding!!.detailsPostData.text = response.body()?.getOrNull(0)?.gender
                    binding!!.phonePostData.text=response.body()?.getOrNull(0)?.phone
                    Glide.with(this@IdentifyGetData)
                        .load(response.body()?.getOrNull(0)?.image)
                        .into(binding!!.identifyImage)

                }
            }

            override fun onFailure(call: Call<List<ParentModel>>, t: Throwable) {
                binding!!.addressPostData.text = t.message
            }

        })
    }

}