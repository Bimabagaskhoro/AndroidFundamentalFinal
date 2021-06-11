package com.bimabagaskhoro.submissionfundamentalakhir.api

import com.bimabagaskhoro.submissionfundamentalakhir.data.model.EntityArrayUser
import com.bimabagaskhoro.submissionfundamentalakhir.data.model.EntityDetailUser
import com.bimabagaskhoro.submissionfundamentalakhir.data.model.EntityUser
import retrofit2.Call
import retrofit2.http.GET
import retrofit2.http.Headers
import retrofit2.http.Path
import retrofit2.http.Query

interface ApiClient {
    @GET("search/users")
    @Headers("Authorization: 11ac542bcaaaa0229265cad011c61e1b808c87a5")
    fun getSearchUsers(
        @Query("q") query: String
    ): Call<EntityArrayUser>


    @GET("users/{username}")
    @Headers("Authorization: 11ac542bcaaaa0229265cad011c61e1b808c87a5")
    fun getUserDetail(
        @Path("username") username: String
    ): Call<EntityDetailUser>


    @GET("users/{username}")
    @Headers("Authorization: 11ac542bcaaaa0229265cad011c61e1b808c87a5")
    fun getUserMain(
        @Path("username") username: String
    ): Call<EntityDetailUser>


    @GET("users/{username}/followers")
    @Headers("Authorization: 11ac542bcaaaa0229265cad011c61e1b808c87a5")
    fun getUserFollowers(
        @Path("username") username: String
    ): Call<ArrayList<EntityUser>>


    @GET("users/{username}/following")
    @Headers("Authorization: 11ac542bcaaaa0229265cad011c61e1b808c87a5")
    fun getUserFollowing(
        @Path("username") username: String
    ): Call<ArrayList<EntityUser>>
}