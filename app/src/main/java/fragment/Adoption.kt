package fragment

import android.content.Intent
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import com.example.we_care.databinding.FragmentAdoptionBinding


class Adoption : Fragment() {

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        val bind = FragmentAdoptionBinding.inflate(layoutInflater)
        bind.letsGoAdoption.setOnClickListener() {
            val intent = Intent(
                this@Adoption.requireContext(), Adoption2::class.java
            ).addFlags(Intent.FLAG_ACTIVITY_CLEAR_TASK or Intent.FLAG_ACTIVITY_CLEAR_TOP)
            startActivity(intent)
        }
        return bind.root

    }
    }
