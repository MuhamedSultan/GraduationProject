package com.example.we_care

import adapter.CustomAdapterLost
import api.Api
import android.annotation.SuppressLint
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.fragment.app.Fragment
import androidx.recyclerview.widget.GridLayoutManager
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import models.userLostPeople
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory


class LostPeople : Fragment() {

    var myRecyclerLost: RecyclerView? = null
    var myArrayListLost: ArrayList<userLostPeople>? = null

    @SuppressLint("WrongConstant")
    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {

        return inflater.inflate(R.layout.fragment_lost_people, container, false)
    }
    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        myRecyclerLost = view.findViewById(R.id.recyclerLostPeople)
        myArrayListLost = ArrayList()

        var retrofit = Retrofit.Builder()
            .baseUrl("https://wecare5.000webhostapp.com/api/")
            .addConverterFactory(GsonConverterFactory.create())
            .build()

        val apiInterface = retrofit.create(Api::class.java)
        val call: Call<List<userLostPeople>> = apiInterface.getLostPeopleData()
        call.enqueue(object : Callback<List<userLostPeople>> {
            override fun onResponse(
                call: Call<List<userLostPeople>>,
                response: Response<List<userLostPeople>>
            ) {
                showData(response.body()!!)

            }
            override fun onFailure(call: Call<List<userLostPeople>>, t: Throwable) {
                Toast.makeText(requireContext(), t.message.toString(), Toast.LENGTH_LONG).show()
            }

        })
    }
    private fun showData(myArrayListLost: List<userLostPeople>) {

        myRecyclerLost?.layoutManager = LinearLayoutManager(context)
        myRecyclerLost?.adapter = CustomAdapterLost(requireContext(),myArrayListLost)
        myRecyclerLost?.layoutManager = GridLayoutManager(context, 1)
    }
}