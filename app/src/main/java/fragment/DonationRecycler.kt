package fragment

import models.userDonation
import android.annotation.SuppressLint
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.GridLayoutManager
import androidx.recyclerview.widget.RecyclerView
import adapter.CustomAdapetrDonation
import com.example.we_care.R
import java.util.*


class DonationRecycler : Fragment() {

    var myArrayListDonatin: ArrayList<userDonation>?=null


    @SuppressLint("WrongConstant")
    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {


        val view = inflater.inflate(R.layout.fragment_donation_recycler, container,false)

            return view

    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)



        var myRecyclerDonation =view.findViewById<RecyclerView>(R.id.recyclerDonation)

        myArrayListDonatin = ArrayList ()
        // 1
        myArrayListDonatin!!.add(
            userDonation(
                R.drawable.home,
            "Noor Al Amal House for Orphan Care",
            "This home has helped many children and the elderly, but now we need help to finish what we started.",
            "3/10/2022")
        )

        // 2
        myArrayListDonatin!!.add(
            userDonation(
                R.drawable.home,
                "Noor Al Amal House for Orphan Care",
                "This home has helped many children and the elderly, but now we need help to finish what we started.",
                "3/10/2022")
        )

        // 3
        myArrayListDonatin!!.add(
            userDonation(
                R.drawable.home,
                "Noor Al Amal House for Orphan Care",
                "This home has helped many children and the elderly, but now we need help to finish what we started.",
                "3/10/2022")
        )

        // 4
        myArrayListDonatin!!.add(
            userDonation(
                R.drawable.home,
                "Noor Al Amal House for Orphan Care",
                "This home has helped many children and the elderly, but now we need help to finish what we started.",
                "3/10/2022")
        )

        // 5
        myArrayListDonatin!!.add(
            userDonation(
                R.drawable.home,
                "Noor Al Amal House for Orphan Care",
                "This home has helped many children and the elderly, but now we need help to finish what we started.",
                "3/10/2022")
        )

        // 6
        myArrayListDonatin!!.add(
            userDonation(
                R.drawable.home,
                "Noor Al Amal House for Orphan Care",
                "This home has helped many children and the elderly, but now we need help to finish what we started.",
                "3/10/2022")
        )

        // 7
        myArrayListDonatin!!.add(
            userDonation(
                R.drawable.home,
                "Noor Al Amal House for Orphan Care",
                "This home has helped many children and the elderly, but now we need help to finish what we started.",
                "3/10/2022")
        )

        // 8
        myArrayListDonatin!!.add(
            userDonation(
                R.drawable.home,
                "Noor Al Amal House for Orphan Care",
                "This home has helped many children and the elderly, but now we need help to finish what we started.",
                "3/10/2022")
        )

        // 9
        myArrayListDonatin!!.add(
            userDonation(
                R.drawable.home,
                "Noor Al Amal House for Orphan Care",
                "This home has helped many children and the elderly, but now we need help to finish what we started.",
                "3/10/2022")
        )

        // 10
        myArrayListDonatin!!.add(
            userDonation(
                R.drawable.home,
                "Noor Al Amal House for Orphan Care",
                "This home has helped many children and the elderly, but now we need help to finish what we started.",
                "3/10/2022")
        )

        val CustomAdaperDonation  = CustomAdapetrDonation (myArrayListDonatin!!)
        myRecyclerDonation.adapter = CustomAdaperDonation

        myRecyclerDonation.layoutManager = GridLayoutManager(context,1)






    }
}