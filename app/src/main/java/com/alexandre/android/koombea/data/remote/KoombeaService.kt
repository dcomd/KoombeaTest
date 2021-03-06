package com.alexandre.android.koombea.data.remote

import com.alexandre.android.koombea.data.models.KoombeaPostResponse
import com.alexandre.android.koombea.utils.Constants.URL
import okhttp3.OkHttpClient
import okhttp3.logging.HttpLoggingInterceptor
import retrofit2.Response
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import retrofit2.http.GET

object ApiClient {

    private var servicesApiInterface: PostService? = null

    fun build(): PostService {
        val builder: Retrofit.Builder = Retrofit.Builder()
            .baseUrl(URL)
            .addConverterFactory(GsonConverterFactory.create())

        val httpClient: OkHttpClient.Builder = OkHttpClient.Builder()
        httpClient.addInterceptor(interceptor())

        val retrofit: Retrofit = builder.client(httpClient.build()).build()
        servicesApiInterface = retrofit.create(
            PostService::class.java)

        return servicesApiInterface as PostService
    }

    private fun interceptor(): HttpLoggingInterceptor {
        val httpLoggingInterceptor = HttpLoggingInterceptor()
        httpLoggingInterceptor.level = HttpLoggingInterceptor.Level.BODY
        return httpLoggingInterceptor
    }

    interface PostService {
        @GET("posts")
        suspend fun getPosts(): Response<KoombeaPostResponse>
    }
}
