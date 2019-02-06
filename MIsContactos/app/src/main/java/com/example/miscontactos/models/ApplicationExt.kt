package com.example.miscontactos.models

import android.app.Application

class ApplicationExt: Application() {

    companion object:ContactList{

        override val contactlist: ArrayList<Contact> =  ArrayList()

        override fun add(element: Contact) {//agregar un elemento
            contactlist.add(element)
        }
    }

}