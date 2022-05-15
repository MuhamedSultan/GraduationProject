package chat

import android.graphics.Color
import android.os.Build
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.MenuItem
import android.view.View
import androidx.fragment.app.Fragment
import chat_fragments.ChatFragment
import chat_fragments.MoreFragment
import chat_fragments.PeopleFragment
import com.example.we_care.R
import com.example.we_care.databinding.ActivityChatUsersBinding
import com.google.android.material.bottomnavigation.BottomNavigationView
import com.google.android.material.navigation.NavigationView
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.firestore.FirebaseFirestore
import com.google.firebase.storage.FirebaseStorage
import com.squareup.okhttp.Dns.SYSTEM
import glide.GlideApp

class ChatUsers : AppCompatActivity(),
    BottomNavigationView.OnNavigationItemSelectedListener {
    var binding :ActivityChatUsersBinding?=null
    private val mAuth : FirebaseAuth by lazy {
        FirebaseAuth.getInstance()
    }
    private val firestoreInstance :FirebaseFirestore by lazy {
        FirebaseFirestore.getInstance()
    }
    private val storageInstance: FirebaseStorage by lazy{
        FirebaseStorage.getInstance ()
    }
    private val mChatFragment=ChatFragment()
    private val mPeopleFragment =PeopleFragment()
     private val mMoreFragment= MoreFragment()
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityChatUsersBinding.inflate(layoutInflater)
        setContentView(binding!!.root)


        firestoreInstance.collection( "users")
        .document (FirebaseAuth.getInstance () .currentUser?.uid. toString ())
            .get ()
            .addOnSuccessListener {
                val user = it.toObject(User::class.java)
                if (user!!.profileImage.isNotEmpty()){
                    GlideApp.with(this)
                .load(storageInstance.getReference(user.profileImage))
                        .into(binding!!.personImage)

            }else{
        binding!!.personImage.setImageResource (R.drawable.person_icon)


            }
    }

        setSupportActionBar(binding!!.chatToolbar)
        supportActionBar?.title=""
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M){
            window.decorView.systemUiVisibility = View.SYSTEM_UI_FLAG_LIGHT_STATUS_BAR
    }
    else {
        window.statusBarColor = Color.WHITE
    }
        binding!!.bottomNavigationView.setOnNavigationItemSelectedListener(this)
        setFragment(mChatFragment)
    }

    override fun onNavigationItemSelected(item: MenuItem): Boolean {
        when (item.itemId) {
            R.id.navigation_chat_item -> {
                setFragment(mChatFragment)
                binding!!.titleToolbarTextView.text="Chat"
                return true
            }
            R.id.navigation_people_item -> {

                setFragment(mPeopleFragment)
                binding!!.titleToolbarTextView.text="People"
                return true
            }
            R.id.navigation_more_item -> {
                setFragment(mMoreFragment)
                binding!!.titleToolbarTextView.text="More"
                return true
                
            }
            else -> return false
        }
    }

    private fun setFragment(fragment: Fragment) {
            val fr=supportFragmentManager.beginTransaction ()
            fr.replace(R.id.coordinator_layout,fragment)
            fr.commit ()
        }





}