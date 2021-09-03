package com.example.contactbook.model

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.*
import androidx.fragment.app.Fragment
import androidx.lifecycle.ViewModelProvider
import androidx.lifecycle.map
import androidx.recyclerview.widget.RecyclerView
import com.example.contactbook.R
import com.example.contactbook.contract
import com.example.contactbook.databinding.ActivityMainBinding
import com.example.contactbook.databinding.FragmentContactsListBinding
import com.example.contactbook.model.ContactsListFragment.Companion.KEY_INFO

class ContactsListFragment : Fragment() {

    private lateinit var binding: FragmentContactsListBinding
    lateinit var viewModel: ContactViewModel

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        binding = FragmentContactsListBinding.inflate(inflater, container, false)
        viewModel = ViewModelProvider(requireActivity()).get(ContactViewModel::class.java)

        binding.contactAddButton.setOnClickListener {
            contract().addContact()
        }
        setupListView()

        return binding.root
    }

    fun setupListView() {
        val contacts = viewModel.contacts
        val data = contacts?.map { contact ->
            mapOf(
                KEY_NAME to contact.name,
                KEY_INFO to contact.info
            )
        }
        val adapter = SimpleAdapter(
            context,
            data,
            R.layout.item_contact,
            arrayOf(KEY_NAME, KEY_INFO),
            intArrayOf(R.id.itemContactName, R.id.itemContactNumber)
        )

        binding.contactListView.adapter = adapter

        binding.contactListView.onItemClickListener =
            AdapterView.OnItemClickListener { _, _, i, _ ->
                val currentContact = viewModel.contacts[i]
                contract().contactInfo(currentContact)
            }
    }

    companion object {
        @JvmStatic
        val KEY_NAME = "name"
        @JvmStatic
        val KEY_INFO = "info"
    }

}