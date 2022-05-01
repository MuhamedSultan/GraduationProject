package com.example.we_care

import android.os.Bundle
import android.widget.TextView
import androidx.appcompat.app.AppCompatActivity
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory

class IdentifyGetData : AppCompatActivity() {
    //  var binding :ActivityIdentifyGetDataBinding ?=null
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_identify_get_data)
        var namePostData = findViewById<TextView>(R.id.namePostData)
        var addressPostData = findViewById<TextView>(R.id.addressPostData)
        var agePostData = findViewById<TextView>(R.id.agePostData)
        var detailsPostData = findViewById<TextView>(R.id.detailsPostData)
   //     var buttonn = findViewById<Button>(R.id.button)
      //  buttonn.setOnClickListener {
            var retrofit =
                Retrofit.Builder()
                    .baseUrl("http://we-care1.herokuapp.com/api/")
                   // .baseUrl("https://jsonplaceholder.typicode.com/")
                    .addConverterFactory(GsonConverterFactory.create()).build()

            var apiInterface = retrofit.create(Api::class.java)
            var getPersonName = Face_recognition.Val
         //   var rdit = findViewById<EditText>(R.id.gett)
         //   var get = rdit.text.toString()
          //  var call: Call<ParentModel> = apiInterface.getHomeless(get)
          //  var call: Call<GetChiledData> = apiInterface.getHomeless(get)
          //  var call: Call<ParentModel> = apiInterface.getChildData()

//            call.enqueue(object : Callback<GetChiledData> {
//                override fun onResponse(call: Call<GetChiledData>, response: Response<GetChiledData>) {
//                  //  if (get == response.body()?.name) {
//                        namePostData.text = response.body()?.name
//                        addressPostData.text = response.body()?.address
//                        agePostData.text = response.body()?.age
//                        detailsPostData.text = response.body()?.gender
////                   // }
//
////                    namePostData.setText(response.body()?.id.toString())
////                    addressPostData.setText(response.body()?.title)
//                }
//
//                override fun onFailure(call: Call<GetChiledData>, t: Throwable) {
//                    namePostData.text = t.message.toString()
//                }
//
//            })
      //  }

    }
}