package com.example.efpro.miscontactos

import android.app.Activity
import android.content.Intent
import android.content.Intent.getIntent
import android.net.Uri
import android.os.Bundle
import android.util.Log
import androidx.appcompat.app.AppCompatActivity
import androidx.core.content.ContextCompat.startActivity
import androidx.lifecycle.ViewModelProviders
import com.example.efpro.miscontactos.Adapters.ContactAdapter
import com.example.efpro.miscontactos.R.id.edit_query
import com.example.efpro.miscontactos.R.id.parent_view
import com.example.efpro.miscontactos.data.Contact
import com.example.efpro.miscontactos.viewmodels.ContactViewModel
import com.google.android.material.snackbar.Snackbar
import kotlinx.android.synthetic.main.activity_contacto.*
import kotlinx.android.synthetic.main.activity_correo.*
import kotlinx.android.synthetic.main.activity_crear.*
import com.bumptech.glide.Glide
import kotlinx.android.synthetic.main.list_item.*

class Contacto : AppCompatActivity() {

    private var Id:Int = -1
    private lateinit var contactViewModel: ContactViewModel
    private lateinit var Image: ByteArray


    companion object {
        const val EXTRA_ID = "com.example.efpro.miscontactos.EXTRA_ID"
        const val EXTRA_NOMBRE = "com.example.efpro.miscontactos.EXTRA_NOMBRE"
        const val EXTRA_PHONE = "com.example.efpro.miscontactos.EXTRA_PHONE"
        const val EXTRA_MAIL = "com.example.efpro.miscontactos.EXTRA_MAIL"
        const val EXTRA_PRIORITY = "com.example.efpro.miscontactos.EXTRA_PRIORITY"
        const val EXTRA_IMAGE = "com.example.efpro.miscontactos.EXTRA:IMAGE"

    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_contacto)
        showNombre.setText(intent.getStringExtra(Crear.EXTRA_NOMBRE))//pedimos los datos del intent anterior
        showPhone.setText(intent.getStringExtra(Crear.EXTRA_PHONE))
        showMail.setText(intent.getStringExtra(Crear.EXTRA_MAIL))
        val number = intent.getStringExtra(Crear.EXTRA_PHONE)

        contactViewModel = ViewModelProviders.of(this).get(ContactViewModel::class.java)
        Id = intent.getIntExtra(Crear.EXTRA_ID, 1)
        Image = intent.getByteArrayExtra(Crear.EXTRA_IMAGE)

        home.setOnClickListener{//redirigimos los botones
            val intento = Intent(this, Contactos::class.java)//Redirigimos a contactos
            startActivity(intento)
            this.finish()
        }

        edit.setOnClickListener{
                val intent = Intent(baseContext, Crear::class.java)
                intent.putExtra(Crear.EXTRA_NOMBRE, intent.getStringExtra(Crear.EXTRA_NOMBRE))
                intent.putExtra(Crear.EXTRA_PHONE, intent.getStringExtra(Crear.EXTRA_PHONE))
                intent.putExtra(Crear.EXTRA_MAIL, intent.getStringExtra(Crear.EXTRA_MAIL))
                intent.putExtra(Crear.EXTRA_ID, intent.getStringExtra(Crear.EXTRA_ID))
                intent.putExtra(Crear.EXTRA_IMAGE, intent.getStringExtra(Crear.EXTRA_IMAGE))
                intent.putExtra(Crear.EXTRA_PRIORITY, intent.getStringExtra(Crear.EXTRA_PRIORITY))
                startActivityForResult(intent, Contactos.EDIT_CONTACT_REQUEST)
        }


        home.setOnClickListener{
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


    override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {
        super.onActivityResult(requestCode, resultCode, data)

        if (requestCode == Contactos.EDIT_CONTACT_REQUEST && resultCode == Activity.RESULT_OK) {
            val id = data?.getIntExtra(Crear.EXTRA_ID, -1)

            val updateContact = Contact(
                data!!.getStringExtra(Crear.EXTRA_NOMBRE),
                data!!.getStringExtra(Crear.EXTRA_PHONE),
                data!!.getStringExtra(Crear.EXTRA_MAIL),
                data.getIntExtra(Crear.EXTRA_PRIORITY, 1),data.getByteArrayExtra(Crear.EXTRA_IMAGE)
            )

            updateContact.image = data.getByteArrayExtra(Crear.EXTRA_IMAGE)
            updateContact.id = id!!
            contactViewModel.update(updateContact)
            showNombre.text = updateContact.nombre
            showPhone.text = updateContact.tele
            showMail.text = updateContact.email
            updateContact.priority

            Id = updateContact.id
            Image = updateContact.image!!

            Glide.with(this).load(Image).into(imageView)


        } else {
            //do noting
        }


    }
}
