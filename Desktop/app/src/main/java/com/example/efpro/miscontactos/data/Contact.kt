package com.example.efpro.miscontactos.data

import androidx.room.Entity
import androidx.room.PrimaryKey
import java.sql.Blob
import java.util.*

@Entity(tableName="contact_table")
data class Contact(var nombre:String, var tele:String, var email:String, var image: ByteArray, var priority: Int){
    @PrimaryKey(autoGenerate = true)
    var id: Int = 0

    override fun equals(other: Any?): Boolean {
        if (this === other) return true
        if (javaClass != other?.javaClass) return false

        other as Contact

        if (nombre != other.nombre) return false
        if (tele != other.tele) return false
        if (email != other.email) return false
        if (!Arrays.equals(image, other.image)) return false
        if (priority != other.priority) return false
        if (id != other.id) return false

        return true
    }

    override fun hashCode(): Int {
        var result = nombre.hashCode()
        result = 31 * result + tele.hashCode()
        result = 31 * result + email.hashCode()
        result = 31 * result + Arrays.hashCode(image)
        result = 31 * result + priority
        result = 31 * result + id
        return result
    }
}
