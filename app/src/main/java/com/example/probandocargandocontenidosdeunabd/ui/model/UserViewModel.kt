/*
* package com.example.probandocargandocontenidosdeunabd.ui.model

import android.app.Application
import androidx.lifecycle.LiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.probandocargandocontenidosdeunabd.db.NoteDataBase
import com.example.probandocargandocontenidosdeunabd.db.UserDao
import com.example.probandocargandocontenidosdeunabd.db.model.User
import com.example.probandocargandocontenidosdeunabd.repository.UserRepository
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch

class UserViewModel(application: Application) {

    private var repository: UserRepository
        get() {
            TODO()
        }

    var allUsers: LiveData<List<User>>
        get() {
            TODO()
        }


    init {
        val db = NoteDataBase.getInstance(application)

        val dao = db.userDao()

        repository = UserRepository(dao)

        allUsers = repository.obtenerUsers()
    }


}*/