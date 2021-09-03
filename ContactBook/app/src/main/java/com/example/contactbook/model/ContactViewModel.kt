package com.example.contactbook.model

import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel

class ContactViewModel : ViewModel() {
    var contacts = listOf<Contact>()

    val currentContacts: MutableLiveData<MutableList<Contact>> by lazy {
        MutableLiveData<MutableList<Contact>>()
    }
}