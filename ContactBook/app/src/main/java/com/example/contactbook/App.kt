package com.example.contactbook

import android.app.Application
import com.example.contactbook.model.ContactsList

class App:Application() {
    var contactList = ContactsList()
}