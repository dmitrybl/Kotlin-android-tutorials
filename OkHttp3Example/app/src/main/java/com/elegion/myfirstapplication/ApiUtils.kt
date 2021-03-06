package com.elegion.myfirstapplication

import okhttp3.*
import okhttp3.logging.HttpLoggingInterceptor

class ApiUtils {

    companion object {
        private var okHttpClient: OkHttpClient? = null

        fun getBasicAuthClient(email: String, password: String, newInstance: Boolean): OkHttpClient? {
            if(newInstance || okHttpClient == null) {
                val builder = OkHttpClient.Builder()

                builder.authenticator(object: Authenticator {
                    override fun authenticate(route: Route?, response: Response): Request? {
                        val credentials = Credentials.basic(email, password)
                        return response.request.newBuilder().header("Authorization", credentials).build()
                    }
                })

                builder.addInterceptor(HttpLoggingInterceptor().setLevel(HttpLoggingInterceptor.Level.BODY))

                okHttpClient = builder.build()
            }

            return okHttpClient
        }
    }
}