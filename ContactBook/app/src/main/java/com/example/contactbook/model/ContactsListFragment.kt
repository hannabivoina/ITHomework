package com.example.contactbook.model

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ArrayAdapter
import androidx.fragment.app.Fragment
import com.example.contactbook.R
import com.example.contactbook.contract
import com.example.contactbook.databinding.ActivityMainBinding
import com.example.contactbook.databinding.FragmentContactsListBinding

class ContactsListFragment : Fragment(){

    private lateinit var binding: FragmentContactsListBinding

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        binding = FragmentContactsListBinding.inflate(inflater, container, false)
        val contacts = contract().contactsList.contacts
        val adapter = ArrayAdapter(requireContext(),android.R.layout.simple_list_item_1,contacts)
        binding.contactsListView.adapter = adapter
        binding.contactsListView.setOnItemClickListener { _, _, i, _ ->
            val currentContact = adapter.getItem(i)!!
            contract().contactInfo(currentContact)
        }
        return binding.root
    }

}