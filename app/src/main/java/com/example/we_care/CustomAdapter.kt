package com.example.we_care

import models.User
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView


class CustomAdapter(var mylist: List<User>) : RecyclerView.Adapter<CustomAdapter.ViewHolder> () {


    override fun onCreateViewHolder(p0: ViewGroup, p1: Int): CustomAdapter.ViewHolder {

        val v  = LayoutInflater.from (p0.context).inflate (R.layout.recyclerevent, p0, false)
        return ViewHolder(v)
    }

    override fun getItemCount (): Int {

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
        p0.date.text=infUser.event_day

       // p0.userPhoto.setImageResource(infUser.instructor_img)
        //  p0.userPhoto.setImageResource (response.body()?.image)
    }

    class ViewHolder(itemView: View): RecyclerView.ViewHolder (itemView) {

        val textTime = itemView.findViewById (R.id.time) as TextView
        val period = itemView.findViewById (R.id.duration) as TextView
        val textHomelessName = itemView.findViewById (R.id.textName) as TextView
        val textHomelessAddress = itemView.findViewById (R.id.textAddress) as TextView
        val textInstructorName = itemView.findViewById (R.id.instructorName) as TextView
        val phoneNumber = itemView.findViewById (R.id.phoneNumber) as TextView
        val date = itemView.findViewById (R.id.date) as TextView
        fun bind (user: User){
            val userPhoto = itemView.findViewById (R.id.photo) as ImageView
           // userPhoto.load(user.event_Period)
        }


    }
}

