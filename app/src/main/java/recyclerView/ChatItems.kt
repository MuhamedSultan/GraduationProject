package recyclerView

//import android.content.ClipData.Item as
//import com.xwray.groupie.kotlinandroidextensions.ViewHolder
import android.content.Context
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import chat.User
import com.example.we_care.R
import com.google.firebase.storage.FirebaseStorage
import com.xwray.groupie.kotlinandroidextensions.Item
import com.xwray.groupie.kotlinandroidextensions.ViewHolder
import glide.GlideApp
import kotlinx.android.synthetic.main.recycler_view_item.*
import kotlinx.android.synthetic.main.recycler_view_item.view.*

//class ChatItems(val user: User, val context: Context) :
//
//    RecyclerView.Adapter<ChatItems.MyViewHolder>() {
//    val storageInstance: FirebaseStorage by lazy {
//        FirebaseStorage.getInstance()
//    }
//
//    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): MyViewHolder {
//        return MyViewHolder(
//            (LayoutInflater.from(parent.context)
//                .inflate(R.layout.recycler_view_item, parent, false))
//        )
//    }
//
//    override fun onBindViewHolder(holder: MyViewHolder, position: Int) {
//     //   var infUserLost = user[position]
//        holder.name.text = user.name
//        holder.time.text = "time"
//        holder.name.text = "last"
////
//        if (user.profileImage.isNotEmpty()) {
//            GlideApp.with(context)
//                .load(storageInstance.getReference(user.profileImage))
//                .into(holder.image.item_circle_imageView)
//        } else {
//            holder.image.item_circle_imageView.setImageResource(R.drawable.person_icon)
//
//        }
//    }
//
//
//    override fun getItemCount(): Int {
//        return 1
//    }
//
//    class MyViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
//        var name = itemView.findViewById<TextView>(R.id.item_name_textView)
//        var time = itemView.findViewById<TextView>(R.id.item_time_textView)
//        var last = itemView.findViewById<TextView>(R.id.item_lastMessage_textView)
//        var image = itemView.findViewById<ImageView>(R.id.item_circle_imageView)
//    }
//
//}


class ChatItems(val uid :String, val user: User, val context: Context) : Item() {
    private val storageInstance: FirebaseStorage by lazy{
        FirebaseStorage.getInstance()
    }
    override fun bind(viewHolder: ViewHolder, position: Int) {
       viewHolder.item_name_textView.text=user.name
       viewHolder.item_time_textView.text="time"
       viewHolder.item_lastMessage_textView.text="last message"

        if (user.profileImage.isNotEmpty()) {
            GlideApp.with(context)
                .load(storageInstance.getReference(user.profileImage))
                .into(viewHolder.item_circle_imageView)
        }else{
            viewHolder.item_circle_imageView.setImageResource(R.drawable.person_icon)
        }
    }

    override fun getLayout(): Int {
        return R.layout.recycler_view_item
    }
}