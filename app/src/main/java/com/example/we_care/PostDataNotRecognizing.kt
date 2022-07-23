package com.example.we_care

import android.content.CursorLoader
import android.content.Intent
import android.database.Cursor
import android.net.Uri
import android.os.Bundle
import android.provider.MediaStore
import android.text.Editable
import android.text.TextWatcher
import android.util.Log
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import api.APIUtils
import api.Api
import com.example.we_care.databinding.ActivityPostDataNotRecognizingBinding
import models.ParentModel
import okhttp3.MediaType
import okhttp3.MultipartBody
import okhttp3.RequestBody
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response
import java.io.File

class PostDataNotRecognizing : AppCompatActivity(), TextWatcher {

    var binding: ActivityPostDataNotRecognizingBinding? = null
    var fileService: Api? = null
    var imagePath: String? = null
    var imageUri: Uri? = null
    private lateinit var parentage :String
    private lateinit var parentAddress :String
    private lateinit var parentGender :String
    private lateinit var parentName :String
    private lateinit var parentPhone :String

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_post_data_not_recognizing)

        binding = ActivityPostDataNotRecognizingBinding.inflate(layoutInflater)
        setContentView(binding!!.root)

//        binding!!.namePostData.addTextChangedListener(this)
//        binding!!.agePostData.addTextChangedListener(this)
//        binding!!.addressPostData.addTextChangedListener(this)
//        binding!!.genderPostData.addTextChangedListener(this)
//        binding!!.phonePostData.addTextChangedListener(this)
//        binding!!.photoPostData.addTextChangedListener(this)




        binding!!.savePostData.setOnClickListener {

             parentage = binding!!.agePostData.text.toString().trim()
             parentAddress = binding!!.addressPostData.text.toString().trim()
             parentGender = binding!!.genderPostData.text.toString().trim()
             parentName = binding!!.namePostData.text.toString().trim()
             parentPhone = binding!!.namePostData.text.toString().trim()


            if (imageUri != null && imagePath != null && parentName.isNotEmpty()
                && parentage.isNotEmpty() && parentPhone.isNotEmpty() && parentAddress.isNotEmpty()
                && parentGender.isNotEmpty()
            ) {


                val file = File(imagePath)
                val requestBody: RequestBody =
                    RequestBody.create(MediaType.parse("multipart/form-data"), file)


                val name = RequestBody.create(MultipartBody.FORM, parentName)
                val age = RequestBody.create(MultipartBody.FORM, parentage)
                val address = RequestBody.create(MultipartBody.FORM, parentAddress)
                val body = MultipartBody.Part.createFormData("file", file.name, requestBody)
                val gender = RequestBody.create(MultipartBody.FORM, parentGender)
                val phone = RequestBody.create(MultipartBody.FORM, parentPhone)

                fileService = APIUtils.getFileService()
                val call: Call<ParentModel?>? =
                    fileService!!.lostPeople(name, age, phone, address, body, gender)
                call?.enqueue(object : Callback<ParentModel?> {
                    override fun onResponse(
                        call: Call<ParentModel?>,
                        response: Response<ParentModel?>
                    ) {
                        if (response.isSuccessful) {
                            Toast.makeText(
                                this@PostDataNotRecognizing,
                                "Data upload successfully",
                                Toast.LENGTH_LONG
                            ).show()
                            binding!!.namePostData.text.clear()
                            binding!!.agePostData.text.clear()
                            binding!!.phonePostData.text.clear()
                            binding!!.addressPostData.text.clear()
                            binding!!.photoPostData.text = ""
                            binding!!.genderPostData.text.clear()

                            Log.v("Upload", "success")
                        }

                    }

                    override fun onFailure(call: Call<ParentModel?>, t: Throwable) {
                        Toast.makeText(
                            this@PostDataNotRecognizing,
                            "error" + t.message.toString(),
                            Toast.LENGTH_LONG
                        ).show()

                    }

                })
            }else {
                Toast.makeText(
                    this@PostDataNotRecognizing,
                    "Please fill all fields with data",
                    Toast.LENGTH_LONG
                ).show()
            }

            binding!!.namePostData.text.clear()
            binding!!.agePostData.text.clear()
            binding!!.phonePostData.text.clear()
            binding!!.addressPostData.text.clear()
            binding!!.photoPostData.text = ""
            binding!!.genderPostData.text.clear()
        }
        binding!!.uploadPostData.setOnClickListener {

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
            binding!!.photoPostData.text = imagePath.toString()
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
//        binding!!.savePostData.isEnabled = binding!!.namePostData.text.trim().isNotBlank()
//                && binding!!.agePostData.text.trim().isNotBlank()
//                && binding!!.addressPostData.text.trim().isNotBlank()
//                && binding!!.genderPostData.text.trim().isNotBlank()
//                && binding!!.photoPostData.text.trim().isNotBlank()
//                && binding!!.phonePostData.text.trim().isNotBlank()

    }

    override fun afterTextChanged(p0: Editable?) {

    }

}
