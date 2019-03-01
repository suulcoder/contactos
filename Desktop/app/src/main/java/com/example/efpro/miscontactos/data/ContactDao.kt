package com.example.efpro.miscontactos.data

import androidx.lifecycle.LiveData
import androidx.room.*
import com.example.efpro.miscontactos.data.Contact

@Dao
interface ContactDao{
    @Insert
    fun insert(contact: Contact)

    @Update
    fun update(contact: Contact)

    @Delete
    fun delete(contact: Contact)

    @Query("DELETE FROM contact_table")
    fun deleteAllContacts()

    @Query("SELECT * FROM contact_table ORDER BY priority DESC")
    fun getAllContacts(): LiveData<List<Contact>>

}