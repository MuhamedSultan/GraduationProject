package com.example.we_care

import android.content.Intent
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import android.widget.Toast.makeText
import androidx.fragment.app.Fragment
import com.example.we_care.databinding.FragmentRecognitionByCameraBinding


class RecognitionByCamera : Fragment() {
    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        val bind = FragmentRecognitionByCameraBinding.inflate(layoutInflater)
        bind.btnParents.setOnClickListener() {
            val intent = Intent(this@RecognitionByCamera.requireContext(), GoToParents::class.java)
            startActivity(intent)
        }
        bind.btnAddTheHomeless.setOnClickListener() {
            val intent = Intent(this@RecognitionByCamera.requireContext(), GoToHomeless::class.java)
            startActivity(intent)
        }
        bind.cameraButton.setOnClickListener() {
            val intent = Intent(
                this@RecognitionByCamera.requireContext(), CameraActivity2::class.java
            ).addFlags(Intent.FLAG_ACTIVITY_CLEAR_TASK or Intent.FLAG_ACTIVITY_CLEAR_TOP)
            startActivity(intent)
        }
        return bind.root
    }

}