package com.example.miscontactos

import android.content.Intent
import android.net.Uri
import android.support.v7.app.AppCompatActivity
import android.os.Bundle
import kotlinx.android.synthetic.main.activity_contacto.*
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
            onStop()
        }

        goMail.setOnClickListener{//enviamos a correo
            val intent = Intent(this, Correo::class.java)
            intent.putExtra("mail", getIntent().getStringExtra("mail"))
            intent.putExtra("numero", getIntent().getStringExtra("telefono"))
            intent.putExtra("message" , "Mi nombre es " + getIntent().getStringExtra("nombre")+ ", y mi telefono es " + number)
            startActivity(intent)
            onStop()
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
