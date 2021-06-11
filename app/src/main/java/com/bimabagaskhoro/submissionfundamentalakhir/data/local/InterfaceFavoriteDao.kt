package com.bimabagaskhoro.submissionfundamentalakhir.data.local

import android.database.Cursor
import androidx.lifecycle.LiveData
import androidx.room.Dao
import androidx.room.Insert
import androidx.room.Query

@Dao
interface InterfaceFavoriteDao {
    @Insert
    suspend fun addToFavorite(entityFavoriteUser: EntityFavoriteUser)

    @Query("SELECT * FROM favorite")
    fun getFavoriteUser(): LiveData<List<EntityFavoriteUser>>

    @Query("SELECT count(*) FROM favorite WHERE favorite.id = :id")
    suspend fun checkUser(id: Int): Int

    @Query("DELETE FROM favorite WHERE favorite.id = :id")
    suspend fun removeUser(id: Int): Int

    @Query("SELECT * FROM favorite")
    fun findAll(): Cursor
}