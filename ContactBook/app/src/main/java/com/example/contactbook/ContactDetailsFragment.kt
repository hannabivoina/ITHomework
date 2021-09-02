package com.example.contactbook

import android.app.AlertDialog
import android.os.Bundle
import android.text.Layout
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.appcompat.widget.AlertDialogLayout
import androidx.constraintlayout.widget.ConstraintLayout
import androidx.core.os.bundleOf
import androidx.fragment.app.Fragment
import androidx.lifecycle.ViewModelProvider
import com.example.contactbook.databinding.FragmentContactDetailsBinding
import com.example.contactbook.model.Contact
import com.example.contactbook.model.ContactViewModel
import kotlinx.coroutines.flow.callbackFlow

class ContactDetailsFragment : Fragment() {

    private lateinit var binding: FragmentContactDetailsBinding
    val contact: Contact
        get() = requireArguments().getSerializable(ADD_CONTACT) as Contact
    lateinit var viewModel: ContactViewModel


    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        binding = FragmentContactDetailsBinding.inflate(inflater, container, false)
        viewModel = ViewModelProvider(requireActivity()).get(ContactViewModel::class.java)

        binding.contactDetailsName.text = contact.name
        binding.contactDetailsInfo.text = contact.info
        binding.contactDeleteButton.setOnClickListener {
            val dialog = AlertDialog.Builder(context)
                .setTitle(resources.getString(R.string.deleteText1))
                .setMessage(resources.getString(R.string.deleteText2))
                .setPositiveButton("OK") { dialog, which -> deleteContact() }
                .setNegativeButton("CANCEL") { dialog, which -> dialog.dismiss() }
                .create()
            dialog.show()
        }
        return binding.root
    }

    companion object {
        private const val ADD_CONTACT = "ADD_CONTACT"
        fun newInstance(contact: Contact): ContactDetailsFragment {
            val fragment = ContactDetailsFragment()
            fragment.arguments = bundleOf(ADD_CONTACT to contact)
            return fragment
        }
    }

    fun deleteContact() {
        var newList: MutableList<Contact> = mutableListOf()
        newList.addAll(viewModel.contacts)
        newList.remove(contact)
        viewModel.contacts = newList.toList()
        Toast.makeText(context, "Deleted", Toast.LENGTH_LONG).show()
        contract().contactListShow()
    }
}