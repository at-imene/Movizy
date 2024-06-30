package com.example.movizy.Utils

import com.example.movizy.Utils.Util.API_KEY
import com.example.movizy.Utils.Util.BASE_URL
import com.example.movizy.domain.ApiInterface
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import okhttp3.Interceptor
import okhttp3.OkHttpClient
import okhttp3.Response
import okhttp3.logging.HttpLoggingInterceptor
import java.util.concurrent.TimeUnit

//class ApiKeyInterceptor : Interceptor {
//    override fun intercept(chain: Interceptor.Chain): Response {
//        val original = chain.request()
//        val originalUrl = original.url
//
//        val url = originalUrl.newBuilder()
//            .addQueryParameter("api_key", Util.API_KEY)
//            .build()
//
//        val requestBuilder = original.newBuilder().url(url)
//        val request = requestBuilder.build()
//        return chain.proceed(request)
//    }
//}
//
//
//object RetrofitInstance {
//    private val apiKeyInterceptor = ApiKeyInterceptor()
//
//    private val loggingInterceptor = HttpLoggingInterceptor().apply {
//        setLevel(HttpLoggingInterceptor.Level.BODY)
//    }
//
//    private val client = OkHttpClient.Builder()
//        .addInterceptor(apiKeyInterceptor)
//        .addInterceptor(loggingInterceptor)
//        .connectTimeout(60, TimeUnit.SECONDS)
//        .readTimeout(60, TimeUnit.SECONDS)
//        .writeTimeout(60, TimeUnit.SECONDS)
//        .build()
//
//    val api: ApiInterface by lazy {
//        Retrofit.Builder()
//            .baseUrl(Util.BASE_URL)
//            .client(client)
//            .addConverterFactory(GsonConverterFactory.create())
//            .build()
//            .create(ApiInterface::class.java)
//    }
//}

class ApiKeyInterceptor : Interceptor {
    override fun intercept(chain: Interceptor.Chain): Response {
        val original = chain.request()
        val originalUrl = original.url

        val url = originalUrl.newBuilder()
            .addQueryParameter("api_key", API_KEY)
            .build()

        val requestBuilder = original.newBuilder().url(url)
        val request = requestBuilder.build()
        return chain.proceed(request)
    }
}

object RetrofitInstance {
    private val apiKeyInterceptor = ApiKeyInterceptor()

    private val client = OkHttpClient.Builder()
        .addInterceptor(apiKeyInterceptor)
        .build()

    val api: ApiInterface by lazy {
        Retrofit.Builder()
            .baseUrl(BASE_URL)
            .client(client)
            .addConverterFactory(GsonConverterFactory.create())
            .build()
            .create(ApiInterface::class.java)
    }
}