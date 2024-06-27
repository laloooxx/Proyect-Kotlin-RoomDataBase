/*package com.example.probandocargandocontenidosdeunabd.db.model

import android.provider.ContactsContract.CommonDataKinds.Email
import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.PrimaryKey
import java.net.PasswordAuthentication

//nombre de usuario, mail contraseña
@Entity(tableName = "user")
data class User (
    @PrimaryKey(autoGenerate = true)
    @ColumnInfo(name = "id")
    var id: Int? = null,

    @ColumnInfo(name = "userName")
    var userName: String,

    @ColumnInfo(name = "email")
    var email: Email,

    @ColumnInfo(name = "password")
    var password: String
)*/

/*package com.example.probandocargandocontenidosdeunabd.ui.model

import androidx.lifecycle.LiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.probandocargandocontenidosdeunabd.db.NoteDataBase
import com.example.probandocargandocontenidosdeunabd.db.model.Note
import com.example.probandocargandocontenidosdeunabd.repository.NotesRepository
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import android.app.Application
import androidx.compose.runtime.State
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.setValue
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.MutableLiveData
import com.example.probandocargandocontenidosdeunabd.core.TextFieldState
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.flow.MutableSharedFlow
import kotlinx.coroutines.flow.asSharedFlow
import java.util.Date
open class NoteViewModel(application: Application): AndroidViewModel(application) {
    private var _text = mutableStateOf(TextFieldState())
    var text: State<TextFieldState> = _text
    private var currentID: Int? = null
    private val coroutineScope = CoroutineScope(Dispatchers.Main)
    var openDialog by mutableStateOf(false)
    private val _eventFlow = MutableSharedFlow<Event>()
    val eventFlow = _eventFlow.asSharedFlow()
    private var repository: NotesRepository
        get() { TODO()}
    var allNotes: LiveData<List<Note>>
        get() {TODO()}
    init {
        val db = NoteDataBase.getInstance(application)
        val dao = db.noteDao()
        repository = NotesRepository(dao)
        allNotes = repository.obtenerNotas()
    }
    private fun load(id: Int?) {
        viewModelScope.launch {
            if (id != null) {
                repository.obtenerUserById(id).also {note ->
                    currentID = note.id
                    _text.value = text.value.copy(
                        text =  note.text
                    )
                }
            } else {
                currentID = null
                _text.value = text.value.copy(
                    text = "text"
                )}}}
    open fun onEvent(event: Event) {
        when(event) {
            is Event.setText ->{
                _text.value = text.value.copy(
                    text = event.text
                )
            }
            is Event.Save -> {
                if (currentID != null) {
                    repository.actualizar(Note(currentID, text.value.text, Date()))
                }else {
                    repository.crear(Note(null, text.value.text, Date()))
                }
                openDialog = false
                coroutineScope.launch (Dispatchers.IO) {
                    _eventFlow.emit(Event.Save)
                }
            }
            is Event.OpenDialog -> {
                openDialog = true
            }
            is Event.CloseDialog -> {
                openDialog = false
            }
            is Event.Load -> {
                load(event.id)
                openDialog = true
            }
            is Event.Delete -> {
                event.id?.let { repository.eliminar(it) }}}}}*/


/*package com.example.probandocargandocontenidosdeunabd.ui.model

import androidx.lifecycle.LiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.probandocargandocontenidosdeunabd.db.NoteDataBase
import com.example.probandocargandocontenidosdeunabd.db.model.Note
import com.example.probandocargandocontenidosdeunabd.repository.NotesRepository
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import android.app.Application
import androidx.compose.runtime.State
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.setValue
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.MutableLiveData
import com.example.probandocargandocontenidosdeunabd.core.TextFieldState
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.flow.MutableSharedFlow
import kotlinx.coroutines.flow.asSharedFlow
import java.util.Date


//le decimos q nuestra clase extienda de viexModel
open class NoteViewModel(application: Application): AndroidViewModel(application) {


    //aca lo q hacemos es definir el estado del texto q va a crearse una nota y modificar la nota.
    private var _text = mutableStateOf(TextFieldState())
    //definimos ele estado q va a estar a la esscucha de algun evento para poder cambiar
    var text: State<TextFieldState> = _text

    //va hacer referencia al id de la nota q vamos a usar, si la nota no existe devuelve null
    private var currentID: Int? = null

    private val coroutineScope = CoroutineScope(Dispatchers.Main)
    //lo creamos para decirle q tiene un estado q pueda cambiar
    var openDialog by mutableStateOf(false)

    //es una variable q va a cambiar un estado y se va a lanzar cuando usemos el evento guardar
    private val _eventFlow = MutableSharedFlow<Event>()
    val eventFlow = _eventFlow.asSharedFlow()

    private var repository: NotesRepository
        get() {
            TODO()
        }

    var allNotes: LiveData<List<Note>>
        get() {
            TODO()
        }

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

    //una funcion q va a cargar cuando creemos una nota, algo de la bd va a recibir un id
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
                        text =  note.text
                    )
                }
            } else {
                currentID = null
                _text.value = text.value.copy(
                    text = "text"
                )
            }
        }
    }

    //cuando lanzamos un evento, aca lo recibimos y capturamos
    open fun onEvent(event: Event) {
        //aca preguntamos cuando viene un evento q hago
        when(event) {
            //aca digo si es "x" evento hago esto, en este caso cambio el valor del texto x el evento del texto, estoy parado en la nota
            is Event.setText ->{
                _text.value = text.value.copy(
                    text = event.text
                )
            }

            //aca le decimos q cuando le doy click al boton guardar
            is Event.Save -> {
                //aca decimos q si es distinto de null(q existe el id), q me actualice la nota
                if (currentID != null) {
                    //aca actualizamos la nota, pasandole el id, el valor y la fecha
                    repository.actualizar(Note(currentID, text.value.text, Date()))
                //aca decimos q si no existe, q lo cree
                }else {
                    repository.crear(Note(
                        null,
                        text.value.text,
                        Date()))
                }

                //las curritinas sirven para manejar los procesos asincronos. Trabajan con observables, para q cuando haya modificaciones
                //me avise, ya sea actualizando, cambiando. Cuando guardamos la informacion le decimos q emita un evento, por ejemplo: "guardado con exito"
                //pero le pedimos q esto lo haga cuando va a la base de datos y vuelve con los cambios,
                openDialog = false
                coroutineScope.launch (Dispatchers.IO) {
                    //si tira error tenemos q declarar el evento q usamos aca
                    _eventFlow.emit(Event.Save)
                }
            }

            //aca decimos q abramos el
            is Event.OpenDialog -> {
                openDialog = true
            }
            is Event.CloseDialog -> {
                openDialog = false
            }
            //aca cargo la nota
            is Event.Load -> {
                load(event.id)
                openDialog = true
            }
            //aca elimino la nota
            is Event.Delete -> {
                event.id?.let { repository.eliminar(it) }
            }
        }
    }
}*/

//ARHCIVO REPOSITORY

/*package com.example.probandocargandocontenidosdeunabd.repository
import androidx.lifecycle.LiveData
import com.example.probandocargandocontenidosdeunabd.db.NotesDao
import com.example.probandocargandocontenidosdeunabd.db.model.Note
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
class NotesRepository(private val notesDao: NotesDao) {
    private val coroutine = CoroutineScope(Dispatchers.Main)
    fun crear(note: Note) {
        coroutine.launch(Dispatchers.IO) {
            notesDao.insert(note)
        }
    }
    fun actualizar(note: Note) {
        coroutine.launch(Dispatchers.IO) {
            notesDao.update(note) } }
    fun obtenerNotas(): LiveData<List<Note>> {
        return notesDao.getNotes() }
    fun eliminar(id:Int) {
        coroutine.launch(Dispatchers.IO) {
            notesDao.delete(id) } }
    suspend fun obtenerUserById(id: Int): Note {
        return notesDao.findById(id)}}*/
