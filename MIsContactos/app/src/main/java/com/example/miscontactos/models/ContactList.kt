package com.example.miscontactos.models

interface ContactList {

    val contactlist: ArrayList<Contact> // Contactos

    fun add(element: Contact) // Agregar elemento
}