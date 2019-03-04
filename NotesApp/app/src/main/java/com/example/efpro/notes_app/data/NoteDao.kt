package com.example.efpro.notes_app.data

import android.arch.lifecycle.LiveData
import androidx.lifecycle.LiveData
import androidx.room.*
import com.example.efpro.notes_app.data.Note

@Dao
interface NoteDao {

    @Insert
    fun insert(note: Note)

    @Update
    fun update(note: Note)

    @Delete
    fun delete(note: Note)

    @Query("DELETE FROM note_table")
    fun deleteAllNotes()

    @Query("SELECT * FROM note_table ORDER BY priority DESC")
    fun getAllNotes(): LiveData<List<Note>>

}