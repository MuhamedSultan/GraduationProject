package com.example.we_care

import api.Api
import android.content.Intent
import android.os.Bundle
import android.text.SpannableString
import android.text.Spanned
import android.text.method.LinkMovementMethod
import android.text.style.UnderlineSpan
import android.view.View
import android.widget.Toast
import androidx.appcompat.app.AlertDialog
import androidx.appcompat.app.AppCompatActivity
import api.APIUtils
import com.example.we_care.databinding.ActivityLoginBinding
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.auth.FirebaseUser
import kotlinx.android.synthetic.main.activity_sign_up.*
import models.Model
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import android.text.style.ClickableSpan as ClickableSpan1

class Login : AppCompatActivity() {

    var binding: ActivityLoginBinding? = null
    var alertDialog: AlertDialog? = null
   private lateinit var email :String
   private lateinit var password: String
    var fileService: Api? = null

    private val mAuth : FirebaseAuth by lazy {
        FirebaseAuth.getInstance()
    }
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        binding = ActivityLoginBinding.inflate(layoutInflater)
        setContentView(binding!!.root)
        supportActionBar?.hide()
        createAccount()
        binding!!.onlogin.setOnClickListener {

            loadingProgress()

            email = binding!!.edEmail.text.toString().trim()
            password = binding!!.edPassword.text.toString().trim()

            if (email.isEmpty() && password.isEmpty()) {

                    alertDialog?.dismiss()
                binding!!.edEmail.error = "Email Required"
                binding!!.edEmail.requestFocus()

                binding!!.edPassword.error = "password Required"
                binding!!.edPassword.requestFocus()
            } else {

                retrofit(email, password)
                signIn(email, password)
            }
        }
    }

    private fun retrofit(email: String, password: String) {


        //   loadingProgress()
//
//        var retrofit = Retrofit.Builder()
//            .baseUrl("https://wecare5.000webhostapp.com/api/")
//            // .baseUrl("http://we-care1.herokuapp.com/api/")
//            .addConverterFactory(GsonConverterFactory.create()).build()
//
//        var apiInterface = retrofit.create(Api::class.java)

        fileService= APIUtils.getFileService()

        var model = Model(null, email, password)

        var call: Call<Model> = fileService!!.login(model)

        call.enqueue(object : Callback<Model> {
            override fun onResponse(call: Call<Model>, response: Response<Model>) {

                var apiResponse: Model? = response.body()

                val sharedPreferences = getSharedPreferences("userData", MODE_PRIVATE)
                val editor = sharedPreferences.edit()
                editor.putString("userName", apiResponse?.name)
                editor.putString("userEmail", apiResponse?.email)
                editor.apply()

                 if (apiResponse?.status_code == 200) {

//                    var intent = Intent(this@Login, NavigationDrawer::class.java)
//                    intent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK or Intent.FLAG_ACTIVITY_CLEAR_TASK)
//                    startActivity(intent)
                     verifyEmail()
                } else if (apiResponse?.status_code==500) {
                     alertDialog?.hide()
                    Toast.makeText(this@Login, "Email or password is wrong.", Toast.LENGTH_LONG)
                        .show()

                }

            }
            override fun onFailure(call: Call<Model>, t: Throwable) {
                Toast.makeText(this@Login, t.message, Toast.LENGTH_LONG).show()
            }
        })
    }

    private fun createAccount() {

        val text = "Don't have an Account? Create One"
        val string = SpannableString(text)
        val span = UnderlineSpan()
        string.setSpan(span, 23, 33, Spanned.SPAN_EXCLUSIVE_EXCLUSIVE)
        val clickableSpan: ClickableSpan1 = object : android.text.style.ClickableSpan() {
            override fun onClick(view: View) {
                var intent = Intent(this@Login, SignUp::class.java)
                startActivity(intent)
            }
        }
        string.setSpan(clickableSpan, 23, 33, Spanned.SPAN_EXCLUSIVE_EXCLUSIVE)
        binding!!.creataccount.text = string
        binding!!.creataccount.movementMethod = LinkMovementMethod.getInstance()

    }

    private fun loadingProgress() {
        val alertbuilder = AlertDialog.Builder(this)
        val view = layoutInflater.inflate(R.layout.loading, null)
        alertbuilder.setView(view)
        alertDialog = alertbuilder.create()
        alertDialog!!.show()
      //  alertDialog!!.setCancelable(false)

    }

//    override fun onPause() {
//        super.onPause()
//        alertDialog?.dismiss()
//    }
    private fun signIn(email: String, password: String) {
        loadingProgress()
        mAuth.signInWithEmailAndPassword(email,password).addOnCompleteListener {
            if (it.isSuccessful){
                val intent = Intent(this,NavigationDrawer::class.java)
                startActivity(intent)
            }else{
             //   alertDialog?.hide()
                Toast.makeText(this,it.exception.toString(),Toast.LENGTH_LONG).show()
            }
        }

    }
    fun verifyEmail(){
        val user: FirebaseUser? =FirebaseAuth.getInstance().currentUser
        if (user!!.isEmailVerified){
            val intent = Intent(this,NavigationDrawer::class.java)
            startActivity(intent)
        }else{
          //  alertDialog!!.cancel()
            Toast.makeText(this,"please verify Email Address",Toast.LENGTH_LONG).show()
        }
    }
}

//                    if (apiResponse != null) {
//                        sharedPrefManager.getInstance(this@Login)
//                            .saveUser(apiResponse)
//                    }
//                        SharedPrefManager.getInstance(this@Login)
//                            .saveUser(SharedPrefManager.getInstance(this@Login).getUser())


//    override fun onStart() {
//        super.onStart()
//       if (sharedPrefManager.getInstance(this).isLoggedIn()){
//           var intent = Intent(this@Login, NavigitionDrawer::class.java)
//           intent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK or Intent.FLAG_ACTIVITY_CLEAR_TASK)
//           startActivity(intent)
//       }
//    }
//            var responsestring: String? = "Response code :" + response.code() +responsefromapi?.password
