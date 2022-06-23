package adapter

import android.content.Context
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.example.we_care.R
import models.userLostPeople

class CustomAdapterLost(var context: Context,var mylistLost: List<userLostPeople>) :
    RecyclerView.Adapter<CustomAdapterLost.ViewHolder>() {

    override fun onCreateViewHolder(p0: ViewGroup, p1: Int): ViewHolder {

        val v = LayoutInflater.from(p0.context).inflate(R.layout.recyclerlostpeople, p0, false)
        return ViewHolder(v)
    }

    override fun getItemCount(): Int {

        return mylistLost.size
    }

    override fun onBindViewHolder(p0: ViewHolder, p1: Int) {
        var infUserLost = mylistLost[p1]
        p0.textName.text = infUserLost.name
        p0.textAgeLost.text = infUserLost.age
        p0.textAddressLost.text = infUserLost.address
        p0.textDetailsLost.text = infUserLost.details

        Glide.with(context)
            .load(infUserLost.userPhotoLost)
            .into(p0.image)


//  p0.userPhotoLosttt.setImageResource (infUserLost.userPhotoLost)
// val userPhotoLosttt = itemView.findViewById (R.id.photoLost) as ImageView
    }

    class ViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {

        val textName = itemView.findViewById(R.id.nameLost) as TextView
        val textAgeLost = itemView.findViewById(R.id.ageLost) as TextView
        val textAddressLost = itemView.findViewById(R.id.addressLost) as TextView
        val textDetailsLost = itemView.findViewById(R.id.detailsLost) as TextView
        val image =itemView.findViewById<ImageView>(R.id.photoLost)

    }
}
