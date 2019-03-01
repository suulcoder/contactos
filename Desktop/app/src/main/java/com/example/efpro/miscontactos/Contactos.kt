package com.example.efpro.miscontactos

import android.app.Activity
import androidx.lifecycle.Observer
import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import androidx.recyclerview.widget.ItemTouchHelper
import android.widget.Toast
import androidx.lifecycle.ViewModelProviders
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.example.efpro.miscontactos.Adapters.ContactAdapter
import com.example.efpro.miscontactos.viewmodels.ContactViewModel
import com.example.efpro.miscontactos.data.Contact
import kotlinx.android.synthetic.main.activity_contactos.*



@Suppress("PLUGIN_WARNING")
class Contactos : AppCompatActivity() {

    companion object {
        const  val ADD_CONTACT_REQUEST = 1
        const  val EDIT_CONTACT_REQUEST = 2
    }

    private lateinit var ContactViewModel: ContactViewModel

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_contactos)

        buttonAddContact.setOnClickListener{
            startActivityForResult(
                Intent(this, Crear::class.java),
                ADD_CONTACT_REQUEST
            )
        }

        recycler_view.layoutManager = LinearLayoutManager(this)
        recycler_view.setHasFixedSize(true)

        val adapter = ContactAdapter()

        recycler_view.adapter = adapter
        ContactViewModel = ViewModelProviders.of(this).get(ContactViewModel::class.java)
        ContactViewModel.getAllContacts().observe(this, Observer<List<Contact>>{
            adapter.submitList(it)
        })

        ItemTouchHelper(object : ItemTouchHelper.SimpleCallback(0,ItemTouchHelper.LEFT.or(ItemTouchHelper.RIGHT)){
            override fun onMove(
                recyclerView: RecyclerView,
                viewHolder: RecyclerView.ViewHolder,
                target: RecyclerView.ViewHolder
            ):Boolean{
                return false
            }

            override fun onSwiped(p0: androidx.recyclerview.widget.RecyclerView.ViewHolder, p1: Int) {
                ContactViewModel.delete(adapter.getContactAt(p0.adapterPosition))
                Toast.makeText(baseContext,"Elemento Borrado",Toast.LENGTH_SHORT).show()
            }
        }).attachToRecyclerView(recycler_view)

        adapter.setOnItemClickListener(object: ContactAdapter.OnItemClickListener{
            override  fun onItemClick(contact: Contact){
                val intent = Intent(baseContext, Contacto::class.java)
                intent.putExtra(Crear.EXTRA_NOMBRE,contact.nombre)
                intent.putExtra(Crear.EXTRA_PHONE,contact.tele)
                intent.putExtra(Crear.EXTRA_MAIL,contact.email)
                intent.putExtra(Crear.EXTRA_ID,contact.id)
                intent.putExtra(Crear.EXTRA_IMAGE,contact.image)
                intent.putExtra(Crear.EXTRA_PRIORITY,contact.priority)
                startActivityForResult(intent, EDIT_CONTACT_REQUEST)
            }

        })
    }

    override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {
        super.onActivityResult(requestCode, resultCode, data)

        if (requestCode== ADD_CONTACT_REQUEST && resultCode == Activity.RESULT_OK){
            val newContact = Contact(
                data!!.getStringExtra(Crear.EXTRA_NOMBRE),
                data.getStringExtra(Crear.EXTRA_PHONE),
                data.getStringExtra(Crear.EXTRA_MAIL),
                data.getByteArrayExtra(Crear.EXTRA_IMAGE),
                data.getIntExtra(Crear.EXTRA_PRIORITY,1)
            )
            ContactViewModel.insert(newContact)
            Toast.makeText(this,"Contacto Guaradado", Toast.LENGTH_SHORT).show()
        }
        else if(requestCode== EDIT_CONTACT_REQUEST && resultCode == Activity.RESULT_OK){
            val id = data?.getIntExtra(Crear.EXTRA_ID,-1)
            if (id==-1){
                Toast.makeText(this,"No se pudo actualizar", Toast.LENGTH_LONG).show()
            }
            val updateContact = Contact(
                data!!.getStringExtra(Crear.EXTRA_NOMBRE),
                data.getStringExtra(Crear.EXTRA_PHONE),
                data.getStringExtra(Crear.EXTRA_MAIL),
                data.getByteArrayExtra(Crear.EXTRA_IMAGE),
                data.getIntExtra(Crear.EXTRA_PRIORITY,1)
            )
            updateContact.id=data.getIntExtra(Crear.EXTRA_ID,-1)
            ContactViewModel.update(updateContact)
        }
        else{
            Toast.makeText(this,"No se ha podido Guardar",Toast.LENGTH_LONG).show()
        }
    }
}
