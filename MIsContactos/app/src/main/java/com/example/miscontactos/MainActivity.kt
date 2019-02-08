package com.example.miscontactos

import android.Manifest
import android.content.ContentValues
import android.content.Intent
import android.content.pm.PackageManager
import android.support.v7.app.AppCompatActivity
import android.os.Bundle
import android.support.v4.app.ActivityCompat
import android.support.v4.content.ContextCompat
import android.widget.ArrayAdapter
import android.widget.ListView
import com.example.miscontactos.Provider.ContactProvider
import com.example.miscontactos.models.Contact
import kotlinx.android.synthetic.main.activity_main.*
import com.example.miscontactos.models.ApplicationExt


class MainActivity : AppCompatActivity() {

    private val MY_PERMISSIONS_REQUEST_CALL_PHONE = 1

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        //SOLICITAMOS PERMISOS SI NO LOS TENEMOS
        if (ActivityCompat.shouldShowRequestPermissionRationale(
                this,
                Manifest.permission.CALL_PHONE
            )
        ) {

        } else {
            ActivityCompat.requestPermissions(
                this,
                arrayOf(Manifest.permission.CALL_PHONE),
                MY_PERMISSIONS_REQUEST_CALL_PHONE
            )

        }

        /*Agregamos los contactos a la base de datos*/
        val values = ContentValues()
        val Contact1 = Contact("Bill Gates","30355923","con18409uvg.edugt", R.drawable.gates)
        val Contact2 = Contact("Elvis Presley","52021073","scontrerasig@gmail.com", R.drawable.elvis)
        val Contact3 = Contact("Russo Brothers","45416153","ef.proyectomundial@gmail.com", R.drawable.russo)
        values.put(ContactProvider.NAME, "Bill Gates")
        values.put(ContactProvider.PHONE, "30355923")
        values.put(ContactProvider.EMAIL, "con18409uvg.edugt")
        values.put(ContactProvider.IMAGEN, R.drawable.gates)
        val uri = contentResolver.insert(ContactProvider.CONTENT_URI, values)



        ver.setOnClickListener{//redirigimos los botones
            val intento = Intent(this, Contactos::class.java)//Redirigimos a contactos
            startActivity(intento)
            onPause()
        }
    }

}
