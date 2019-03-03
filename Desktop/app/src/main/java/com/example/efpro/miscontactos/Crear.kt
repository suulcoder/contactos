package com.example.efpro.miscontactos

import android.app.Activity
import androidx.appcompat.app.AppCompatActivity
import android.content.ContentValues
import android.content.Intent
import android.graphics.Bitmap
import android.graphics.BitmapFactory
import android.os.Bundle
import android.provider.MediaStore
import android.view.Menu
import android.widget.Toast
import com.example.efpro.miscontactos.data.Contact
import kotlinx.android.synthetic.main.activity_crear.*
import kotlinx.android.synthetic.main.list_item.*
import java.io.FileNotFoundException
import android.graphics.drawable.BitmapDrawable
import androidx.core.app.NotificationCompat.getExtras
import java.io.ByteArrayOutputStream
import android.graphics.Bitmap.CompressFormat
import androidx.lifecycle.ViewModelProviders
import com.bumptech.glide.Glide
import com.example.efpro.miscontactos.Contactos.Companion.EDIT_CONTACT_REQUEST
import com.example.efpro.miscontactos.data.ContactDao
import com.example.efpro.miscontactos.data.ContactRepository
import com.example.efpro.miscontactos.viewmodels.ContactViewModel
import java.io.IOException
class Crear : AppCompatActivity() {

    val REQUEST_IMAGE_CAPTURE = 2
    val REQUEST_GALLERY = 1

   //Extraemos la nueva informacion
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
        setContentView(R.layout.activity_crear)
        number_picker_priority.minValue = 1
        number_picker_priority.maxValue = 10

        supportActionBar?.setHomeAsUpIndicator(R.drawable.adduser)

        //si la opcion es editar
        if(intent.getIntExtra(EXTRA_ID,-1) != -1 ) {
            nombre.setText(intent.getStringExtra(EXTRA_PHONE))
            telefono.setText(intent.getStringExtra(EXTRA_PHONE))
            correo.setText(intent.getStringExtra(EXTRA_MAIL))
            number_picker_priority.value = intent.getIntExtra(EXTRA_PRIORITY,1)
        }else{//o agregar uno nuevo
            //tittle = "AGREGAR"
        }

        tomar.setOnClickListener {
            tomarFoto()
        }

        cargar.setOnClickListener{
            galeria()
        }

        save.setOnClickListener{
            saveContact()
        }

        regreso.setOnClickListener{
            val intento = Intent(this, Contactos::class.java)//Redirigimos a contactos
            startActivity(intento)
            this.finish()
        }
    }




    private fun saveContact(){
        val bitmap = (imageView.drawable as BitmapDrawable).bitmap
        val stream = ByteArrayOutputStream()
        bitmap.compress(Bitmap.CompressFormat.PNG, 100, stream)
        val imagen = stream.toByteArray()
        if(nombre.text.toString().isNotEmpty() && telefono.text.toString().isNotEmpty() || correo.text.toString().isNotEmpty()){

            val data = Intent().apply {
                putExtra(EXTRA_NOMBRE,nombre.text.toString())
                putExtra(EXTRA_PHONE,telefono.text.toString())
                putExtra(EXTRA_MAIL,correo.text.toString())
                putExtra(EXTRA_PRIORITY,number_picker_priority.value)
                putExtra(EXTRA_IMAGE,imagen)//la agregamos al intent
                if (intent.getIntExtra(EXTRA_ID,-1) != -1){
                    putExtra(EXTRA_ID,intent.getIntExtra(EXTRA_ID,-1))
                }
            }
            setResult(Activity.RESULT_OK,data)
            finish()

        }else{
            Toast.makeText(this,"POR FAVOR LLENE TODOS LOS ESPACIOS", Toast.LENGTH_SHORT).show()
            return
        }
    }



    private fun tomarFoto() {
        Intent(MediaStore.ACTION_IMAGE_CAPTURE).also { takePictureIntent ->
            takePictureIntent.resolveActivity(packageManager)?.also {
                startActivityForResult(takePictureIntent, REQUEST_IMAGE_CAPTURE)
            }
        }
    }

    private fun galeria() {
        val galleryIntent = Intent(
            Intent.ACTION_PICK,
            MediaStore.Images.Media.EXTERNAL_CONTENT_URI
        )

        startActivityForResult(galleryIntent, 1)
    }


    override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {
        if (requestCode == REQUEST_IMAGE_CAPTURE && resultCode == RESULT_OK) {
            val imageBitmap = data?.extras!!.get("data") as Bitmap
            imageView.setImageBitmap(imageBitmap)
        }else if (requestCode == REQUEST_GALLERY) {
            //To get the contact photo from the gallery
            if (data != null) {
                val contentURI = data!!.data
                try {
                    val bitmap = MediaStore.Images.Media.getBitmap(this.contentResolver, contentURI)
                    imageView!!.setImageBitmap(bitmap)
                } catch (e: IOException) {
                    e.printStackTrace()
                }

            }

        }
    }

}
