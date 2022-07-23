package com.example.we_care

import android.content.Intent
import android.os.Bundle
import android.text.Editable
import android.text.SpannableString
import android.text.Spanned
import android.text.TextWatcher
import android.text.method.LinkMovementMethod
import android.text.style.ClickableSpan
import android.text.style.UnderlineSpan
import android.util.Patterns
import android.view.View
import android.widget.Toast
import androidx.appcompat.app.AlertDialog
import androidx.appcompat.app.AppCompatActivity
import api.APIUtils
import api.Api
import com.example.we_care.databinding.ActivitySignUpBinding
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.auth.FirebaseUser
import com.google.firebase.firestore.DocumentReference
import com.google.firebase.firestore.FirebaseFirestore
import models.Model
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

class SignUp : AppCompatActivity(), TextWatcher {
    var binding: ActivitySignUpBinding? = null
    lateinit var username: String
    lateinit var email: String
    lateinit var phoneNumber: String
    lateinit var password: String
    lateinit var confirmpassword: String
    var fileService: Api? = null
    var alertDialog: AlertDialog? = null

    private val mAuth: FirebaseAuth by lazy {
        FirebaseAuth.getInstance()
    }
    private val fireStoreInstance: FirebaseFirestore by lazy {
        FirebaseFirestore.getInstance()
    }
    private val currentUserDocRef: DocumentReference
        get() = fireStoreInstance.document("users/${mAuth.currentUser?.uid.toString()}")

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivitySignUpBinding.inflate(layoutInflater)
        setContentView(binding!!.root)
        supportActionBar?.hide()
//
//        binding!!.username.addTextChangedListener(this)
//        binding!!.Emailaddress.addTextChangedListener(this)
//        binding!!.phoneNumber.addTextChangedListener(this)
//        binding!!.password.addTextChangedListener(this)

        binding!!.onsignup.setOnClickListener {
            username = binding!!.username.text.toString().trim()
            email = binding!!.Emailaddress.text.toString().trim()
            phoneNumber = binding!!.phoneNumber.text.toString().trim()
            password = binding!!.password.text.toString().trim()
            confirmpassword = binding!!.confirmpassword.text.toString().trim()

            if (username.isEmpty() && email.isEmpty() && password.isEmpty() && phoneNumber.isEmpty()) {
                valid()
            } else {
                loadingProgress()

                retrofit(username, email, phoneNumber, password)
              //  createUserWithEmailAndPassword(username, email, password)
            }
        }
        loginNow()
    }

    private fun retrofit(username: String, email: String,phoneNumber:String, password: String) {

        fileService = APIUtils.getFileService()
        val model = Model(username, email, phoneNumber, password)

        val call: Call<Model> = fileService!!.register(model)
        call.enqueue(object : Callback<Model> {
            override fun onResponse(call: Call<Model>, response: Response<Model>) {
                alertDialog?.dismiss()
                if (response.isSuccessful) {

                    alertDialog?.dismiss()
                      if (password != confirmpassword) {
                        binding!!.confirmpassword.error = "password doesn't match"
                        binding!!.confirmpassword.requestFocus()
                        alertDialog?.dismiss()
                    } else if (!Patterns.EMAIL_ADDRESS.matcher(email).matches()) {
                        binding!!.Emailaddress.error = "Please Enter a valid Email"
                        binding!!.Emailaddress.requestFocus()
                    } else if (password.length < 6) {
                        binding!!.password.error = "password 6 letters at least"
                        binding!!.password.requestFocus()
                    } else {
                        //  sendVerification()
                        val intent = Intent(this@SignUp, Login::class.java)
                        intent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK or Intent.FLAG_ACTIVITY_CLEAR_TASK)
                        startActivity(intent)
                        finish()
                    }
                } else if (response.body()?.status_code == 500) {
                    alertDialog?.dismiss()
                    binding!!.username.error = "username is used by another one"
                    binding!!.username.requestFocus()


                }

            }

            override fun onFailure(call: Call<Model>, t: Throwable) {
                Toast.makeText(this@SignUp, t.message, Toast.LENGTH_LONG).show()
            }

        })
    }

    private fun loginNow() {
        val text = "Already have an Account? Login"
        val string = SpannableString(text)
        val span = UnderlineSpan()

        string.setSpan(span, 25, 30, Spanned.SPAN_EXCLUSIVE_EXCLUSIVE)
        val clickableSpan: ClickableSpan = object : android.text.style.ClickableSpan() {
            override fun onClick(view: View) {
                var intent = Intent(this@SignUp, Login::class.java)
                startActivity(intent)
            }
        }
        string.setSpan(clickableSpan, 25, 30, Spanned.SPAN_EXCLUSIVE_EXCLUSIVE)
        binding?.loginnow?.text = string
        binding?.loginnow?.movementMethod = LinkMovementMethod.getInstance()
    }

    private fun valid() {
        if (username.isEmpty()) {
            binding!!.username.error = "username required"
            binding!!.username.requestFocus()
        }
        if (email.isEmpty()) {
            binding!!.Emailaddress.error = "EmailAddress required"
            binding!!.Emailaddress.requestFocus()
        }

        if (password.isEmpty()) {
            binding!!.password.error = "password required"
            binding!!.password.requestFocus()
        }
        if (confirmpassword.isEmpty()) {
            binding!!.confirmpassword.error = "confirm password required"
            binding!!.confirmpassword.requestFocus()
        }
        if (phoneNumber.isEmpty()) {
            binding!!.phoneNumber.error = "phoneNumber required"
            binding!!.phoneNumber.requestFocus()
        }

    }

    private fun loadingProgress() {
        val alertbuilder = AlertDialog.Builder(this)
        val view = layoutInflater.inflate(R.layout.loading, null)
        alertbuilder.setView(view)
        alertDialog = alertbuilder.create()
        alertDialog!!.show()
    }

    override fun beforeTextChanged(p0: CharSequence?, p1: Int, p2: Int, p3: Int) {
    }

    override fun onTextChanged(p0: CharSequence?, p1: Int, p2: Int, p3: Int) {

//        binding!!.onsignup.isEnabled = binding!!.username.text.trim().isNotBlank()
//                && binding!!.Emailaddress.text.trim().isNotEmpty()
//                && binding!!.password.text.trim().isNotEmpty()
//                && binding!!.confirmpassword.text.trim().isNotEmpty()
//                && binding!!.phoneNumber.text.trim().isNotEmpty()

    }

    override fun afterTextChanged(p0: Editable?) {
    }

    private fun createUserWithEmailAndPassword(name: String, Email: String, Password: String) {
        mAuth.createUserWithEmailAndPassword(Email, Password).addOnCompleteListener {
            if (it.isSuccessful) {
              //  sendVerification()
                var newUser = chat.User(name, "")
                currentUserDocRef.set(newUser)

            } else if (!it.isSuccessful) {

                Toast.makeText(this, it.exception?.message, Toast.LENGTH_LONG).show()

            }
        }
    }

    private fun sendVerification() {
        val user: FirebaseUser? = FirebaseAuth.getInstance().currentUser
        user?.reload()
        user!!.sendEmailVerification().addOnCompleteListener {
            if (it.isSuccessful) {
                Toast.makeText(this, "Verification Email Sent", Toast.LENGTH_LONG).show()
                val intent = Intent(this, Login::class.java)
                intent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK or Intent.FLAG_ACTIVITY_CLEAR_TASK)
                startActivity(intent)
            } else if (!it.isSuccessful) {
                Toast.makeText(this, it.exception?.message, Toast.LENGTH_LONG).show()
            }
        }
    }
}


