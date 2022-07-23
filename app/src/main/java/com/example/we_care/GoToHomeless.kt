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
import com.example.we_care.databinding.ActivityGoToHomelessBinding
import models.ParentModel
import okhttp3.MediaType
import okhttp3.MultipartBody
import okhttp3.RequestBody
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response
import java.io.File

class GoToHomeless : AppCompatActivity(), TextWatcher {

    var binding: ActivityGoToHomelessBinding? = null
    private var fileService: Api? = null
    private var imagePath: String? = null
    private var imageUri: Uri? = null
    private lateinit var ageHomeless: String
    private lateinit var addressHomeless: String
    private lateinit var genderHomeless: String
    private lateinit var nameHomeless: String
    private lateinit var phoneHomeless: String


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityGoToHomelessBinding.inflate(layoutInflater)

        setContentView(binding!!.root)

//        binding!!.nameHomeless.addTextChangedListener(this)
//        binding!!.ageHomeless.addTextChangedListener(this)
//        binding!!.addressHomeless.addTextChangedListener(this)
//        binding!!.genderHomeless.addTextChangedListener(this)
//        binding!!.photoHomeless.addTextChangedListener(this)
//        binding!!.phoneHomeless.addTextChangedListener(this)


        binding!!.saveHomeless.setOnClickListener {

            ageHomeless = binding!!.ageHomeless.text.toString().trim()
            addressHomeless = binding!!.addressHomeless.text.toString().trim()
            genderHomeless = binding!!.genderHomeless.text.toString().trim()
            nameHomeless = binding!!.nameHomeless.text.toString().trim()
            phoneHomeless = binding!!.phoneHomeless.text.toString().trim()

            if (imageUri != null && imagePath != null && ageHomeless.isNotEmpty() && addressHomeless.isNotEmpty() && genderHomeless.isNotEmpty() && nameHomeless.isNotEmpty() && phoneHomeless.isNotEmpty()
            ) {
                val file = File(imagePath)
                val requestBody: RequestBody =
                    RequestBody.create(MediaType.parse("multipart/form-data"), file)


                val name = RequestBody.create(MultipartBody.FORM, nameHomeless)
                val age = RequestBody.create(MultipartBody.FORM, ageHomeless)
                val address = RequestBody.create(MultipartBody.FORM, addressHomeless)
                val body = MultipartBody.Part.createFormData("file", file.name, requestBody)
                val gender = RequestBody.create(MultipartBody.FORM, genderHomeless)
                val phone = RequestBody.create(MultipartBody.FORM, phoneHomeless)

                fileService = APIUtils.getFileService()
                val call: Call<ParentModel?>? =
                    fileService!!.homeless(name, age, phone, address, body, gender)
                call?.enqueue(object : Callback<ParentModel?> {
                    override fun onResponse(
                        call: Call<ParentModel?>,
                        response: Response<ParentModel?>
                    ) {
                        if (response.isSuccessful) {
                            Toast.makeText(
                                this@GoToHomeless,
                                "Data uploaded successfully",
                                Toast.LENGTH_LONG
                            ).show()
                            binding!!.nameHomeless.text.clear()
                            binding!!.ageHomeless.text.clear()
                            binding!!.phoneHomeless.text.clear()
                            binding!!.addressHomeless.text.clear()
                            binding!!.photoHomeless.text = ""
                            binding!!.genderHomeless.text.clear()

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
            } else {
                Toast.makeText(
                    this@GoToHomeless,
                    "Please fill all fields with data",
                    Toast.LENGTH_LONG
                ).show()
            }

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
//        binding!!.saveHomeless.isEnabled = binding!!.nameHomeless.text.trim().isNotBlank()
//                && binding!!.ageHomeless.text.trim().isNotBlank()
//                && binding!!.addressHomeless.text.trim().isNotBlank()
//                && binding!!.genderHomeless.text.trim().isNotBlank()
//                && binding!!.photoHomeless.text.trim().isNotBlank()
//                && binding!!.phoneHomeless.text.trim().isNotBlank()

    }

    override fun afterTextChanged(p0: Editable?) {
    }


}
