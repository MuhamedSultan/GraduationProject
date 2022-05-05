package com.example.we_care

import models.ParentModel
import android.content.Intent
import android.graphics.Bitmap
import android.net.Uri
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.provider.MediaStore
import android.util.Base64
import android.widget.Toast
import com.example.we_care.databinding.ActivityGoToHomelessBinding
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import java.io.ByteArrayOutputStream

class GoToHomeless : AppCompatActivity() {

    var binding: ActivityGoToHomelessBinding? = null
    var imagepath: Uri?=null
    var bitmap : Bitmap?=null
    var parentName :String?=null
    var parentage :String?=null
    var parentaddress :String?=null
    var parentDetail :String?=null
    var imagemsar :String?=null
    var encodedImage :String?=null

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityGoToHomelessBinding.inflate(layoutInflater)

        setContentView(binding!!.root)
//
//        binding!!.saveHomeless.setOnClickListener {
//
//            var byteArrayqutputstream =  ByteArrayOutputStream ()
//            bitmap?.compress (Bitmap.CompressFormat.JPEG,  75,byteArrayqutputstream )
//            var imageInByte: ByteArray? = byteArrayqutputstream.toByteArray()
//            encodedImage = Base64.encodeToString(imageInByte, Base64.DEFAULT)
//
//
//
//            var retrofit=  Retrofit.Builder().baseUrl("https://grad-project3.000webhostapp.com/api/")
//                .addConverterFactory(GsonConverterFactory.create()).build()
//
//
//            var apiInterface = retrofit.create(Api::class.java)
//
//            parentName = binding!!.nameHomeless.text.toString().trim()
//            parentage = binding!!.ageHomeless.text.toString().trim()
//
//            parentaddress = binding!!.addressHomeless.text.toString().trim()
//
//            parentDetail = binding!!.detailsHomeless.text.toString().trim()
//            //   imagemsar = binding!!.photoHomeless.text.toString().trim()
//
//
//
//            var parentModel = ParentModel(parentName!!,
//                parentage!!, parentaddress!!,null, parentDetail!!)
//
//            var call1 : Call<ParentModel> = apiInterface.parent(parentModel)
//
//            call1.enqueue(object : Callback<ParentModel>{
//                override fun onResponse(call: Call<ParentModel>, response: Response<ParentModel>) {
//                    Toast.makeText(this@GoToHomeless,"success",Toast.LENGTH_SHORT).show()
//
//
//                }
//
//                override fun onFailure(call: Call<ParentModel>, t: Throwable) {
//                    //  Toast.makeText(this@GoToParents,t.message.toString(),Toast.LENGTH_SHORT).show()
//
//                }
//
//            })
//
//        }
//
//        binding!!.uploadHomeless.setOnClickListener {
//            openCamera()
//        }
//
//    }
//    private fun openCamera (){
//        val image = Intent(Intent.ACTION_PICK)
//        image.type = "image/*"
//        startActivityForResult(image, 1)
//    }
//
//    override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {
//        super.onActivityResult(requestCode, resultCode, data)
//        if (requestCode == 1 && resultCode == RESULT_OK) {
//            imagepath = data?.data
//            if (imagepath != null) {
//                // val filepath  = Calendar.getInstance().time.toString()
//                bitmap = MediaStore.Images.Media.getBitmap(contentResolver, imagepath)
//                binding?.photoHomeless?.setText(imagepath.toString())
//
//
//            }
//        }
    }
}