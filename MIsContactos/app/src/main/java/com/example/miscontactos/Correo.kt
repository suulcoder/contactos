package com.example.miscontactos

import android.content.BroadcastReceiver
import android.content.Context
import android.content.Intent
import android.support.v7.app.AppCompatActivity
import android.os.Bundle
import android.widget.EditText
import android.widget.Toast
import com.example.miscontactos.models.ApplicationExt
import com.example.miscontactos.models.Contact
import kotlinx.android.synthetic.main.activity_contacto.*
import kotlinx.android.synthetic.main.activity_correo.*
import kotlinx.android.synthetic.main.activity_crear.*

class Correo : AppCompatActivity() {


    override fun onCreate(savedInstanceState: Bundle?) {//empezamos activity
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_correo)

        var mail = getIntent().getStringExtra("mail")
        var mensaje = getIntent().getStringExtra("message")
        var number = getIntent().getStringExtra("numero")

        send.setOnClickListener{}

        mailto.setText(mail)
        text.setText(mensaje)


            var recipent = findViewById(R.id.recipent) as EditText
            var mailto = findViewById(R.id.mailto) as EditText
            var subject = findViewById(R.id.subject) as EditText
            var message = findViewById(R.id.text) as EditText

        send.setOnClickListener {//enviamos correo
            val intento1 = Intent(this, Contactos::class.java)
            Toast.makeText(this, message.getText().toString() +  "enviado desde " + recipent.getText().toString() + " hacia " + mailto.getText().toString(),Toast.LENGTH_LONG).show()
            startActivity(intento1)
            onStop()
        }

    }
}
