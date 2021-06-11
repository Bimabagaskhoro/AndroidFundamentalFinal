package com.bimabagaskhoro.submissionfundamentalakhir.viewmodel

import android.app.Application
import android.util.Log
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import com.bimabagaskhoro.submissionfundamentalakhir.api.RetrofitClient
import com.bimabagaskhoro.submissionfundamentalakhir.data.model.EntityDetailUser
import com.bimabagaskhoro.submissionfundamentalakhir.data.local.AbstractDatabaseFavorite
import com.bimabagaskhoro.submissionfundamentalakhir.data.local.EntityFavoriteUser
import com.bimabagaskhoro.submissionfundamentalakhir.data.local.InterfaceFavoriteDao
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

class DetailViewModel(application: Application) : AndroidViewModel(application) {
    val listUser = MutableLiveData<EntityDetailUser>()

    private var interfaceFavoriteDao: InterfaceFavoriteDao?
    private var abstractDatabaseFavorite: AbstractDatabaseFavorite? =
        AbstractDatabaseFavorite.getDatabase(application)

    init {
        interfaceFavoriteDao = abstractDatabaseFavorite?.interfaceFavoriteDao()
    }

    fun setUserDetail(username: String) {
        RetrofitClient.apiInstance
            .getUserDetail(username)
            .enqueue(object : Callback<EntityDetailUser> {
                override fun onResponse(
                    call: Call<EntityDetailUser>,
                    response: Response<EntityDetailUser>
                ) {
                    if (response.isSuccessful) {
                        listUser.postValue(response.body())
                    }
                }

                override fun onFailure(call: Call<EntityDetailUser>, t: Throwable) {
                    t.message?.let { Log.d("Failure", it) }
                }
            })
    }

    fun getUserDetail(): LiveData<EntityDetailUser> = listUser
    fun addFavorite(username: String, id: Int, avatar_url: String) {
        CoroutineScope(Dispatchers.IO).launch {
            val user = EntityFavoriteUser(
                username,
                id,
                avatar_url
            )
            interfaceFavoriteDao?.addToFavorite(user)
            Log.d("Failure", user.toString())
        }
    }

    suspend fun checkUser(id: Int) = interfaceFavoriteDao?.checkUser(id)
    fun removeFavorite(id: Int) {
        CoroutineScope(Dispatchers.IO).launch {
            interfaceFavoriteDao?.removeUser(id)
            Log.d("Failure", id.toString())
        }
    }

}