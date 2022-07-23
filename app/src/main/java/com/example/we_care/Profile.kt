package com.example.we_care

import android.content.SharedPreferences
import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import com.example.we_care.databinding.ActivityProfileBinding
import storage.SharedPrefManager

class Profile : AppCompatActivity() {
    var binding: ActivityProfileBinding?= null

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        // Inflate the layout for this fragment
        binding = ActivityProfileBinding.inflate(layoutInflater)
        setContentView(binding!!.root)


        //inflater.inflate(R.layout.fragment_profile, container, false)
//        val retrofit= Retrofit.Builder()
//            .baseUrl("http://we-care1.herokuapp.com/api/")
//            .addConverterFactory(GsonConverterFactory.create()).build()
//
//        var api =retrofit.create(Api::class.java)
//
//        var call :Call<Model> = api.getProfileData()
//
//        call.enqueue(object :Callback<Model>{
//            override fun onResponse(call: Call<Model>, response: Response<Model>) {
//                binding!!.username.text=response.body()?.name
//                binding!!.Email.text=response.body()?.email
//                binding!!.fullName.text=response.body()?.name
//
//
//            }
//
//            override fun onFailure(call: Call<Model>, t: Throwable) {
//                binding!!.Email.text=t.message.toString()
//            }
//
//        })

        //view.getsh ( "MyUserName",MODE_PRIVATE)


        //  var model: Model = sharedPrefManager.getInstance(this).getUser()
//        binding!!.username.text = model.name
//        binding!!.Email.text = model.email
//        binding!!.fullName.text = model.name


        val sharedPreferences: SharedPreferences =
            getSharedPreferences("DataOfUser", MODE_PRIVATE)

        val userName = sharedPreferences.getString("userName", "Nothing")
        val email = sharedPreferences.getString("userEmail", "Nothing")
        val phone = sharedPreferences.getString("userPhone", "Nothing")

        binding!!.username.text = userName
        binding!!.Email.text = email
        binding!!.fullName.text = userName
        binding!!.phone.text=phone
    }
}