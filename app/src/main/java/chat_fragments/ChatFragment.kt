package chat_fragments

import android.content.Context
import android.content.Intent
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import androidx.recyclerview.widget.LinearLayoutManager
import chat.ChatActivity
import chat.ChatProfile
import chat.User
import com.example.we_care.R
import com.example.we_care.databinding.FragmentChatBinding
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.firestore.FirebaseFirestore
import com.google.firebase.firestore.ListenerRegistration
import com.xwray.groupie.GroupAdapter
import com.xwray.groupie.OnItemClickListener

import com.xwray.groupie.Section
import com.xwray.groupie.ViewHolder
import com.xwray.groupie.kotlinandroidextensions.Item
import kotlinx.android.synthetic.main.fragment_chat.*
import recyclerView.ChatItems

class ChatFragment : Fragment() {

   // var binding: FragmentChatBinding? = null
    private val firestoreInstance: FirebaseFirestore by lazy {
        FirebaseFirestore.getInstance()
    }
private lateinit var chatSection: Section
    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {

        val profileImage = activity?.findViewById<ImageView>(R.id.person_image)

        profileImage?.setOnClickListener {
            val intent = Intent(activity, ChatProfile::class.java)
            startActivity(intent)
        }
        addChatListener(::initRecyclerview)
        return inflater.inflate(R.layout.fragment_chat, container, false)
    }

    private fun addChatListener(onListen :(List<Item>)->Unit): ListenerRegistration {
        return firestoreInstance.collection("users")
            .addSnapshotListener { querySnapshot, firebaseFirestoreException ->
              //  var fragment = ChatFragment();
             //   if (fragment.activity != null) {
                    if (firebaseFirestoreException != null) {
                        return@addSnapshotListener
                    }
                    val items = mutableListOf<Item>()
                    querySnapshot!!.documents.forEach {document ->
                        items.add(ChatItems(document.id,document.toObject(User::class.java)!!, requireActivity()))
                    }
                    onListen(items)
                }
            }
   // }

    private fun initRecyclerview(item: List<Item>) {
        Myrec.apply {
            layoutManager = LinearLayoutManager(activity)
            adapter = GroupAdapter<ViewHolder>().apply {
                chatSection=Section(item)
                add(chatSection)
                setOnItemClickListener(onItemClick)
            }

        }
    }
    private val onItemClick = OnItemClickListener { item, view ->
        if (item is ChatItems) {

            val intentChatActivity = Intent(activity, ChatActivity::class.java)
            intentChatActivity.putExtra("userName", item.user.name)
            intentChatActivity.putExtra("profileImage", item.user.profileImage)
            intentChatActivity.putExtra("otherUId",item.uid)

            requireActivity().startActivity(intentChatActivity)
        }

    }

}