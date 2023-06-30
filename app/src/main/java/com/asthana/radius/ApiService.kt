package com.asthana.radius

import com.asthana.radius.Models.ApiModels
import io.reactivex.Single
import retrofit2.http.GET

interface ApiService {
    @GET("ad-assignment/db")
    fun getApiData(): Single<ApiModels.ApiResponse>
}
