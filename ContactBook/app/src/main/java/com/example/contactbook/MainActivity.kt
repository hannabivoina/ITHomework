package com.example.contactbook

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import com.example.contactbook.model.Contact
import com.example.contactbook.model.ContactsList
import com.example.contactbook.model.ContactsListFragment

class MainActivity : AppCompatActivity(), AppContract {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        if (savedInstanceState == null){
            supportFragmentManager.beginTransaction()
                .add(R.id.mainFragment, ContactsListFragment())
                .commit()
        }
    }

    override val contactsList: ContactsList
        get() = (applicationContext as App).contactList

    override fun contactInfo(contact: Contact) {
        val fragment = ContactDetailsFragment.newInstance(contact)
        supportFragmentManager.beginTransaction()
            .replace(R.id.mainFragment, fragment)
            .addToBackStack(null)
            .commit()
    }

    override fun contactDelete() {
        val fragment = ContactDeleteFragment()
        supportFragmentManager.beginTransaction()
            .replace(R.id.contactDetailsDeleteLay, fragment)
            .addToBackStack(null)
            .commit()
    }

}