package com.yudit.submissiongithubuser.data.retrofit

import com.yudit.submissiongithubuser.data.response.DetailUserResponse
import retrofit2.http.GET
import retrofit2.http.Headers
import retrofit2.http.Query
import retrofit2.Call
import retrofit2.http.*
import com.yudit.submissiongithubuser.data.response.GithubResponse
import com.yudit.submissiongithubuser.data.response.ItemsItem

interface ApiService {
    @GET("search/users")
    fun getUserProfile(
        @Query("q") q: String
    ): Call<GithubResponse>

    @GET("users/{username}")
    fun getDetailUser(@Path("username") username: String): Call<DetailUserResponse>

    @GET("users/{username}/followers")
    fun getFollowers(@Path("username") username: String): Call<List<ItemsItem>>
    @GET("users/{username}/following")
    fun getFollowing(@Path("username") username: String): Call<List<ItemsItem>>
}