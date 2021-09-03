package com.example.contactbook

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.EditText
import android.widget.Toast
import androidx.fragment.app.Fragment
import androidx.lifecycle.ViewModelProvider
import com.example.contactbook.databinding.FragmentContactAddBinding
import com.example.contactbook.model.Contact
import com.example.contactbook.model.ContactViewModel

class ContactAddFragment : Fragment() {

    private lateinit var binding: FragmentContactAddBinding
    lateinit var viewModel: ContactViewModel

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        binding = FragmentContactAddBinding.inflate(inflater, container, false)
        contract().contactInfoLayPhone()
        viewModel = ViewModelProvider(requireActivity()).get(ContactViewModel::class.java)


        binding.contactSaveButton.setOnClickListener {
            if (binding.contactAddName.text.toString() == "" || addInfo() == "") {
                Toast.makeText(context, "Please add your name/contact", Toast.LENGTH_SHORT).show()
            } else {
                view?.findViewById<EditText>(R.id.addEmailEditText)
                val newContact = Contact(
                    id = viewModel.contacts.size,
                    name = binding.contactAddName.text.toString(),
                    info = addInfo()
                )

                val newList: MutableList<Contact> = mutableListOf(newContact)
                newList.addAll(viewModel.contacts)
                viewModel.contacts = newList.toList()

                Toast.makeText(context, "Added", Toast.LENGTH_LONG).show()

                contract().contactListShow()
            }
        }
        binding.contactAddSelectEmail.setOnClickListener {
            contract().contactInfoLayEmail()
        }
        binding.contactAddSelectPhone.setOnClickListener {
            contract().contactInfoLayPhone()
        }
        return binding.root
    }

    fun addInfo(): String {
        lateinit var info: String
        if (binding.contactAddSelectEmail.isChecked) {
            info = view?.findViewById<EditText>(R.id.addEmailEditText)?.text.toString()
        } else {
            info = view?.findViewById<EditText>(R.id.addPhoneEditText)?.text.toString()
        }
        return info
    }

}