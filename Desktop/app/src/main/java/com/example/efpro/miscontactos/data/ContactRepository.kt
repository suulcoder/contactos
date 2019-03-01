package com.example.efpro.miscontactos.data

import android.app.Application
import androidx.lifecycle.LiveData
import android.os.AsyncTask


class ContactRepository(application: Application){
    private var ContactDao: ContactDao
    private var allContacts: LiveData<List<Contact>>

    init{
        val database: ContactDatabase = ContactDatabase.getInstance(
            application.applicationContext
        )!!
        ContactDao = database.ContactDao()
        allContacts = ContactDao.getAllContacts()
    }

    fun insert(contact: Contact){
        val insertContactAsyncTask = InsertContactAsyncTask(ContactDao).execute(contact)
    }

    fun update(contact: Contact){
        val updateContactAsyncTask = UpdateContactAsyncTask(ContactDao).execute(contact)
    }

    fun delete(contact: Contact){
        val deleteContactAsyncTask = DeleteContactAsyncTask(ContactDao).execute(contact)
    }

    fun deleteAllContacts(){
        val deleteAllNotesAsyncTask = DeleteAllNotesAsyncTask(ContactDao).execute()
    }

    fun getAllContacts(): LiveData<List<Contact>> {
        return allContacts
    }

    companion object {
        private class InsertContactAsyncTask(val contactDao: ContactDao) : AsyncTask<Contact, Unit, Unit>(){


            override fun doInBackground(vararg p0: Contact?){
                contactDao.insert(p0[0]!!)
            }

        }

        private class UpdateContactAsyncTask(val contactDao: ContactDao) : AsyncTask<Contact, Unit, Unit>(){

            override fun doInBackground(vararg p0: Contact?){
                contactDao.update(p0[0]!!)
            }
        }

        private class DeleteContactAsyncTask(val contactDao: ContactDao): AsyncTask<Contact, Unit, Unit>() {

            override fun doInBackground(vararg p0: Contact?) {
                contactDao.delete(p0[0]!!)
            }
        }

        private class DeleteAllNotesAsyncTask(val contactDao: ContactDao): AsyncTask<Unit, Unit, Unit>(){


            override fun doInBackground(vararg p0: Unit?) {
                contactDao.deleteAllContacts()
            }

        }
    }
}