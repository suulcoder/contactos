package com.example.miscontactos

import android.content.Intent
import android.net.Uri
import android.support.v7.app.AppCompatActivity
import android.os.Bundle
import android.view.View
import android.widget.AdapterView
import android.widget.ListView
import android.widget.Toast
import com.example.miscontactos.Provider.ContactProvider
import com.example.miscontactos.models.ApplicationExt
import com.example.miscontactos.models.Contact
import kotlinx.android.synthetic.main.activity_contacto.*
import kotlinx.android.synthetic.main.activity_contactos.*

class Contactos : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_contactos)

        var listview = findViewById(R.id.listacontactos) as ListView


        val URL = "content://com.example.miscontactos.Provider.ContactProvider"

        val contacts = Uri.parse(URL)
        val c = contentResolver.query(contacts, null, null, null, "name")


        if (c.moveToFirst()) {//Obtenemos la base de datos
            do {
                val Contact = Contact(c.getString(c.getColumnIndex(ContactProvider.NAME)),c.getString(c.getColumnIndex(
                    ContactProvider.PHONE)),c.getString(c.getColumnIndex(ContactProvider.EMAIL)), c.getInt(c.getColumnIndex(
                    ContactProvider.IMAGEN)))
                    ApplicationExt.add(Contact)//Agregmaos al array global
            } while (c.moveToNext())
        }
        c.close();


        val adapter = CustomAdapter(applicationContext, ApplicationExt.contactlist)//nuestro adapter personalizado
        listview.setAdapter(adapter)//lo agregamos a nuestro list view

        Agregar.setOnClickListener{//redirigimos los botones
            val intento = Intent(this, Crear::class.java)//Redirigimos a contactos
            startActivity(intento)
            onStop()
        }

        listview.setOnItemClickListener { parent, view, position, id ->//redirigimos a la plantilla dle contacto
            val intento1 = Intent(this,Contacto::class.java)
            intento1.putExtra("nombre",ApplicationExt.contactlist[position].nombre)
            intento1.putExtra("telefono",ApplicationExt.contactlist[position].tele)
            intento1.putExtra("mail",ApplicationExt.contactlist[position].email)
            intento1.putExtra("foto",ApplicationExt.contactlist[position].image)
            startActivity(intento1)
            onStop()
        }

        listview.onItemLongClickListener = object : AdapterView.OnItemLongClickListener{//SI lo mantiene sostenido se elimina y notifica
        override fun onItemLongClick(parent: AdapterView<*>?, view: View?, position: Int, id: Long): Boolean {
            val itemValue = listview.getItemAtPosition(position) as String
            Toast.makeText(applicationContext,"Se ha eliminado el elemento " + ApplicationExt.contactlist[position] + " al carrito de compra",Toast.LENGTH_SHORT).show()//Informamos el elemento que se elimino
            ApplicationExt.del(position)//lo eliminamos del array
            adapter.notifyDataSetChanged()//Actualizamos
            /*Ahora lo eliminamos de la base de datos*/

            val uri = contentResolver.delete(ContactProvider.CONTENT_URI)
            return true
        }
        }

    }

}
