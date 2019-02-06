package com.example.miscontactos

import android.content.Intent
import android.support.v7.app.AppCompatActivity
import android.os.Bundle
import android.view.View
import android.widget.AdapterView
import android.widget.ListView
import android.widget.Toast
import com.example.miscontactos.models.ApplicationExt
import kotlinx.android.synthetic.main.activity_contacto.*
import kotlinx.android.synthetic.main.activity_contactos.*

class Contactos : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_contactos)

        var listview = findViewById(R.id.listacontactos) as ListView

        listview.adapter = CustomAdapter(applicationContext, ApplicationExt.contactlist)

        Agregar.setOnClickListener{//redirigimos los botones
            val intento = Intent(this, Crear::class.java)//Redirigimos a contactos
            startActivity(intento)
            onStop()
        }

        listview.setOnItemClickListener { parent, view, position, id ->
            val intento1 = Intent(this,Contacto::class.java)
            intento1.putExtra("nombre",ApplicationExt.contactlist[position].nombre)
            intento1.putExtra("telefono",ApplicationExt.contactlist[position].tele)
            intento1.putExtra("mail",ApplicationExt.contactlist[position].email)
            intento1.putExtra("foto",ApplicationExt.contactlist[position].image)
            startActivity(intento1)
            onStop()
        }


    }

}
