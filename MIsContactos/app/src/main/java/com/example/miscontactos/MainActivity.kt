package com.example.miscontactos

import android.Manifest
import android.content.ContentValues
import android.content.Intent
import android.content.pm.PackageManager
import android.net.Uri
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
        //val values = ContentValues()
        //values.put(ContactProvider.NAME, "Russo Brothers")
        //values.put(ContactProvider.PHONE, "45416153")
        //values.put(ContactProvider.EMAIL, "ef.proyectomundial@gmail.com")
        //values.put(ContactProvider.IMAGEN, R.drawable.russo)
        //val uri = contentResolver.insert(ContactProvider.CONTENT_URI, values)

        val URL = "content://com.example.miscontactos.Provider.ContactProvider"

        val contacts = Uri.parse(URL)
        val c = contentResolver.query(contacts, null, null, null, "name")


        if (c.moveToFirst()) {
            do {
                val Contact = Contact(c.getString(c.getColumnIndex(ContactProvider.NAME)),c.getString(c.getColumnIndex(ContactProvider.PHONE)),c.getString(c.getColumnIndex(ContactProvider.EMAIL)), c.getInt(c.getColumnIndex(ContactProvider.IMAGEN)))
                ApplicationExt.add(Contact)
            } while (c.moveToNext())
        }
        c.close();


        ver.setOnClickListener{//redirigimos los botones
            val intento = Intent(this, Contactos::class.java)//Redirigimos a contactos
            startActivity(intento)
            onPause()
        }
    }

}
