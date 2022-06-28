package com.example.we_care

import api.APIUtils
import api.Api
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
import com.example.we_care.databinding.ActivityGoToParentsBinding
import models.ParentModel
import okhttp3.MediaType
import okhttp3.MultipartBody
import okhttp3.RequestBody
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response
import java.io.File

class GoToParents : AppCompatActivity(),TextWatcher {
    var binding: ActivityGoToParentsBinding? = null
    var fileService: Api? = null
    var imagePath: String? = null
      var imageUri: Uri? = null

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityGoToParentsBinding.inflate(layoutInflater)
        setContentView(binding!!.root)

        binding!!.nameParents.addTextChangedListener(this)
        binding!!.ageParents.addTextChangedListener(this)
        binding!!.addressParents.addTextChangedListener(this)
        binding!!.detailsParents.addTextChangedListener(this)
        binding!!.photoParents.addTextChangedListener(this)

        fileService = APIUtils.getFileService()


        binding!!.savedata.setOnClickListener {

            val file = File(imagePath)
            val requestBody: RequestBody =
                RequestBody.create(MediaType.parse("multipart/form-data"), file)

            val parentage = binding!!.ageParents.text.toString().trim()
            val parentaddress = binding!!.addressParents.text.toString().trim()
            val parentDetail = binding!!.detailsParents.text.toString().trim()
            val parentName = binding!!.nameParents.text.toString().trim()

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
                            this@GoToParents,
                            "Data upload successfully",
                            Toast.LENGTH_LONG
                        ).show()
                        Log.v("Upload", "success")
                    }

                }

                override fun onFailure(call: Call<ParentModel?>, t: Throwable) {
                    Toast.makeText(
                        this@GoToParents,
                        "error" + t.message.toString(),
                        Toast.LENGTH_LONG
                    ).show()

                }

            })


        }
        binding!!.uploadParents.setOnClickListener {

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
            binding!!.photoParents.text = imagePath.toString()
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
        binding!!.savedata.isEnabled = binding!!.nameParents.text.trim().isNotBlank()
                &&binding!!.ageParents.text.trim().isNotBlank()
                &&binding!!.addressParents.text.trim().isNotBlank()
                &&binding!!.detailsParents.text.trim().isNotBlank()
                &&binding!!.photoParents.text.trim().isNotBlank()

    }

    override fun afterTextChanged(p0: Editable?) {

    }

}