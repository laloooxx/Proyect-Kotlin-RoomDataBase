/*
* package com.example.probandocargandocontenidosdeunabd.repository

import androidx.lifecycle.LiveData
import com.example.probandocargandocontenidosdeunabd.db.UserDao
import com.example.probandocargandocontenidosdeunabd.db.model.User
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch


//El repositorio actuará como una capa de abstracción entre la fuente de datos (en este caso, la base de datos Room)
// y la interfaz de usuario
class UserRepository(private val userDao: UserDao) {

    private val coroutine = CoroutineScope(Dispatchers.Main)

    fun crear(user: User){
        coroutine.launch(Dispatchers.IO) {
            userDao.insert(user)
        }
    }

    fun actualizar(user: User) {
        coroutine.launch(Dispatchers.IO) {
            userDao.updateUser(user)
        }
    }

    fun obtenerUsers(): LiveData<List<User>> {
        return userDao.getUsers()
    }

    fun eliminar(id: Int) {
        coroutine.launch(Dispatchers.IO) {
            userDao.deleteUser(id)
        }
    }

    suspend fun buscarPorId(id: Int): User {
        return userDao.getUserById(id)
    }


    fun filtrarPorUsername(username: String) {
        coroutine.launch(Dispatchers.IO) {
            userDao.findByUsername(username)
        }
    }
}*/