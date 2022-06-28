package fragment

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.fragment.app.Fragment
import api.APIUtils
import api.Api
import com.example.we_care.databinding.FragmentConnectUsBinding
import models.ContactUsModel
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory


class ContactUs : Fragment() {
    var binding: FragmentConnectUsBinding? = null
    var fileService: Api? = null
    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        binding = FragmentConnectUsBinding.inflate(layoutInflater)

        binding!!.submitConnect.setOnClickListener {

        var name = binding!!.userNameConnect.text.toString()
        var Email = binding!!.emailConnect.text.toString()
        var phone = binding!!.phoneConnect.text.toString()
        var message = binding!!.messageConnect.text.toString()
        var model = ContactUsModel(name, Email, phone, message)

            fileService=APIUtils.getFileService()
        var call: Call<ContactUsModel> = fileService!!.contactUsToHelp(model)
        call.enqueue(object : Callback<ContactUsModel> {
            override fun onResponse(
                call: Call<ContactUsModel>,
                response: Response<ContactUsModel>
            ) {
                Toast.makeText(requireContext(),"Your message Sent Successfully",Toast.LENGTH_LONG).show()
                binding!!.userNameConnect.text.clear()
                 binding!!.emailConnect.text.clear()
                 binding!!.phoneConnect.text.clear()
                binding!!.messageConnect.text.clear()
              }

            override fun onFailure(call: Call<ContactUsModel>, t: Throwable) {
                Toast.makeText(requireContext(),t.message.toString(),Toast.LENGTH_LONG).show()
            }

        })
    }

        return binding!!.root
    }

}


