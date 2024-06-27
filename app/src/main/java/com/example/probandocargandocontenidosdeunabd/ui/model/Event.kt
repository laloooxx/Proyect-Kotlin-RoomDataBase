package com.example.probandocargandocontenidosdeunabd.ui.model

//creamos una clase q va a tener todos los eventos de nuestra aplicacion.
//decimos con sealed q cualquier evento q creemos tenemos q agregarlo aca, no podemos hacerlo de otro lado, le decimos q cualquier evento q tengamos
//herede de aca si o si.
sealed class Event {
    //Aca decimos q seteamos el texto, actualiza el campo del texto
    data class SetText(val text: String): Event()
    //dice el valor del dialogo, si esta abierto
    object OpenDialog: Event()
    //aca si esta cerrado
    object CloseDialog: Event()
    //accion q nos permite guardar o actualizar el evento, captura
    object Save: Event()
    //accion de eliminar la nota
    data class Delete(val id: Int): Event()
    //accion de cargar la nota
    data class Load(val id: Int?): Event()
}