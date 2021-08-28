package com.example.contactbook

import android.os.Bundle
import android.text.Layout
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.constraintlayout.widget.ConstraintLayout
import androidx.core.os.bundleOf
import androidx.fragment.app.Fragment
import com.example.contactbook.databinding.FragmentContactDetailsBinding
import com.example.contactbook.model.Contact

class ContactDetailsFragment:Fragment() {

    private lateinit var binding:FragmentContactDetailsBinding
    val contact:Contact
        get() = requireArguments().getSerializable(ADD_CONTACT) as Contact


    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        binding = FragmentContactDetailsBinding.inflate(inflater,container,false)
        binding.contactDetailsName.text = contact.name
        binding.contactDetailsInfo.text = contact.info
        binding.contactDeleteButton.setOnClickListener {
            contract().contactDelete() }
        return binding.root
    }

    companion object{
        private const val ADD_CONTACT = "ADD_CONTACT"
        fun newInstance(contact: Contact): ContactDetailsFragment {
            val fragment = ContactDetailsFragment()
            fragment.arguments = bundleOf(ADD_CONTACT to contact)
            return fragment
        }
    }
}