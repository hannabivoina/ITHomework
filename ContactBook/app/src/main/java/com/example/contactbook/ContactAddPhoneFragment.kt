package com.example.contactbook

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import com.example.contactbook.databinding.FragmentContactAddPhoneBinding

class ContactAddPhoneFragment : Fragment() {
    private lateinit var binding: FragmentContactAddPhoneBinding
    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        binding = FragmentContactAddPhoneBinding.inflate(inflater, container, false)
        return binding.root
    }
}