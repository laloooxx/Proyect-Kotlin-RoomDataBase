/*
* package com.example.probandocargandocontenidosdeunabd.repository

import androidx.lifecycle.LiveData
import com.example.probandocargandocontenidosdeunabd.db.NotesDao
import com.example.probandocargandocontenidosdeunabd.db.model.Note
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch

class NotesRepository(private val notesDao: NotesDao) {

    //la curritina hace q las peticiones entren de manera ordenada, para q espere a q vaya a buscar a la bd y
    //traiga las rta a las peticiones. Se usan cuando se trabaja de manera asincrona, osea tengo q ir a buscar a la bd
    //datos, para q no se pause la aplicacion decimos q continue ejecutando todo y cuando termine de traer los datos
    //continuar
    private val coroutine = CoroutineScope(Dispatchers.Main)
     fun crear(note: Note) {
        //con coroutine creaamos un hilo y le decimos q vaya a insertar esto
        coroutine.launch(Dispatchers.IO) {
            notesDao.insert(note)
        }
    }


     fun actualizar(note: Note) {
        coroutine.launch(Dispatchers.IO) {
            notesDao.update(note)
        }
    }


    //al ser un livedata obtengo una vez y los cambios voy a verlos de una x usar el livedata
    fun obtenerNotas(): LiveData<List<Note>> {
        return notesDao.getNotes()
    }


      fun eliminar(id:Int) {
        coroutine.launch(Dispatchers.IO) {
            notesDao.delete(id)
        }
    }


    suspend fun obtenerUserById(id: Int): Note {
        return notesDao.findById(id)
    }
}
*/


package com.example.probandocargandocontenidosdeunabd.repository

import androidx.lifecycle.LiveData
import com.example.probandocargandocontenidosdeunabd.db.NotesDao
import com.example.probandocargandocontenidosdeunabd.db.model.Note
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.withContext

class NotesRepository(private val notesDao: NotesDao) {
    suspend fun crear(note: Note) {
        withContext(Dispatchers.IO) {
            notesDao.insert(note)
        }
    }

    suspend fun actualizar(note: Note) {
        withContext(Dispatchers.IO) {
            notesDao.update(note)
        }
    }

    fun obtenerNotas(): LiveData<List<Note>> {
        return notesDao.getNotes()
    }

    suspend fun eliminar(id: Int) {
        withContext(Dispatchers.IO) {
            notesDao.delete(id)
        }
    }

    suspend fun obtenerUserById(id: Int): Note {
        return withContext(Dispatchers.IO) {
            notesDao.findById(id)
        }
    }
}
