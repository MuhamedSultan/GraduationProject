package com.example.we_care

import models.userDonation
import android.content.Intent
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Button
import android.widget.ImageView
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
//import kotlinx.android.synthetic.main.recyclerdonition.view.*
import java.util.*

class CustomAdapetrDonation(var mylistDonation: ArrayList<userDonation>) : RecyclerView.Adapter<CustomAdapetrDonation.ViewHolder> () {


    override fun onCreateViewHolder(p0: ViewGroup, p1: Int): CustomAdapetrDonation.ViewHolder {

        val v  = LayoutInflater.from (p0.context).inflate (R.layout.recyclerdonition, p0, false)
        return ViewHolder(v)

    }

    override fun getItemCount (): Int {

        return mylistDonation.size
    }

    override fun onBindViewHolder(p0:ViewHolder, p1: Int) {
        var infUserLost = mylistDonation[p1]
        p0.dateDonationnn.text = infUserLost.dateDonation
        p0.textNameHomeDonationnn.text = infUserLost.textNameHomeDonation
        p0.textDonationnn.text = infUserLost.textDonation
        p0.userPhotoDonationnn.setImageResource (infUserLost.PhotoDonation)

       p0.donationBtn.setOnClickListener {
            val intent = Intent(
                it.context,Donation::class.java
            )
            it.context.startActivity(intent)
        }


    }

    class ViewHolder(itemView: View): RecyclerView.ViewHolder (itemView) {

        val dateDonationnn = itemView.findViewById (R.id.dateDonation) as TextView
        val textNameHomeDonationnn = itemView.findViewById (R.id.nameDonation) as TextView
        val textDonationnn = itemView.findViewById (R.id.detailsDonation) as TextView
        val userPhotoDonationnn = itemView.findViewById (R.id.photoDonation) as ImageView
        val donationBtn : Button = itemView.findViewById(R.id.btnDonation)as Button
    }
}
