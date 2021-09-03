package com.example.contactbook

import androidx.fragment.app.Fragment
import com.example.contactbook.model.Contact

fun Fragment.contract(): AppContract = requireActivity() as AppContract

interface AppContract {
    fun contactInfo(contact: Contact)
    fun addContact()
    fun contactListShow()
    fun contactInfoLayPhone()
    fun contactInfoLayEmail()
}