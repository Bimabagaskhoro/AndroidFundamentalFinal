package com.bimabagaskhoro.consumerapp.viewmodel

import android.app.Application
import android.content.Context
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import com.bimabagaskhoro.consumerapp.data.DatabaseContract
import com.bimabagaskhoro.consumerapp.data.EntityUser
import com.bimabagaskhoro.consumerapp.data.MappingHelper

class FavoriteViewModel(application: Application) : AndroidViewModel(application) {

    private val list = MutableLiveData<ArrayList<EntityUser>>()

    fun setFavoriteUser(context: Context) {
        val cursor = context.contentResolver.query(
            DatabaseContract.FavoriteUserColumn.CONTENT_URI,
            null,
            null,
            null,
            null
        )
        val listConverted = MappingHelper.mapCursorToArrayList(cursor)
        list.postValue(listConverted)
    }

    fun getFavoriteUser(): LiveData<ArrayList<EntityUser>> {
        return list
    }
}