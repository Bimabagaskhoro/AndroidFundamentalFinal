package com.bimabagaskhoro.submissionfundamentalakhir.viewmodel

import android.util.Log
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.bimabagaskhoro.submissionfundamentalakhir.api.RetrofitClient
import com.bimabagaskhoro.submissionfundamentalakhir.data.model.EntityArrayUser
import com.bimabagaskhoro.submissionfundamentalakhir.data.model.EntityUser
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

class MainViewModel : ViewModel() {
    val listUser = MutableLiveData<ArrayList<EntityUser>>()

    fun setSearchUsers(query: String) {
        RetrofitClient.apiInstance
            .getSearchUsers(query)
            .enqueue(object : Callback<EntityArrayUser> {
                override fun onResponse(
                    call: Call<EntityArrayUser>,
                    arrayResponse: Response<EntityArrayUser>
                ) {
                    if (arrayResponse.isSuccessful) {
                        listUser.postValue(arrayResponse.body()?.items)
                    }
                }

                override fun onFailure(call: Call<EntityArrayUser>, t: Throwable) {
                    t.message?.let { Log.d("Failure", it) }
                }

            })
    }

    fun getSearchUsers(): LiveData<ArrayList<EntityUser>> {
        return listUser
    }
}