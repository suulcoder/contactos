package com.example.efpro.miscontactos.data

import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.PrimaryKey
import java.sql.Blob
import java.util.*

@Entity(tableName = "contact_table")
data class Contact(var nombre:String, var tele:String, var email:String, var priority: Int, var image: ByteArray
) {

    //does it matter if these are private or not?
    @PrimaryKey(autoGenerate = true)
    var id: Int = 0


}
