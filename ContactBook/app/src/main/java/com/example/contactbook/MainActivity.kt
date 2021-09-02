package com.example.contactbook

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.Button
import androidx.activity.viewModels
import androidx.lifecycle.ViewModelProvider
import com.example.contactbook.model.*

class MainActivity : AppCompatActivity(), AppContract {

    lateinit var viewModel: ContactViewModel

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        viewModel = ViewModelProvider(this).get(ContactViewModel::class.java)

        if (savedInstanceState == null) {
            supportFragmentManager.beginTransaction()
                .add(R.id.mainFragment, ContactsListFragment())
                .commit()
        }

    }

//    override val contactsList: ContactsList
//        get() = (applicationContext as App).contactList

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

    override fun addContact() {
        val fragment = ContactAddFragment()
        supportFragmentManager.beginTransaction()
            .replace(R.id.mainFragment, fragment)
            .addToBackStack(null)
            .commit()
    }

    override fun contactListShow() {
        supportFragmentManager.beginTransaction()
            .replace(R.id.mainFragment, ContactsListFragment())
            .addToBackStack(null)
            .commit()
    }

    override fun contactInfoLayPhone() {
        supportFragmentManager.beginTransaction()
            .replace(R.id.contactAddLay, ContactAddPhoneFragment())
            .commit()
    }

    override fun contactInfoLayEmail() {
        supportFragmentManager.beginTransaction()
            .replace(R.id.contactAddLay, ContactAddEmailFragment())
            .commit()
    }
}