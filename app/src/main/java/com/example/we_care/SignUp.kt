package com.example.we_care

import api.Api
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
import com.example.we_care.databinding.ActivitySignUpBinding
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.firestore.DocumentReference
import com.google.firebase.firestore.FirebaseFirestore
import models.Model
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory

class SignUp : AppCompatActivity(), TextWatcher {
    var binding: ActivitySignUpBinding? = null
    lateinit var username: String
    lateinit var email: String
    lateinit var password: String
    lateinit var confirmpassword: String
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

        binding!!.username.addTextChangedListener(this)
        binding!!.Emailaddress.addTextChangedListener(this)
        binding!!.password.addTextChangedListener(this)

        binding!!.onsignup.setOnClickListener {
            username = binding!!.username.text.toString().trim()
            email = binding!!.Emailaddress.text.toString().trim()
            password = binding!!.password.text.toString().trim()
            confirmpassword = binding!!.confirmpassword.text.toString().trim()

            loadingProgress()
            retrofit(username, email, password)
            createUserWithEmailAndPassword(username, email, password)
        }
        loginNow()
    }
//    override fun onStart() {
//        super.onStart()
//        if (SharedPrefManager.getInstance(this).isLoggedIn()){
//            var intent = Intent(this, NavigitionDrawer::class.java)
//            intent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK or Intent.FLAG_ACTIVITY_CLEAR_TASK)
//            startActivity(intent)
//        }
//    }

    private fun retrofit(username: String, email: String, password: String) {
        var retrofit =
            Retrofit.Builder()
                .baseUrl("https://wecare5.000webhostapp.com/api/")
               // .baseUrl("https://grad-project3.000webhostapp.com/api/")
                //.baseUrl("http://we-care1.herokuapp.com/api/")
                .addConverterFactory(GsonConverterFactory.create()).build()

        var apiInterface = retrofit.create(Api::class.java)

        val model = Model(username, email, password)

        val call: Call<Model> = apiInterface.register(model)
        call.enqueue(object : Callback<Model> {
            override fun onResponse(call: Call<Model>, response: Response<Model>) {

                if (response.isSuccessful) {
                    //Toast.makeText(this@SignUp,"fdg"+response.body()?.email,Toast.LENGTH_LONG).show()
//                       Log.d("mmresponse"+response.body()?.name,"success")
                    alertDialog?.dismiss()
                    if (username.isEmpty() && email.isEmpty() && password.isEmpty()) {
                        valid()
                    } else if (password != confirmpassword) {
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
                        val sharedPreferences = getSharedPreferences("Data", MODE_PRIVATE)
                        val editier = sharedPreferences.edit()
                        editier.putString("name", response.body()!!.name)
                        editier.putString("email", response.body()!!.email)
                        editier.apply()
                        val intent = Intent(this@SignUp, Login::class.java)
                        intent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK or Intent.FLAG_ACTIVITY_CLEAR_TASK)
                        startActivity(intent)
                    }
                } else if (response.body()?.status_code == 500) {
                    binding!!.username.error = "username is used by another one"
                    binding!!.username.requestFocus()
                    alertDialog?.dismiss()

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

        binding!!.onsignup.isEnabled = binding!!.username.text.trim().isNotBlank()
                && binding!!.Emailaddress.text.trim().isNotEmpty()
                && binding!!.password.text.trim().isNotEmpty()

    }

    override fun afterTextChanged(p0: Editable?) {
    }

    private fun createUserWithEmailAndPassword(name: String, Email: String, Password: String) {
        mAuth.createUserWithEmailAndPassword(Email, Password).addOnCompleteListener {
            val newUser = chat.User(name,"")
            currentUserDocRef.set(newUser)

            if (!it.isSuccessful) {

                Toast.makeText(this, it.exception?.message, Toast.LENGTH_LONG).show()

            }
        }
    }
}


