package com.example.we_care

import android.content.CursorLoader
import models.ParentModel
import android.content.Intent
import android.database.Cursor
import android.graphics.Bitmap
import android.net.Uri
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.provider.MediaStore
import android.text.Editable
import android.text.TextWatcher
import android.util.Base64
import android.util.Log
import android.widget.Toast
import api.APIUtils
import api.Api
import com.example.we_care.databinding.ActivityGoToHomelessBinding
import com.example.we_care.databinding.ActivityGoToParentsBinding
import okhttp3.MediaType
import okhttp3.MultipartBody
import okhttp3.RequestBody
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import java.io.ByteArrayOutputStream
import java.io.File

class GoToHomeless : AppCompatActivity(),TextWatcher {

    var binding: ActivityGoToHomelessBinding? = null
//    var imagepath: Uri?=null
//    var bitmap : Bitmap?=null
//    var parentName :String?=null
//    var parentage :String?=null
//    var parentaddress :String?=null
//    var parentDetail :String?=null
//    var imagemsar :String?=null
//    var encodedImage :String?=null

    var fileService: Api? = null
    var imagePath: String? = null
    var imageUri: Uri? = null

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityGoToHomelessBinding.inflate(layoutInflater)
//
        setContentView(binding!!.root)

        binding!!.nameHomeless.addTextChangedListener(this)
        binding!!.ageHomeless.addTextChangedListener(this)
        binding!!.addressHomeless.addTextChangedListener(this)
        binding!!.detailsHomeless.addTextChangedListener(this)
        binding!!.photoHomeless.addTextChangedListener(this)

        fileService = APIUtils.getFileService()


        binding!!.saveHomeless.setOnClickListener {

            val file = File(imagePath)
            val requestBody: RequestBody =
                RequestBody.create(MediaType.parse("multipart/form-data"), file)

            val parentage = binding!!.ageHomeless.text.toString().trim()
            val parentaddress = binding!!.addressHomeless.text.toString().trim()
            val parentDetail = binding!!.detailsHomeless.text.toString().trim()
            val parentName = binding!!.nameHomeless.text.toString().trim()

            val name = RequestBody.create(MultipartBody.FORM, parentName)
            val age = RequestBody.create(MultipartBody.FORM, parentage)
            val address = RequestBody.create(MultipartBody.FORM, parentaddress)
            val body = MultipartBody.Part.createFormData("file", file.name, requestBody)
            val gender = RequestBody.create(MultipartBody.FORM, parentDetail)

            val call: Call<ParentModel?>? = fileService!!.parent(name,age,address,body,gender)
            call?.enqueue(object : Callback<ParentModel?> {
                override fun onResponse(call: Call<ParentModel?>, response: Response<ParentModel?>) {
                    if (response.isSuccessful) {
                        Toast.makeText(
                            this@GoToHomeless,
                            "Data uploaded successfully",
                            Toast.LENGTH_LONG
                        ).show()
                        Log.v("Upload", "success")
                    }

                }

                override fun onFailure(call: Call<ParentModel?>, t: Throwable) {
                    Toast.makeText(
                        this@GoToHomeless,
                        "error" + t.message.toString(),
                        Toast.LENGTH_LONG
                    ).show()

                }

            })


        }
        binding!!.uploadHomeless.setOnClickListener {
                val intent = Intent(Intent.ACTION_PICK)
                intent.type = "image/*"
                startActivityForResult(intent, 0)

        }
    }


    override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {
        super.onActivityResult(requestCode, resultCode, data)

        if (resultCode == RESULT_OK) {
            if (data == null) {
                Toast.makeText(this, "Unable to choose image!", Toast.LENGTH_SHORT).show()
                return

            }
            imageUri = data.data
            imagePath = getRealPathFromUri(imageUri!!)
            binding!!.photoHomeless.text = imageUri.toString()

        }
    }

    private fun getRealPathFromUri(uri: Uri): String {
        val projection: Array<String> = arrayOf(MediaStore.Images.Media.DATA)
        val loader = CursorLoader(applicationContext, uri, projection, null, null, null)
        val cursor: Cursor = loader.loadInBackground()
        var column_idx: Int = cursor.getColumnIndexOrThrow(MediaStore.Images.Media.DATA)
        cursor.moveToFirst()
        var result: String = cursor.getString(column_idx)
        cursor.close()
        return result
    }


    override fun beforeTextChanged(p0: CharSequence?, p1: Int, p2: Int, p3: Int) {

    }

    override fun onTextChanged(p0: CharSequence?, p1: Int, p2: Int, p3: Int) {
        binding!!.saveHomeless.isEnabled = binding!!.nameHomeless.text.trim().isNotBlank()
                &&binding!!.ageHomeless.text.trim().isNotBlank()
                &&binding!!.addressHomeless.text.trim().isNotBlank()
                &&binding!!.detailsHomeless.text.trim().isNotBlank()
                &&binding!!.photoHomeless.text.trim().isNotBlank()
    }

    override fun afterTextChanged(p0: Editable?) {
    }


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
