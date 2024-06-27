package com.example.probandocargandocontenidosdeunabd.db.model

import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.PrimaryKey
import java.util.Date

//le decimos q cree la table notes, utilizado room, le indicamos cual es la estructura q va a tener mi
//base de datos en mi proyecto
@Entity(tableName = "notes")
data class Note(
    //aca le indicamos la primary key
    @PrimaryKey(autoGenerate = true)
    //asi el nombre de la table
    @ColumnInfo(name = "id")
    //asi el tipo de dato
    var id: Int? = null,
    //con @NoNull le decimos q no puede ser nulo y q tiene q agregarlo si o si

    @ColumnInfo(name = "text")
    var text: String,

    //va a tener la fecha de cuando se crea la nota, cuando se modifica etc.
    @ColumnInfo(name = "update")
    var update: Date?
)