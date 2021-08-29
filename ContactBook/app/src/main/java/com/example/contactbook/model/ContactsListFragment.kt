package com.example.contactbook.model

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.*
import androidx.fragment.app.Fragment
import androidx.recyclerview.widget.RecyclerView
import com.example.contactbook.R
import com.example.contactbook.contract
import com.example.contactbook.databinding.ActivityMainBinding
import com.example.contactbook.databinding.FragmentContactsListBinding
import com.example.contactbook.model.ContactsListFragment.Companion.KEY_INFO

class ContactsListFragment : Fragment(){

    private lateinit var binding: FragmentContactsListBinding

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        binding = FragmentContactsListBinding.inflate(inflater, container, false)
        setupListView()

//        val adapter = ArrayAdapter(requireContext(),android.R.layout.simple_list_item_1,contacts)
//        binding.contactsListView.adapter = adapter
//        binding.contactsListView.setOnItemClickListener { _, _, i, _ ->
//            val currentContact = adapter.getItem(i)!!
//            contract().contactInfo(currentContact)
//        }
        return binding.root
    }


    fun setupListView(){
        val contacts = contract().contactsList.contacts
        val data = (0..contacts.size).map {
            mapOf(
                KEY_NAME to "contacts.get(it).name",
                KEY_INFO to "contacts.get(it).info"
            )
        }
        val adapter = SimpleAdapter(
            context,
            data,
            R.layout.item_contact,
            arrayOf(KEY_NAME, KEY_INFO),
            intArrayOf(R.id.itemContactName,R.id.itemContactNumber))

        binding.contactListView.adapter = adapter

        binding.contactListView.onItemClickListener = AdapterView.OnItemClickListener{ _, _, i, _ ->
            val selectedContact = data[i]
            val currentContact = contacts[i]
            contract().contactInfo(currentContact)
        }

    }
    companion object{
        @JvmStatic val KEY_NAME = "name"
        @JvmStatic val KEY_INFO = "info"
    }

}