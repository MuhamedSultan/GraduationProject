package chat

import android.graphics.Color
import android.os.Build
import android.os.Bundle
import android.view.View
import androidx.appcompat.app.AppCompatActivity
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.we_care.R
import com.example.we_care.databinding.ActivityChatBinding
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.firestore.FirebaseFirestore
import com.google.firebase.firestore.Query
import com.google.firebase.storage.FirebaseStorage
import com.xwray.groupie.GroupAdapter
import com.xwray.groupie.kotlinandroidextensions.ViewHolder
import glide.GlideApp
import models.TextMessage
import recyclerView.TextMessageItem
import java.util.*

class ChatActivity : AppCompatActivity() {

    var binding: ActivityChatBinding? = null

    private val fireStoreInstance: FirebaseFirestore by lazy {
        FirebaseFirestore.getInstance()
    }
    private val firebaseInstance: FirebaseStorage by lazy {
        FirebaseStorage.getInstance()
    }
    private val chatChannelCollectionRef = fireStoreInstance.collection("chatChannels")
    private var mOtherUserId = ""
    val mcurrentUserId = FirebaseAuth.getInstance().currentUser!!.uid
    private val messageAdapter by lazy {
        GroupAdapter<ViewHolder>()
    }


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityChatBinding.inflate(layoutInflater)
        setContentView(binding!!.root)



        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) {
            window.decorView.systemUiVisibility = View.SYSTEM_UI_FLAG_LIGHT_STATUS_BAR
        } else {
            window.statusBarColor = Color.WHITE
        }
        val userName = intent.getStringExtra("userName")
        val profileImage = intent.getStringExtra("profileImage")
        mOtherUserId = intent.getStringExtra("otherUId").toString()

        binding!!.textViewUserName.text = userName

        crateChatChannel { channelId ->

            getMessages(channelId)

            binding!!.sendMessage.setOnClickListener {
                val sendMessage = TextMessage(
                    binding!!.editTextMessage.text.toString(),
                    mcurrentUserId,
                    Calendar.getInstance().time
                )
                sendMessage(channelId, sendMessage)
                binding!!.editTextMessage.text.clear()
            }
        }

        binding!!.recyclerViewChat.apply {
             adapter = messageAdapter
        }

        if (profileImage!!.isNotEmpty()) {
            GlideApp.with(this)
                .load(firebaseInstance.getReference(profileImage))
                .into(binding!!.circleImageProfile)
        } else {
            binding!!.circleImageProfile.setImageResource(R.drawable.person_icon)
        }
        binding!!.imageViewBack.setOnClickListener {
            finish()
        }
    }

    private fun sendMessage(chaneelId: String, messageSend: TextMessage) {
        chatChannelCollectionRef.document(chaneelId).collection("messages")
            .add(messageSend)
    }

    private fun crateChatChannel(onComplete: (channelId: String) -> Unit) {

        fireStoreInstance.collection("users")
            .document(FirebaseAuth.getInstance().currentUser!!.uid)
            .collection("sharedChat")
            .document(mOtherUserId)
            .get()
            .addOnSuccessListener {
                if (it.exists()) {
                    onComplete(it["channelId"] as String)
                    return@addOnSuccessListener
                }

                val newChatChannel = fireStoreInstance.collection("users").document()


                fireStoreInstance.collection("users")
                    .document(mOtherUserId)
                    .collection("sharedChat")
                    .document(mcurrentUserId)
                    .set(mapOf("channelId" to newChatChannel.id))

                fireStoreInstance.collection("users")
                    .document(mcurrentUserId)
                    .collection("sharedChat")
                    .document(mOtherUserId)
                    .set(mapOf("channelId" to newChatChannel.id))

                onComplete(newChatChannel.id)

            }
    }

    private fun getMessages(channelId: String) {
        val query =
            chatChannelCollectionRef.document(channelId).collection("messages").orderBy("date"
            ,Query.Direction.DESCENDING)
        query.addSnapshotListener { value, error ->
            messageAdapter.clear()
            value!!.documents.forEach { document ->
                messageAdapter.add(
                    TextMessageItem(
                        document.toObject(TextMessage::class.java)!!,
                        document.id,
                        this
                    )
                )

            }
        }
    }
}