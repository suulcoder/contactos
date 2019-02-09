package com.example.miscontactos

import android.content.ContentValues
import android.content.Intent
import android.graphics.Bitmap
import android.support.v7.app.AppCompatActivity
import android.os.Bundle
import android.provider.MediaStore
import android.widget.Toast
import com.example.miscontactos.Provider.ContactProvider
import com.example.miscontactos.models.ApplicationExt
import com.example.miscontactos.models.Contact
import kotlinx.android.synthetic.main.activity_contacto.*
import kotlinx.android.synthetic.main.activity_crear.*

class editContact : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_edit_contact)

        showNombre.setText(getIntent().getStringExtra("nombre"))//pedimos los datos del intent anterior
        var number = getIntent().getStringExtra("telefono")
        showPhone.setText(number)
        showMail.setText(getIntent().getStringExtra("mail"))
        setPhoto.setImageDrawable(getDrawable(getIntent().getIntExtra("foto",R.drawable.background)))

        tomar.setOnClickListener {
            tomarFoto()
        }

        home.setOnClickListener {
            //redirigimos los botones
            val intento = Intent(this, Contactos::class.java)//Redirigimos a contactos
            startActivity(intento)
            this.finish()
        }

        save.setOnClickListener {
            val Contact1 = Contact(nombre.getText().toString(),telefono.getText().toString(),correo.getText().toString(), imageView.getDrawable().getConstantState().hashCode())
            var accion = ApplicationExt.analize(Contact1)
            if (accion){
                Toast.makeText(this, "ESTE CONTACTO YA ESTA REGISTRADO.", Toast.LENGTH_LONG).show()
            }
            else{

                val list: Array<String> =  arrayOf(number)
                val values = ContentValues()
                values.put(ContactProvider.NAME, nombre.text.toString())
                values.put(ContactProvider.PHONE, telefono.text.toString())
                values.put(ContactProvider.EMAIL, correo.text.toString())
                values.put(ContactProvider.IMAGEN, imageView.drawable.getConstantState().hashCode())
                val uri = contentResolver.update(ContactProvider.CONTENT_URI, values,"phone=?",list)
                val intento1 = Intent(this,Contacto::class.java)//creamos un nuevo contacto y lo agregamos y enviamos a su respectivo activity
                intento1.putExtra("nombre",nombre.text.toString())
                intento1.putExtra("telefono",telefono.text.toString())
                intento1.putExtra("mail",correo.text.toString())
                intento1.putExtra("foto",imageView.drawable.getConstantState().hashCode())
                startActivity(intento1)
                onStop()
            }
        }

    }

    val REQUEST_IMAGE_CAPTURE = 1

    private fun tomarFoto() {
        Intent(MediaStore.ACTION_IMAGE_CAPTURE).also { takePictureIntent ->
            takePictureIntent.resolveActivity(packageManager)?.also {
                startActivityForResult(takePictureIntent, REQUEST_IMAGE_CAPTURE)
            }
        }
    }

    override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {
        if (requestCode == REQUEST_IMAGE_CAPTURE && resultCode == RESULT_OK) {
            val imageBitmap = data?.extras!!.get("data") as Bitmap
            imageView.setImageBitmap(imageBitmap)
        }
    }
}
