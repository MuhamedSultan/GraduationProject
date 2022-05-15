package com.example.we_care

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import com.example.we_care.databinding.FragmentConnectUsBinding
import models.ContactUsModel
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory


class ContactUs : Fragment() {
    var binding: FragmentConnectUsBinding? = null
    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        binding = FragmentConnectUsBinding.inflate(layoutInflater)

        binding!!.submitConnect.setOnClickListener {

        var retrofit = Retrofit.Builder()
            .baseUrl("http://we-care1.herokuapp.com/api/")
            .addConverterFactory(GsonConverterFactory.create())
            .build()

        var api = retrofit.create(Api::class.java)


        var name = binding!!.userNameConnect.text.toString()
        var Email = binding!!.emailConnect.text.toString()
        var phone = binding!!.phoneConnect.text.toString()
        var message = binding!!.messageConnect.text.toString()
        var model = ContactUsModel(name, Email, phone, message)
        var call: Call<ContactUsModel> = api.contactUsToHelp(model)
        call.enqueue(object : Callback<ContactUsModel> {
            override fun onResponse(
                call: Call<ContactUsModel>,
                response: Response<ContactUsModel>
            ) {
              }

            override fun onFailure(call: Call<ContactUsModel>, t: Throwable) {
                binding!!.userNameConnect.setText(t.message)
            }

        })
    }

        return binding!!.root
    }

}


