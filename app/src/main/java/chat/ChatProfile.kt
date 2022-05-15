package chat

import android.app.Activity
import android.content.Intent
import android.graphics.Bitmap
import android.graphics.Color
import android.os.Build
import android.os.Bundle
import android.provider.MediaStore
import android.view.View
import androidx.appcompat.app.AppCompatActivity
import com.example.we_care.R
import com.example.we_care.databinding.ActivityChatProfileBinding
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.firestore.DocumentReference
import com.google.firebase.firestore.FirebaseFirestore
import com.google.firebase.storage.FirebaseStorage
import com.google.firebase.storage.StorageReference
import glide.GlideApp
import java.io.ByteArrayOutputStream
import java.util.*

class ChatProfile : AppCompatActivity() {
    var binding: ActivityChatProfileBinding? = null

    companion object {
        const val RC_SELECT_IMAGE = 2
    }

    private lateinit var userName: String
    private val firestoreInstance: FirebaseFirestore by lazy {
        FirebaseFirestore.getInstance()
    }
    private val currentUserDoRef: DocumentReference
        get() = firestoreInstance.document("users/${FirebaseAuth.getInstance().currentUser?.uid.toString()}")

    val storageInstance: FirebaseStorage by lazy {
        FirebaseStorage.getInstance()
    }
    val currentUserStorageRef: StorageReference
        get() = storageInstance.reference.child(FirebaseAuth.getInstance().currentUser?.uid.toString())


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityChatProfileBinding.inflate(layoutInflater)
        setContentView(binding!!.root)

        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M){
                window.decorView.systemUiVisibility= View.SYSTEM_UI_FLAG_LIGHT_STATUS_BAR
    }else {
            window.statusBarColor = Color.WHITE
        }
            setSupportActionBar(binding!!.profileToolbar)
            binding!!.profileToolbar.title = "Me"
            supportActionBar?.setHomeButtonEnabled(true)
            supportActionBar?.setDisplayHomeAsUpEnabled(true)

        getUserInfo {
            userName = it.name

binding!!.profileUserName.text=it.name
            if (it.profileImage.isNotEmpty()) {

                GlideApp.with(this)
                    .load(storageInstance.getReference(it.profileImage))
                    .placeholder(R.drawable.person_icon)
                    .into(binding!!.circleImageView)
            }
        }
        binding!!.circleImageView.setOnClickListener {
            val intentImage = Intent().apply {
                type = "image/*"
                action = Intent.ACTION_GET_CONTENT
                putExtra(Intent.EXTRA_MIME_TYPES, arrayOf("image/jpeg","image/png"))
            }
            startActivityForResult(
                Intent.createChooser(intentImage, "Select Image"),
                RC_SELECT_IMAGE
            )

        }
    }

    override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {
        super.onActivityResult(requestCode, resultCode, data)
        if (requestCode == RC_SELECT_IMAGE && resultCode == Activity.RESULT_OK
            && data != null && data.data != null
        ) {
            binding!!.circleImageView.setImageURI(data.data)

            val selectedImagePath = data.data
            val selectedImageBmp =
                MediaStore.Images.Media.getBitmap(this.contentResolver, selectedImagePath)
            val outputstream = ByteArrayOutputStream()
            selectedImageBmp.compress(Bitmap.CompressFormat.JPEG, 20, outputstream)
            val selectedimageBytes = outputstream.toByteArray()
            uploadProfileImage(selectedimageBytes) {

                val userFieldMap = mutableMapOf<String, Any>()
                userFieldMap["name"] = userName
                userFieldMap["profileImage"] = it
                currentUserDoRef.update(userFieldMap)
            }
        }
    }

    private fun uploadProfileImage(
        selectedimageBytes: ByteArray,
        onSuccessListener: (imagePath: String) -> Unit
    ) {
        val ref =
            currentUserStorageRef.child("profilePictures/${UUID.nameUUIDFromBytes(selectedimageBytes)}")
        ref.putBytes(selectedimageBytes).addOnCompleteListener {
            if (it.isSuccessful) {
                onSuccessListener(ref.path)
            } else
//                    (Toast.makeText( this, "Error:${it.exception?.message.toString()}", Toast.LENGTH_LONG)
//            .show())
                binding!!.profileUserName.setText(it.exception.toString())

        }
    }

    private fun getUserInfo(onComplete: (User) -> Unit) {
        currentUserDoRef.get().addOnSuccessListener {
            onComplete(it.toObject(User::class.java)!!)
        }
    }

}

