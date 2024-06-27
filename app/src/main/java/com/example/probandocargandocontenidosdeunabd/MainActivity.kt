package com.example.probandocargandocontenidosdeunabd

import android.annotation.SuppressLint
import android.app.Application
import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.viewModels
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Add
import androidx.compose.material.icons.rounded.Delete
import androidx.compose.material.icons.rounded.Edit
import androidx.compose.material3.Divider
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.FabPosition
import androidx.compose.material3.FloatingActionButton
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.ListItem
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Scaffold
import androidx.compose.material3.SnackbarHost
import androidx.compose.material3.SnackbarHostState
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.material3.TextButton
import androidx.compose.material3.TextField
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.livedata.observeAsState
import androidx.compose.runtime.remember
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.ui.Alignment
import androidx.compose.ui.ExperimentalComposeUiApi
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.window.Dialog
import androidx.compose.ui.window.DialogProperties
import androidx.lifecycle.viewmodel.compose.LocalViewModelStoreOwner
import androidx.lifecycle.viewmodel.compose.viewModel
import com.example.probandocargandocontenidosdeunabd.db.model.Note
import com.example.probandocargandocontenidosdeunabd.ui.model.Event
import com.example.probandocargandocontenidosdeunabd.ui.model.NoteViewModel
import com.example.probandocargandocontenidosdeunabd.ui.model.NoteViewModelFactory
import com.example.probandocargandocontenidosdeunabd.ui.theme.ProbandoCargandoContenidosDeUnaBdTheme
import kotlinx.coroutines.flow.collectLatest
import kotlinx.coroutines.launch

class MainActivity : ComponentActivity() {


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContent {
            ProbandoCargandoContenidosDeUnaBdTheme {
                // A surface container using the 'background' color from the theme
                Surface(
                    modifier = Modifier.fillMaxSize(),
                    color = MaterialTheme.colorScheme.background
                ) {
                    Crud()
                }
            }
        }
    }
}


@Composable
fun Crud() {
    val owner = LocalViewModelStoreOwner.current
    owner?.let {
        val viewModel: NoteViewModel = viewModel(
            it,
            "NoteViewModel",
            NoteViewModelFactory(
                LocalContext.current.applicationContext
                        as Application
            )
        )
        CrudScreenSetUp(viewModel)
    }
}



@SuppressLint("UnusedMaterial3ScaffoldPaddingParameter")
@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun CrudScreenSetUp(viewModel: NoteViewModel) {
    //observeAsState() es una extensión de Jetpack Compose que convierte un LiveData en un State
    //State es una clase en Compose que mantiene datos observables que pueden ser utilizados para recomponer una
    // UI cuando cambian. listOf() proporciona un valor inicial vacío (List<Note>) mientras que los datos
    // reales están siendo cargados
    val all by viewModel.allNotes.observeAsState(listOf() )

    val snackbarHostState = remember { SnackbarHostState() }
    val scope = rememberCoroutineScope()

    LaunchedEffect(snackbarHostState) {
        viewModel.eventFlow.collectLatest { event ->
            when (event) {
                is Event.Save -> {
                    scope.launch {
                        snackbarHostState.showSnackbar(
                            "Action done"
                        )
                    }
                }
                is Event.CloseDialog -> {
                    // Handle CloseDialog event
                }
                is Event.Delete -> {
                    // Handle Delete event
                }
                is Event.Load -> {
                    // Handle Load event
                }
                is Event.SetText -> {
                    // Handle setText event
                }
                is Event.OpenDialog -> {
                    // Handle OpenDialog event
                }
            }
        }
    }

    Scaffold(
        floatingActionButton = {
            FloatingActionButton(
                onClick = {
                    viewModel.onEvent(Event.Load(null))
                }) {
                Icon(
                    imageVector = Icons.Default.Add,
                    contentDescription = "New note"
                )
            }
        },
        floatingActionButtonPosition = FabPosition.End,
        snackbarHost = { SnackbarHost(snackbarHostState) }
    ) {
        CrudScreen (
            all = all,
            openDialog = viewModel.openDialog,
            text = viewModel.text.value.text,
            onEvent = { viewModel.onEvent(it) }
        )
    }
}


@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun CrudScreen(
    all: List<Note>,
    openDialog: Boolean,
    text: String,
    onEvent: (Event) -> Unit,
) {
    Box(modifier = Modifier.fillMaxSize()) {
        LazyColumn {
            items(all) { note ->
                ListItem(
                    headlineContent = { Text(note.text) },
                    supportingContent = { Text(note.update.toString()) },
                    modifier = Modifier.padding(start = 5.dp, end = 5.dp, top = 5.dp),
                    trailingContent = {
                        IconButton(onClick = {
                            note.id?.let {
                                    id -> onEvent(Event.Delete(id))
                            }
                        }) {
                            Icon(Icons.Rounded.Delete, contentDescription = null)
                        }
                    },
                    leadingContent = {
                        IconButton(onClick = {
                            onEvent(Event.Load(note.id))
                        }) {
                            Icon(Icons.Rounded.Edit, contentDescription = null)
                        }
                    }
                )
                Divider()
            }
        }
    }
    EditDialog(openDialog = openDialog, text = text, onEvent = onEvent)
}



@OptIn(ExperimentalComposeUiApi::class, ExperimentalMaterial3Api::class)
@Composable
fun EditDialog(openDialog: Boolean, text: String, onEvent: (Event) -> Unit) {

    if (openDialog) {
        Dialog(
            onDismissRequest = { onEvent(Event.CloseDialog) },
            properties = DialogProperties(usePlatformDefaultWidth = false)
        ) {
            Surface(modifier = Modifier.fillMaxSize()) {
                Column (modifier = Modifier.padding(16.dp),
                    horizontalAlignment = Alignment.CenterHorizontally) {
                    TextField(
                        value = text,
                        onValueChange = { onEvent (Event.SetText(it)) },
                        label = { Text("Text") }
                    )
                }
                Box(modifier = Modifier.padding(16.dp)) {
                    TextButton(
                        onClick = { onEvent(Event.CloseDialog) },
                        modifier = Modifier.align(Alignment.BottomCenter)
                    ) {
                        Text("Cancel")
                    }
                    TextButton(
                        onClick = { onEvent(Event.Save) },
                        modifier = Modifier.align(Alignment.BottomEnd)
                    ) {
                        Text("Confirm")
                    }
                }
            }
        }
    }
}


@Preview(showBackground = true, showSystemUi = true)
@Composable
fun DefaultPreview() {
    ProbandoCargandoContenidosDeUnaBdTheme {
        Text("probando")
    }
}