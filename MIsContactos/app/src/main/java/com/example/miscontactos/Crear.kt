package com.example.miscontactos

import android.content.ContentValues
import android.content.Intent
import android.support.v7.app.AppCompatActivity
import android.os.Bundle
import android.widget.EditText
import android.widget.Toast
import com.example.miscontactos.Provider.ContactProvider
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



        save.setOnClickListener {
            val values = ContentValues()
            values.put(ContactProvider.NAME, nombre.text.toString())
            values.put(ContactProvider.PHONE, telefono.text.toString())
            values.put(ContactProvider.EMAIL, correo.text.toString())
            values.put(ContactProvider.IMAGEN, R.drawable.fondo3)
            val uri = contentResolver.insert(ContactProvider.CONTENT_URI, values)
            val Contact1 = Contact(nombre.getText().toString(),telefono.getText().toString(),correo.getText().toString(), R.drawable.fondo3)
            var accion = ApplicationExt.analize(Contact1)
            if (accion){
                Toast.makeText(this, "ESTE CONTACTO YA ESTA REGISTRADO.",Toast.LENGTH_LONG).show()
            }
            else{
                val intento1 = Intent(this,Contacto::class.java)//creamos un nuevo contacto y lo agregamos y enviamos a su respectivo activity
                intento1.putExtra("nombre",nombre.text.toString())
                intento1.putExtra("telefono",telefono.text.toString())
                intento1.putExtra("mail",correo.text.toString())
                intento1.putExtra("foto",R.drawable.fondo3)
                startActivity(intento1)
                onStop()
            }


        }


    }
}
