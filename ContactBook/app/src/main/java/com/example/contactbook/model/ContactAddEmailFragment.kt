package com.example.contactbook.model

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import com.example.contactbook.databinding.FragmentContactAddEmailBinding

class ContactAddEmailFragment : Fragment() {
    private lateinit var binding: FragmentContactAddEmailBinding
    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        binding = FragmentContactAddEmailBinding.inflate(inflater, container, false)
        return binding.root
    }
}