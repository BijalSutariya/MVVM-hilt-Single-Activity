package com.fintech.bijalpractical.di

import com.fintech.bijalpractical.data.DataModel
import retrofit2.http.GET

interface ApiService {
    @GET("photos")
    suspend fun getData(): List<DataModel>
}