package com.example.probandocargandocontenidosdeunabd.ui.model

import android.app.Application
import androidx.compose.runtime.*
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.LiveData
import androidx.lifecycle.viewModelScope
import com.example.probandocargandocontenidosdeunabd.core.TextFieldState
import com.example.probandocargandocontenidosdeunabd.db.NoteDataBase
import com.example.probandocargandocontenidosdeunabd.db.model.Note
import com.example.probandocargandocontenidosdeunabd.repository.NotesRepository
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.MutableSharedFlow
import kotlinx.coroutines.flow.asSharedFlow
import kotlinx.coroutines.launch
import java.util.Date

    //le decimos q nuestra clase extienda de Androidviewmodel
class NoteViewModel(application: Application) : AndroidViewModel(application) {

    //aca lo q hacemos es definir el estado del texto q va a crearse una nota y modificar la nota.
    private var _text = mutableStateOf(TextFieldState())

    //definimos ele estado q va a estar a la esscucha de algun evento para poder cambiar
    var text: State<TextFieldState> = _text

    //va hacer referencia al id de la nota q vamos a usar, si la nota no existe devuelve null
    private var currentID: Int? = null

    //lo creamos para decirle q tiene un estado q pueda cambiar
    var openDialog by mutableStateOf(false)

    //es una variable q va a cambiar un estado y se va a lanzar cuando usemos el evento guardar
    private val _eventFlow = MutableSharedFlow<Event>()
    val eventFlow = _eventFlow.asSharedFlow()

    //creamos una variable repository para usar nuestro repositorio
    private val repository: NotesRepository

    //creamos una variable para almacenar todas las notas q hayan y le decimos q este escuchando los posibles cambios
    var allNotes: LiveData<List<Note>>

    //iniciamos la base de datos, el dao, el repositorio
    init {
        //la instancia de mi base de datos, es la referencia a mi bd, le estoy pasando el contexto.
        //aca preg si la instancia esta, si no esta la crea
        val db = NoteDataBase.getInstance(application)
        //obtengo a quien va a manipular mi bd o fuente de datos, aquel q modifica, elimina, crea datos
        //seria el controlador, el crud
        val dao = db.noteDao()
        //este lo q hace es comunicarse con el q maneja los datos y usarlo como se debe
        //es el q maneja los datos a quien va y a donde pedirlo. Cuando de la vista necesite datos, el q se los va a dar
        //es el repository.
        repository = NotesRepository(dao)
        allNotes = repository.obtenerNotas()
    }

    //una funcion q va a cargar cuando creemos una nota
    private fun load(id: Int?) {
        //como va a estar todo el tiempo viendo q se modifique, aca lo q hacemos es q cuando no estemos sobre esa nota
        //modificando o escribiendo destruya el ciclo de estar viendo si hay cambios,
        //con el .laucnh le decimos q cuando el foco este ahi este esperando el cambio
        viewModelScope.launch {
            //verificamos si el id existe, q lo busque cuando hacemos referencia a estar parado en una nota
            if (id != null) {
                //.also va a devolver algo(una nota)
                repository.obtenerUserById(id).also {note ->
                //le voy a indicar el id q va usar en este casso el id de la nota
                currentID = note.id
                //aca decimos q al valor del texto lo vaya guardando
                _text.value = text.value.copy(
                text =  note.text)
                }
            } else {
                currentID = null
                _text.value = text.value.copy(
                    text = ""
                )
            }
        }
    }


        //cuando lanzamos un evento, aca lo recibimos y capturamos
        fun onEvent(event: Event) {
            //aca digo si es "x" evento hago esto, en este caso cambio el valor del texto x el evento del texto, estoy parado en la nota
            when (event) {
                //le decimos q cuando vayamos a actualizar el campo de texto use los valores text
                is Event.SetText -> {
                    _text.value = _text.value.copy(
                        text = event.text
                    )
                }
                //aca le decimos q cuando le doy click al boton guardar haga esto
                is Event.Save -> {
                    viewModelScope.launch {
                        //aca decimos q si es distinto de null(q existe el id), q me actualice la nota
                        if (currentID != null) {
                            //aca actualizamos la nota, pasandole el id, el valor y la fecha
                            repository.actualizar(
                                Note(
                                    id = currentID,
                                    text = text.value.text,
                                    update = Date()
                                )
                            )
                        } else {
                            //aca decimos q si no existe, q lo cree
                            repository.crear(
                                Note(
                                    id = null,
                                    text = text.value.text,
                                    update = Date()
                                )
                            )
                        }
                        //las curritinas sirven para manejar los procesos asincronos. Trabajan con observables, para q cuando
                        //haya modificaciones me avise, ya sea actualizando, cambiando. Cuando guardamos la informacion
                        //le decimos q emita un evento, por ejemplo: "guardado con exito" pero le pedimos q esto lo haga
                        //cuando va a la base de datos y vuelve con los cambios,
                        openDialog = false
                        //le decimos q guarde el evento una vez q pase
                        _eventFlow.emit(Event.Save)
                    }
                }
                //aca declaramos cuando el evento sea abrir la nota
                is Event.OpenDialog -> {
                    openDialog = true
                }
                //aca declaramos cuando el evento sea cerrar la nota
                is Event.CloseDialog -> {
                    openDialog = false
                }
                //aca declaramos cuando el evento sea cargar la nota
                is Event.Load -> {
                    load(event.id)
                    openDialog = true
                }
                //aca declaramos cuando el evento sea eliminar la nota
                is Event.Delete -> {
                    viewModelScope.launch {
                        event.id?.let { repository.eliminar(it) }
                    }
                }
            }
        }
}
