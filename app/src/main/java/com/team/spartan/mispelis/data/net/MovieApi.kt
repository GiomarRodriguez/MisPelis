package com.team.spartan.mispelis.data.net

import com.team.spartan.mispelis.data.model.PopularResponse
import retrofit2.Call
import retrofit2.http.GET
import retrofit2.http.Query
import io.reactivex.Observable

interface MovieApi {

    @GET("movie/popular")
    fun fetchMovies(@Query("api_key") api_key: String): Observable<PopularResponse>

}