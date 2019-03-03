package com.example.efpro.miscontactos.viewmodels

import android.app.Application
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.LiveData
import com.example.efpro.miscontactos.data.Contact
import com.example.efpro.miscontactos.data.ContactRepository

class ContactViewModel(application: android.app.Application): AndroidViewModel(application){
    private var repository : ContactRepository = ContactRepository(application)
    private var allContacts: LiveData<List<Contact>> = repository.getAllContacts()

    fun insert(contact: Contact){
        repository.insert(contact)
    }

    fun update(contact: Contact){
        repository.update((contact))
    }

    fun delete(contact: Contact){
        repository.delete(contact)
    }

    fun deleteAllContacts(){
        repository.deleteAllContacts()
    }

    fun getAllContacts(): LiveData<List<Contact>> {
        return allContacts
    }
}