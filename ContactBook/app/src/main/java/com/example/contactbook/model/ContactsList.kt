package com.example.contactbook.model

class ContactsList {
    var contacts: List<Contact> = (1..10).map { Contact(
        id = it,
        name = "Hanna",
        info = "+375291910240"
    )
    }
}