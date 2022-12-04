package com.example.we_care

import android.content.Intent
import android.graphics.BitmapFactory
import android.net.Uri
import android.os.Bundle
import android.provider.MediaStore
import android.util.Log
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import api.APIUtils
import api.Api
import com.example.we_care.databinding.ActivityGoToParentsBinding
import models.ParentModel
import okhttp3.MediaType
import okhttp3.MultipartBody
import okhttp3.RequestBody
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response
import java.io.File


class GoToParents : AppCompatActivity() {
    var binding: ActivityGoToParentsBinding? = null
    var fileService: Api? = null
    var imagePath: String? = null
    var dataimage: Uri? = null

    private lateinit var parentName: String
    private lateinit var parentage: String
    private lateinit var parentPhone: String
    private lateinit var parentAddress: String
    private lateinit var parentGender: String

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityGoToParentsBinding.inflate(layoutInflater)
        setContentView(binding!!.root)

        binding!!.savedata.setOnClickListener {

            parentName = binding!!.nameParents.text.toString().trim()
            parentage = binding!!.ageParents.text.toString().trim()
            parentPhone = binding!!.phoneParent.text.toString().trim()
            parentAddress = binding!!.addressParents.text.toString().trim()
            parentGender = binding!!.genderParents.text.toString().trim()

            if (dataimage != null && imagePath != null && parentName.isNotEmpty()
                && parentage.isNotEmpty() && parentPhone.isNotEmpty() && parentAddress.isNotEmpty()
                && parentGender.isNotEmpty()
            ) {

                val file = imagePath?.let { it1 -> File(it1) }
                val requestBody: RequestBody =
                    RequestBody.create(MediaType.parse("multipart/form-file"), file!!)


                val name = RequestBody.create(MultipartBody.FORM, parentName)
                val age = RequestBody.create(MultipartBody.FORM, parentage)
                val phone = RequestBody.create(MultipartBody.FORM, parentPhone)
                val address = RequestBody.create(MultipartBody.FORM, parentAddress)
                val body = MultipartBody.Part.createFormData("image", file.name, requestBody)
                val gender = RequestBody.create(MultipartBody.FORM, parentGender)

                fileService = APIUtils.getFileService()


                val call: Call<ParentModel?>? =
                    fileService!!.parent(name, age, phone, address, body, gender)
                call?.enqueue(object : Callback<ParentModel?> {
                    override fun onResponse(
                        call: Call<ParentModel?>,
                        response: Response<ParentModel?>
                    ) {
                        if (response.isSuccessful) {
                            Toast.makeText(
                                this@GoToParents,
                                "Data upload successfully",
                                Toast.LENGTH_LONG
                            ).show()
                            binding!!.nameParents.text.clear()
                            binding!!.ageParents.text.clear()
                            binding!!.phoneParent.text.clear()
                            binding!!.addressParents.text.clear()
                            binding!!.photoParents.text = ""
                            binding!!.genderParents.text.clear()
                            Log.v("Upload", response.body().toString())
                        } else {
                            Toast.makeText(
                                this@GoToParents,
                                "error",
                                Toast.LENGTH_LONG
                            ).show()
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

            } else {
                Toast.makeText(
                    this@GoToParents,
                    "Please fill all fields with data",
                    Toast.LENGTH_LONG
                ).show()
            }
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
            if (requestCode == 0) {

                dataimage = data?.data
                val imageprojection = arrayOf(MediaStore.Images.Media.DATA)
                val cursor = contentResolver.query(dataimage!!, imageprojection, null, null, null)
                if (cursor != null) {
                    cursor.moveToFirst()
                    val indexImage = cursor.getColumnIndex(imageprojection[0])
                    imagePath = cursor.getString(indexImage)
                    if (imagePath != null) {
                        val image: File = File(imagePath)
                        binding!!.photoParents.text = imagePath.toString()
                        val bitmap = BitmapFactory.decodeFile(imagePath)

                    }
                }
            }
        }


////        if (resultCode == RESULT_OK) {
////            if (data == null) {
////                Toast.makeText(this, "Unable to choose image!", Toast.LENGTH_SHORT).show()
////                return
////
////            }
////            imageUri = data.data
////            imagePath = getRealPathFromUri(imageUri!!)
////            binding!!.photoParents.text = imagePath.toString()
////        }
//
//    }
//        private fun getRealPathFromUri(uri: Uri): String {
//            val projection: Array<String> = arrayOf(MediaStore.Images.Media.DATA)
//            val loader = CursorLoader(applicationContext, uri, projection, null, null, null)
//            val cursor: Cursor = loader.loadInBackground()
//            var column_idx: Int = cursor.getColumnIndexOrThrow(MediaStore.Images.Media.DATA)
//            cursor.moveToFirst()
//            var result: String = cursor.getString(column_idx)
//            cursor.close()
//            return result
//        }
    }
}
