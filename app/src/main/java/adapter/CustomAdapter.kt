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
import models.User


class CustomAdapter :
    RecyclerView.Adapter<CustomAdapter.ViewHolder> {
    private var mcontext: Context
    private  var mylist: List<User>

    constructor(context: Context, mylist: List<User>) {
        this.mcontext = context
        this.mylist = mylist
    }

    override fun onCreateViewHolder(p0: ViewGroup, p1: Int): ViewHolder {

        val v = LayoutInflater.from(p0.context).inflate(R.layout.recyclerevent, p0, false)
        return ViewHolder(v)
    }

    override fun getItemCount(): Int {

        return mylist.size
    }

    override fun onBindViewHolder(p0: ViewHolder, p1: Int) {
        var infUser = mylist[p1]
        p0.textTime.text = infUser.event_time
        p0.period.text = infUser.event_Period
        p0.phoneNumber.text = infUser.house_phone
        p0.textInstructorName.text = infUser.responsible
        p0.textHomelessName.text = infUser.house_name
        p0.textHomelessAddress.text = infUser.house_address
        p0.date.text = infUser.event_day

        Glide.with(mcontext)
            .load("https://upload.wikimedia.org/wikipedia/commons/thumb/6/60/Scarlett_Johansson_by_Gage_Skidmore_2_%28cropped%29.jpg/1200px-Scarlett_Johansson_by_Gage_Skidmore_2_%28cropped%29.jpg")
            .placeholder(R.drawable.person_icon)
            .error(R.drawable.img)
            .into(p0.userPhoto)

        // p0.userPhoto.setImageResource(infUser.instructor_img)
    }

    class ViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {

        val textTime = itemView.findViewById(R.id.time) as TextView
        val period = itemView.findViewById(R.id.duration) as TextView
        val textHomelessName = itemView.findViewById(R.id.textName) as TextView
        val textHomelessAddress = itemView.findViewById(R.id.textAddress) as TextView
        val textInstructorName = itemView.findViewById(R.id.instructorName) as TextView
        val phoneNumber = itemView.findViewById(R.id.phoneNumber) as TextView
        val date = itemView.findViewById(R.id.date) as TextView
        val userPhoto = itemView.findViewById(R.id.photo) as ImageView




    }
}

