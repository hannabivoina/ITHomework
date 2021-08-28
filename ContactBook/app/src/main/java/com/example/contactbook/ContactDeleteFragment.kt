package com.example.contactbook

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import com.example.contactbook.databinding.FragmentContactDeleteBinding
import com.example.contactbook.databinding.FragmentContactDetailsBinding

class ContactDeleteFragment:Fragment() {

    private lateinit var binding: FragmentContactDeleteBinding
    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        binding = FragmentContactDeleteBinding.inflate(inflater,container,false)
        binding.layoutDelete
        return binding.root
    }
}