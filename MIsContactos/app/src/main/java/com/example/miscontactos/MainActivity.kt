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

        } else {//mostramos mensajes
            ActivityCompat.requestPermissions(
                this,
                arrayOf(Manifest.permission.CALL_PHONE),
                MY_PERMISSIONS_REQUEST_CALL_PHONE
            )

        }


        ver.setOnClickListener{//redirigimos los botones
            val intento = Intent(this, Contactos::class.java)//Redirigimos a contactos
            startActivity(intento)
            onPause()
        }
    }

}
