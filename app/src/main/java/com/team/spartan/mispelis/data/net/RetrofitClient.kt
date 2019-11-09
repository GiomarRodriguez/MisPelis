package com.team.spartan.mispelis.data.net

import com.team.spartan.mispelis.data.model.PopularResponse
import okhttp3.OkHttpClient
import retrofit2.Retrofit
import retrofit2.adapter.rxjava2.RxJava2CallAdapterFactory
import retrofit2.converter.gson.GsonConverterFactory
import io.reactivex.Observable

class RetrofitClient {

     private val movieApi: MovieApi

     init {
         val builder = OkHttpClient.Builder()
         val okHttpClient = builder.build()
         val retrofit = Retrofit.Builder()
             .baseUrl("https://api.themoviedb.org/3/")
             .addConverterFactory(GsonConverterFactory.create())
             .addCallAdapterFactory(RxJava2CallAdapterFactory.create())
             .client(okHttpClient)
             .build()

         movieApi = retrofit.create(MovieApi::class.java)
     }

    fun fetchMovies(): Observable<PopularResponse> {
        return movieApi.fetchMovies("c94380b61dcb278a451897a485bf087a")
    }
 }