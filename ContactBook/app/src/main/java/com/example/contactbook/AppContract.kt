package com.example.contactbook

import androidx.fragment.app.Fragment
import com.example.contactbook.model.Contact
import com.example.contactbook.model.ContactsList

fun Fragment.contract(): AppContract = requireActivity() as AppContract

interface AppContract {
    val contactsList: ContactsList
    fun contactInfo(contact: Contact)
    fun contactDelete()
}