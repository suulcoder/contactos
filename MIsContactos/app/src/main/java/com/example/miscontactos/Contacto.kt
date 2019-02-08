package com.example.miscontactos

import android.content.Intent
import android.net.Uri
import android.support.v7.app.AppCompatActivity
import android.os.Bundle
import android.support.design.widget.Snackbar
import android.util.Log
import kotlinx.android.synthetic.main.activity_contacto.*
import kotlinx.android.synthetic.main.activity_correo.*
import kotlinx.android.synthetic.main.activity_list_item.*

class Contacto : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_contacto)

        showNombre.setText(getIntent().getStringExtra("nombre"))//pedimos los datos del intent anterior
        var number = getIntent().getStringExtra("telefono")
        showPhone.setText(number)
        showMail.setText(getIntent().getStringExtra("mail"))
        setPhoto.setImageDrawable(getDrawable(getIntent().getIntExtra("foto",R.drawable.background)))

        home.setOnClickListener{//redirigimos los botones
            val intento = Intent(this, Contactos::class.java)//Redirigimos a contactos
            startActivity(intento)
            this.finish()
        }

        goMail.setOnClickListener{//enviamos a correo
            val emailIntent = Intent(Intent.ACTION_SEND)
            //Making the intent for email
            emailIntent.data = Uri.parse("mailto:")
            emailIntent.type = "text/plain"
            emailIntent.putExtra(Intent.EXTRA_EMAIL, getIntent().getStringExtra("mail"))
            val nombre = getIntent().getStringExtra(("nombre"))
            emailIntent.putExtra(Intent.EXTRA_TEXT, "Mi nombre es $nombre, y mi teléfono es $number")
            try {
                startActivity(Intent.createChooser(emailIntent, "Send mail..."))
                Log.i("Enviando correo...", "")
            } catch (ex: android.content.ActivityNotFoundException) {
                Snackbar.make(parent_view, "No hay cliente de email...", Snackbar.LENGTH_LONG).show()
            }
        }

        call.setOnClickListener{
            try {
                val intentcall = Intent(Intent.ACTION_CALL,Uri.fromParts("tel",number,null))//si nos otorgan los permisos
                startActivity(intentcall)
            }
            catch (e: Exception){
                val intentcall = Intent(Intent.ACTION_DIAL,Uri.fromParts("tel",number,null))//si no nos otorgan los perimosos
                startActivity(intentcall)
            }

        }

    }
}
