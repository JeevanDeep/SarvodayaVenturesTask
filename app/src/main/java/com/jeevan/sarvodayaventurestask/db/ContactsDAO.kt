package com.jeevan.sarvodayaventurestask.db

import android.arch.persistence.room.Dao
import android.arch.persistence.room.Insert
import android.arch.persistence.room.Query
import android.arch.persistence.room.Update
import com.jeevan.sarvodayaventurestask.model.SignUpModel

@Dao
interface ContactsDAO {
    @Query("Select * From SignUpModel Where phoneNumber like (:number) And password like (:password)")
    fun login(number: String, password: String): SignUpModel?

    @Insert
    fun saveUser(user: SignUpModel)

    @Update
    fun updateUser(user: SignUpModel)

    @Query("Select * from SignUpModel Where phoneNumber like (:number)")
    fun getUserProfile(number: String): SignUpModel

}
