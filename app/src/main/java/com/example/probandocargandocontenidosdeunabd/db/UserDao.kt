/*
* package com.example.probandocargandocontenidosdeunabd.db

import androidx.lifecycle.LiveData
import androidx.room.Dao
import androidx.room.Insert
import androidx.room.Query
import androidx.room.Update
import com.example.probandocargandocontenidosdeunabd.db.model.Note
import com.example.probandocargandocontenidosdeunabd.db.model.User

@Dao
interface UserDao {

    @Insert
    fun insert(user: User)

    @Update
    fun updateUser(user: User)

    @Query("DELETE FROM user WHERE id = :id")
    fun deleteUser(id: Int)

    @Query("SELECT * FROM USER")
    fun getUsers(): LiveData<List<User>>

    @Query("SELECT * FROM USER WHERE id = :id")
    fun getUserById(id: Int): User

    @Query("SELECT * FROM USER WHERE userName = :username")
    fun findByUsername(username: String)
}*/