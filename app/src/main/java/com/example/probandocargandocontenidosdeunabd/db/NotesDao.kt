package com.example.probandocargandocontenidosdeunabd.db

import androidx.lifecycle.LiveData
import androidx.room.Dao
import androidx.room.Insert
import androidx.room.Query
import androidx.room.Update
import com.example.probandocargandocontenidosdeunabd.db.model.Note

//creamos la logica de la bd para consultas, definimos las operaciones a la bd
@Dao
interface NotesDao {

    @Insert
    fun insert(note: Note)

    @Update
    fun update(note: Note)

    @Query("DELETE FROM notes WHERE id = :id")
    fun delete(id: Int)

    @Query("SELECT * from notes")
    //con liveData le decimos q va a devolver una data y dentro le definimos q va a ser una lista
    //livedata es un observable va a estar escuchando la lista de datos, para cuando haya cambios se actualiza de forma automaticamente
    //lo q hace es cuanndo alguien este subscrito a esta lista y haya camvios para poder actualizarse
    fun getNotes(): LiveData<List<Note>>

    @Query("SELECT * from notes WHERE id = :id")
    fun findById(id: Int): Note
}