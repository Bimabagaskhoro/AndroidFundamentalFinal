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

class FollowingViewModel : ViewModel(){
    val listFollowing = MutableLiveData<ArrayList<EntityUser>>()

    fun setListFollowing(username: String) {
        RetrofitClient.apiInstance
            .getUserFollowing(username)
            .enqueue(object : Callback<ArrayList<EntityUser>> {
                override fun onResponse(
                    call: Call<ArrayList<EntityUser>>,
                    response: Response<ArrayList<EntityUser>>
                ) {
                    if (response.isSuccessful) {
                        listFollowing.postValue(response.body())
                    }
                }

                override fun onFailure(call: Call<ArrayList<EntityUser>>, t: Throwable) {
                    t.message?.let { Log.d("Failure", it) }
                }

            })
    }

    fun getListFollowing(): LiveData<ArrayList<EntityUser>> {
        return listFollowing
    }
}