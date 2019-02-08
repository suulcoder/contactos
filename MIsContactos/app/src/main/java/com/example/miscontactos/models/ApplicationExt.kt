package com.example.miscontactos.models

import android.app.Application

class ApplicationExt: Application() {

    companion object:ContactList{

        override val contactlist: ArrayList<Contact> =  ArrayList()

        override fun add(element: Contact): Boolean {//agregar un elemento
            var control =  analize(element)//analizamos
            if(control==false){//si no se encuentra
                contactlist.add(element)//lo agregamos
            }
            return(control)//devolvemos el resultado
        }

        fun analize(element: Contact): Boolean {//Analiza si el elemento ya esta dentro o no
            var control = false
            for (item in contactlist){
                if(element.compare(item)){
                    control = true
                }
            }
            return control//devolvemos el resultado
        }

        override fun del(elementIndex: Int) {
            contactlist.removeAt(elementIndex)
        }
    }

}