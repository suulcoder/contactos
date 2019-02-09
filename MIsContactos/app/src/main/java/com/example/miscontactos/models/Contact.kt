package com.example.miscontactos.models

data class Contact(var nombre:String, var tele:String, var email:String, var image: Int)    {

    //Compara si dos objetos son identicos
    fun compare(contacto: Contact): Boolean {
        return (this.nombre==contacto.nombre) && (this.tele==contacto.tele) && (this.email==contacto.email) && (this.image==contacto.image)//devolvemos el resultado
    }

}