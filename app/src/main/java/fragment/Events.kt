package fragment

//import kotlinx.android.synthetic.main.fragment_events.*
import adapter.CustomAdapter
import android.annotation.SuppressLint
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.fragment.app.Fragment
import androidx.recyclerview.widget.GridLayoutManager
import androidx.recyclerview.widget.LinearLayoutManager
import api.Api
import com.example.we_care.R
import kotlinx.android.synthetic.main.fragment_events.*
import models.User
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory


class Events : Fragment() {

    var myArrayList: ArrayList<User>? = null

    @SuppressLint("WrongConstant")
    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {

        return inflater.inflate(R.layout.fragment_events, container, false)

    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)


        myArrayList = ArrayList()
        //
        var retrofit = Retrofit.Builder()
            .baseUrl("https://wecare5.000webhostapp.com/api/")
            .addConverterFactory(GsonConverterFactory.create()).build()
        var apiInterface = retrofit.create(Api::class.java)
        var call: Call<List<User>> = apiInterface.event()
        call.enqueue(
            object : Callback<List<User>> {
                override fun onResponse(
                    call: Call<List<User>>,
                    response: Response<List<User>>
                ) {
                    showData(response.body()!!)
//                val customAdapter = CustomAdapter(myArrayList!!)
//                Mrecycler.adapter = customAdapter

//
                }

                override fun onFailure(call: Call<List<User>>, t: Throwable) {
                    Toast.makeText(requireActivity(), t.message.toString(), Toast.LENGTH_LONG).show()
                    //  binding!!.textView.setText(t.message.toString())

                }

            })

    }

    private fun showData(myArrayList: List<User>) {

        Mrecycler.layoutManager = LinearLayoutManager(context)
        Mrecycler.adapter = CustomAdapter(requireActivity(),myArrayList)
        Mrecycler.layoutManager = GridLayoutManager(context, 1)


    }

}
