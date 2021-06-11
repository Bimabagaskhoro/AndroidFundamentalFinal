package com.bimabagaskhoro.submissionfundamentalakhir.viewmodel

import android.app.Application
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.LiveData
import com.bimabagaskhoro.submissionfundamentalakhir.data.local.AbstractDatabaseFavorite
import com.bimabagaskhoro.submissionfundamentalakhir.data.local.EntityFavoriteUser
import com.bimabagaskhoro.submissionfundamentalakhir.data.local.InterfaceFavoriteDao

class FavoriteViewModel(application: Application) : AndroidViewModel(application) {
    private var interfaceFavoriteDao: InterfaceFavoriteDao?
    private var abstractDatabaseFavorite: AbstractDatabaseFavorite? =
        AbstractDatabaseFavorite.getDatabase(application)

    init {
        interfaceFavoriteDao = abstractDatabaseFavorite?.interfaceFavoriteDao()
    }

    fun getFavoriteUser(): LiveData<List<EntityFavoriteUser>>? {
        return interfaceFavoriteDao?.getFavoriteUser()
    }
}