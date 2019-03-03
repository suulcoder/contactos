package com.example.efpro.miscontactos

import android.app.Activity
import android.content.Context
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



   private lateinit var contactViewModel: ContactViewModel

    companion object {
        lateinit var adapter: ContactAdapter
        //Constants for the intent keys
        const val ADD_CONTACT_REQUEST = 1
        const val EDIT_CONTACT_REQUEST = 1
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_contactos)

        buttonAddContact.setOnClickListener{
            startActivityForResult(
                Intent(this, Crear::class.java),
                ADD_CONTACT_REQUEST
            )
        }

        // Creates a vertical Layout Manager
        recycler_view.layoutManager = androidx.recyclerview.widget.LinearLayoutManager(this)
        // Access the RecyclerView Adapter and load the data into it
        recycler_view.setHasFixedSize(true)

        contactViewModel = ViewModelProviders.of(this).get(ContactViewModel::class.java)



        contactViewModel.getAllContacts().observe(this, Observer<List<Contact>> {
            adapter.submitList(it)
        })


        //With Observers...
        ItemTouchHelper(object : ItemTouchHelper.SimpleCallback(0, ItemTouchHelper.LEFT.or(ItemTouchHelper.RIGHT)) {
            override fun onMove(
                recyclerView: RecyclerView,
                viewHolder: RecyclerView.ViewHolder,
                target: RecyclerView.ViewHolder
            ): Boolean {
                return false
            }

            override fun onSwiped(viewHolder: RecyclerView.ViewHolder, direction: Int) {
                removeContact(viewHolder.adapterPosition)
            }
        }
        ).attachToRecyclerView(recycler_view)

        adapter = ContactAdapter()

        adapter.setOnItemClickListener(object: ContactAdapter.OnItemClickListener{
            override  fun onItemClick(contact: Contact){
                val intent = Intent(baseContext, Contacto::class.java)
                intent.putExtra(Crear.EXTRA_NOMBRE,contact.nombre)
                intent.putExtra(Crear.EXTRA_PHONE,contact.tele)
                intent.putExtra(Crear.EXTRA_MAIL,contact.email)
                intent.putExtra(Crear.EXTRA_ID,contact.id)
                intent.putExtra(Crear.EXTRA_IMAGE,contact.image)
                intent.putExtra(Crear.EXTRA_PRIORITY,contact.priority)
                intent.putExtra(Crear.EXTRA_IMAGE, contact.image)
                startActivityForResult(intent, EDIT_CONTACT_REQUEST)
            }

        })

        //Setting the recycler adapter
        recycler_view.adapter = adapter

        recycler_view.setOnClickListener {
            //startActivity(Intent(this, SaveContactActivity::class.java))
            //this.finish()
            startActivityForResult(
                Intent(this, Contacto::class.java),ADD_CONTACT_REQUEST)
        }
    }



    private fun removeContact(position: Int) {
        // Delete contact records
        val contactRemoved = adapter.getContactAt(position)
        Toast.makeText(this, "Contacto '${contactRemoved.nombre}' eliminado", Toast.LENGTH_LONG).show()
        contactViewModel.delete(contactRemoved)

    }

    override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {
        super.onActivityResult(requestCode, resultCode, data)

        if (requestCode == ADD_CONTACT_REQUEST && resultCode == Activity.RESULT_OK) {
            val newContact = Contact(
                data!!.getStringExtra(Crear.EXTRA_NOMBRE),
                data!!.getStringExtra(Crear.EXTRA_PHONE),
                data!!.getStringExtra(Crear.EXTRA_MAIL),
                data.getIntExtra(Crear.EXTRA_PRIORITY, 1),data.getByteArrayExtra(Crear.EXTRA_IMAGE)

            )

            contactViewModel.insert(newContact)

            Toast.makeText(this,"Exito", Toast.LENGTH_LONG).show()

        } else if (requestCode == EDIT_CONTACT_REQUEST && resultCode == Activity.RESULT_OK) {
            Toast.makeText(this,"Exito", Toast.LENGTH_LONG).show()

        } else {
            Toast.makeText(this,"No se ha podido Guardar",Toast.LENGTH_LONG).show()

        }


    }
}
