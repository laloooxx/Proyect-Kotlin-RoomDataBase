package com.example.probandocargandocontenidosdeunabd.db

import android.content.Context
import androidx.room.Database
import androidx.room.Room
import androidx.room.RoomDatabase
import androidx.room.TypeConverters
import com.example.probandocargandocontenidosdeunabd.db.model.Note
import com.example.probandocargandocontenidosdeunabd.db.Converter

//aca creamos la instanciaa de mi base de datos, vamos a manejar un unico hilo osea solamente nosotros vamos a manejar
// y conectarnos a nuestra bd xq la usamos localmente.

@Database(entities = [(Note:: class)],
                        version = 1,
                        exportSchema = false)
//nos sirve para convertir datos, transformrlos, convertirlos, de fecha a numero y numero a fecha es en este caso
@TypeConverters(Converter::class)
abstract class NoteDataBase: RoomDatabase(){
    //a una misma base de datos, no varia
    abstract fun noteDao(): NotesDao
    //abstract fun userDao(): UserDao

    //aseguramos q siempre sea la misma instancia
    companion object{
        //le decimos q cuando trabajemos con mi bd, y tengamos varios accesos, me define q voy a tener una variable
        // instancia para.
        //le decimos q sincronice la instancia, x mas q lleguen varias peticiones, hacemos q sea controlable, q entren
        // en orden
        @Volatile
        private var INSTANCE: NoteDataBase? = null

        //esta funcion nos devuelve la instancia de la bd, como vamos a poder manipularla? a traves del patron dao
        //el contexto hace referencia a todo lo q tenemos en la aplicacion, recursos, referencias etc
        fun getInstance(context: Context): NoteDataBase {
            //aca decimos q vamos a ingresar de una manera sincronizada a la bd q creamos
            synchronized(this) {
                var instance = INSTANCE

                //preguntamos si esta creada la bd
                if (instance == null) {
                    //en caso de q no este, la creamos con el comando Room.dbBuilder, le agregamos el contexto
                    instance = Room.databaseBuilder(
                        context.applicationContext,
                        NoteDataBase::class.java,
                        "note_database"
                        //destruye mi esquema de la bd, cuadno trabajamos en desarrollo le decimos q quiero borrar para poder crear una nueva de 0,
                        //le decimos q borre todo
                        ).fallbackToDestructiveMigration()
                        .build()

                    INSTANCE = instance
                }
                return instance
            }
        }
    }
}