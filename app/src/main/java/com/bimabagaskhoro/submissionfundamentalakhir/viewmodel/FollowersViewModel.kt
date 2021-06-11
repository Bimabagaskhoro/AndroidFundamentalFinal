package com.bimabagaskhoro.submissionfundamentalakhir.viewmodel

import android.util.Log
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.bimabagaskhoro.submissionfundamentalakhir.api.RetrofitClient
import com.bimabagaskhoro.submissionfundamentalakhir.data.model.EntityUser
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

class FollowersViewModel : ViewModel() {
    val listFollowers = MutableLiveData<ArrayList<EntityUser>>()

    fun setListFollowers(username: String) {
        RetrofitClient.apiInstance
            .getUserFollowers(username)
            .enqueue(object : Callback<ArrayList<EntityUser>> {
                override fun onResponse(
                    call: Call<ArrayList<EntityUser>>,
                    response: Response<ArrayList<EntityUser>>
                ) {
                    if (response.isSuccessful) {
                        listFollowers.postValue(response.body())
                    }
                }

                override fun onFailure(call: Call<ArrayList<EntityUser>>, t: Throwable) {
                    t.message?.let { Log.d("Failure", it) }
                }
            })

    }

    fun getListFollowers(): LiveData<ArrayList<EntityUser>> {
        return listFollowers
    }
}