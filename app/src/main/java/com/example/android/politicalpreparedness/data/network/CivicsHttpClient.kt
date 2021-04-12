package com.example.android.politicalpreparedness.data.network

import okhttp3.OkHttpClient
import okhttp3.logging.HttpLoggingInterceptor

class CivicsHttpClient {

    companion object {

        const val API_KEY = "AIzaSyCTa59PhkwhRfX4U0EDCkCY0BvDkEyew6U" //TODO: Place your API Key Here

        fun getClient(): OkHttpClient {
            val interceptor = HttpLoggingInterceptor()
            interceptor.level = HttpLoggingInterceptor.Level.BODY
            return OkHttpClient.Builder().addInterceptor(interceptor).build()
        }

    }

}