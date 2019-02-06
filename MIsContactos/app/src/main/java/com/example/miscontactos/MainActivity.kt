package com.example.miscontactos

import android.content.Intent
import android.support.v7.app.AppCompatActivity
import android.os.Bundle
import android.widget.ArrayAdapter
import android.widget.ListView
import com.example.miscontactos.models.Contact
import kotlinx.android.synthetic.main.activity_main.*
import com.example.miscontactos.models.ApplicationExt


class MainActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)


        val Contact1 = Contact("Bill Gates","30355923","con18409uvg.edugt", R.drawable.gates)
        val Contact2 = Contact("Elvis Presley","52021073","scontrerasig@gmail.com", R.drawable.elvis)
        val Contact3 = Contact("Russo Brothers","45416153","ef.proyectomundial@gmail.com", R.drawable.russo)
        ApplicationExt.add(Contact1)
        ApplicationExt.add(Contact2)
        ApplicationExt.add(Contact3)



        ver.setOnClickListener{//redirigimos los botones
            val intento = Intent(this, Contactos::class.java)//Redirigimos a contactos
            startActivity(intento)
            onPause()
        }
    }

}
