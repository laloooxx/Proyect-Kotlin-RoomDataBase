package com.example.probandocargandocontenidosdeunabd.db

import androidx.room.TypeConverter
import java.util.Date

class Converter {

    @TypeConverter
    //a este valor q esta entrando, recuperando en el tipo Long, lo transformamos a una fecha comun
    //el ":" antes de la llave te dice q puede volver, en este caso vuelve una fecha
    fun FromTimeStamp(value: Long?): Date? {
        return value?.let {Date(it)}
    }

    @TypeConverter
    //va a traer una fecha de tipo date, y almacenarlo en Long, cuando quiera recuperar el timstamp es el paso anterior
    //aca puede volver un timstamp como hicimos arriba
    fun dataToTimeStamp(date: Date?): Long? {
        //obtengo date,
        return date?.time?.toLong()
    }
}