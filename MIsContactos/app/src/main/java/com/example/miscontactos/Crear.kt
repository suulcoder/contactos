package com.example.miscontactos

import android.app.Activity
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
import kotlinx.android.synthetic.main.activity_crear.*
import android.graphics.BitmapFactory
import java.io.FileNotFoundException


class Crear : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_crear)

        regreso.setOnClickListener {
            //redirigimos los botones
            val intento = Intent(this, Contactos::class.java)//Redirigimos a contactos
            startActivity(intento)
            this.finish()
        }

        tomar.setOnClickListener {
            tomarFoto()
        }

        cargar.setOnClickListener {
            val photoPickerIntent = Intent(Intent.ACTION_PICK)
            photoPickerIntent.type = "image/*"
            startActivityForResult(photoPickerIntent, 1)
        }

        save.setOnClickListener {
            val Contact1 = Contact(nombre.getText().toString(),telefono.getText().toString(),correo.getText().toString(), R.drawable.fondo)
            var accion = ApplicationExt.analize(Contact1)
            if (accion){
                Toast.makeText(this, "ESTE CONTACTO YA ESTA REGISTRADO.",Toast.LENGTH_LONG).show()
            }
            else{
                val values = ContentValues()
                values.put(ContactProvider.NAME, nombre.text.toString())
                values.put(ContactProvider.PHONE, telefono.text.toString())
                values.put(ContactProvider.EMAIL, correo.text.toString())
                values.put(ContactProvider.IMAGEN, R.drawable.fondo)
                val uri = contentResolver.insert(ContactProvider.CONTENT_URI, values)
                val intento1 = Intent(this,Contacto::class.java)//creamos un nuevo contacto y lo agregamos y enviamos a su respectivo activity
                intento1.putExtra("nombre",nombre.text.toString())
                intento1.putExtra("telefono",telefono.text.toString())
                intento1.putExtra("mail",correo.text.toString())
                intento1.putExtra("foto",R.drawable.fondo)
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
/*
    override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {
        if (requestCode == REQUEST_IMAGE_CAPTURE && resultCode == RESULT_OK) {
            val imageBitmap = data?.extras!!.get("data") as Bitmap
            imageView.setImageBitmap(imageBitmap)
        }
    }
*/
    override fun onActivityResult(reqCode: Int, resultCode: Int, data: Intent?) {
        super.onActivityResult(reqCode, resultCode, data)


        if (resultCode == Activity.RESULT_OK) {
            try {
                val imageUri = data!!.data
                val imageStream = contentResolver.openInputStream(imageUri!!)
                val selectedImage = BitmapFactory.decodeStream(imageStream)
                imageView.setImageBitmap(selectedImage)
            } catch (e: FileNotFoundException) {
                e.printStackTrace()
                Toast.makeText(this, "Error inesperado", Toast.LENGTH_LONG).show()
            }

        } else {
            Toast.makeText(this, "No se ha seleccionado imagen", Toast.LENGTH_LONG).show()
        }
    }
}
