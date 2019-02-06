package com.example.miscontactos

import android.content.Intent
import android.support.v7.app.AppCompatActivity
import android.os.Bundle
import android.widget.EditText
import com.example.miscontactos.models.ApplicationExt
import com.example.miscontactos.models.Contact
import kotlinx.android.synthetic.main.activity_crear.*

class Crear : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_crear)

        home.setOnClickListener {
            //redirigimos los botones
            val intento = Intent(this, Contactos::class.java)//Redirigimos a contactos
            startActivity(intento)
            onStop()
        }

        var nombre = findViewById(R.id.nombre) as EditText
        var telefono = findViewById(R.id.telefono) as EditText
        var mail = findViewById(R.id.correo) as EditText

        save.setOnClickListener {
            val Contact1 = Contact(nombre.getText().toString(),telefono.getText().toString(),mail.getText().toString(), R.drawable.fondo3)
            ApplicationExt.add(Contact1)
            val intento1 = Intent(this,Contacto::class.java)//creamos un nuevo contacto y lo agregamos y enviamos a su respectivo activity
            intento1.putExtra("nombre",Contact1.nombre)
            intento1.putExtra("telefono",Contact1.tele)
            intento1.putExtra("mail",Contact1.email)
            intento1.putExtra("foto",Contact1.image)
            startActivity(intento1)
            onStop()

        }


    }
}
