package com.example.we_care

import android.graphics.Bitmap
import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import com.example.we_care.databinding.ActivityUploadBinding

class Upload : AppCompatActivity() {
    var binding: ActivityUploadBinding? = null
    var path: String? = null
    var bitmap: Bitmap?=null
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityUploadBinding.inflate(layoutInflater)
        setContentView(binding!!.root)

//
//        binding!!.select.setOnClickListener {
//
//
//            if (ContextCompat.checkSelfPermission(
//                    applicationContext,
//                    android.Manifest.permission.WRITE_EXTERNAL_STORAGE
//                ) == PackageManager.PERMISSION_GRANTED
//            ) {
//                val intent = Intent()
//                intent.type = "image/*"
//                intent.action = Intent.ACTION_GET_CONTENT
//                startActivityForResult(intent, 10)
//            } else {
//                ActivityCompat.requestPermissions(
//                    this@Upload,
//                    arrayOf(android.Manifest.permission.WRITE_EXTERNAL_STORAGE),
//                    1
//                )
//            }
//
//
//        }
//        binding!!.save.setOnClickListener {
//
//
//            val image: String = convertToString()
//         var retrofit = Retrofit.Builder().baseUrl("http://we-care1.herokuapp.com/api/")
//                .addConverterFactory(GsonConverterFactory.create()).build()
//            val apiInterface: Api = retrofit.create(Api::class.java)
//
//
//           // val call: Call<Pojo?>? = apiInterface.uploadImage(image)
//
//          //  call?.enqueue(object :Callback<Pojo?>{
//                override fun onResponse(call: Call<Pojo?>, response: Response<Pojo?>) {
//                    binding!!.textView2.text="no"
//
//                }
//
//                override fun onFailure(call: Call<Pojo?>, t: Throwable) {
//                    binding!!.textView2.text=t.message.toString()
//                }
//
//            })








//
//            var byteArrayqutputstream = ByteArrayOutputStream()
//            bitmap?.compress(Bitmap.CompressFormat.JPEG, 75, byteArrayqutputstream)
//            var imageInByte: ByteArray? = byteArrayqutputstream.toByteArray()
//          var encodedImage = Base64.encodeToString(imageInByte, Base64.DEFAULT)

//
//            var retrofit =
//                Retrofit.Builder().baseUrl("http://we-care1.herokuapp.com/api/")
//                    .addConverterFactory(GsonConverterFactory.create()).build()
//
//            var apiInter = retrofit.create(Api::class.java)
//
//            var call :Call<ParentModel> = apiInter.upload(encodedImage)
//            call.enqueue(object :Callback<ParentModel>{
//                override fun onResponse(call: Call<ParentModel>, response: Response<ParentModel>) {
//                    binding!!.textView2.text=response.body()?.age
//                }
//
//                override fun onFailure(call: Call<ParentModel>, t: Throwable) {
//                    binding!!.textView2.text=t.message.toString()
//                }
//
//            })



//
//        }
//    }
//
//    override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {
//        super.onActivityResult(requestCode, resultCode, data)
//
//
//        if (requestCode == 10 && resultCode == RESULT_OK) {
//            var uri: Uri? = data?.getData()
//            var context: Context = this
//            path = uri?.let { RealPathUtil.getRealPatht(context, it) }
//             bitmap = BitmapFactory.decodeFile(path)
//            binding!!.imageView2.setImageBitmap(bitmap)
//        }
//
//
//
//    }
//    private fun convertToString(): String {
//        val byteArrayOutputStream = ByteArrayOutputStream()
//        bitmap!!.compress(Bitmap.CompressFormat.JPEG, 100, byteArrayOutputStream)
//        // bitmap.compress(Bitmap.CompressFormat.JPEG, 100, byteArrayOutputStream);
//        val imgByte = ByteArrayOutputStream::class.java.newInstance().toByteArray()
//        return Base64.encodeToString(imgByte, Base64.DEFAULT)
    }

}