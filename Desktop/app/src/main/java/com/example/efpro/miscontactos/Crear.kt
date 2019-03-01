package com.example.efpro.miscontactos

import android.app.Activity
import androidx.appcompat.app.AppCompatActivity
import android.content.ContentValues
import android.content.Intent
import android.graphics.BitmapFactory
import android.os.Bundle
import android.provider.MediaStore
import android.view.Menu
import android.widget.Toast
import com.example.efpro.miscontactos.data.Contact
import kotlinx.android.synthetic.main.activity_crear.*
import kotlinx.android.synthetic.main.list_item.*
import java.io.FileNotFoundException

class Crear : AppCompatActivity() {

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

        supportActionBar?.setHomeAsUpIndicator(R.drawable.ic_mtrl_chip_close_circle)

        //si la opcion es editar
        if(intent.hasExtra(EXTRA_ID)){
            nombre.setText(intent.getStringExtra(EXTRA_NOMBRE))
            telefono.setText(intent.getStringExtra(EXTRA_PHONE))
            correo.setText(intent.getStringExtra(EXTRA_MAIL))
            number_picker_priority.value = intent.getIntExtra(EXTRA_PRIORITY,1)
            //imageView.setImageState()
        }else{//o agregar uno nuevo
            //tittle = "AGREGAR"
        }

        save.setOnClickListener{
            saveContact()
        }
    }




    private fun saveContact(){
        if(nombre.text.toString().trim().isBlank() || telefono.text.toString().trim().isBlank() || correo.text.toString().trim().isBlank()){
            Toast.makeText(this,"POR FAVOR LLENE TODOS LOS ESPACIOS", Toast.LENGTH_SHORT).show()
            return
        }

        val data = Intent().apply {
            putExtra(EXTRA_NOMBRE,nombre.text.toString())
            putExtra(EXTRA_PHONE,telefono.text.toString())
            putExtra(EXTRA_MAIL,correo.text.toString())
            putExtra(EXTRA_PRIORITY,number_picker_priority.value)
            //putExtra(EXTRA_IMAGE,imageView.get)
            if (intent.getIntExtra(EXTRA_ID,-1) != -1){
                putExtra(EXTRA_ID,intent.getIntExtra(EXTRA_ID,-1))
            }
        }

        setResult(Activity.RESULT_OK,data)
        finish()

    }
}
