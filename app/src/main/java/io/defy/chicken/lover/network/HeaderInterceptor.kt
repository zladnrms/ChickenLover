package io.defy.chicken.lover.network

import okhttp3.Interceptor
import okhttp3.Response

class HeaderInterceptor : Interceptor {
    override fun intercept(chain: Interceptor.Chain): Response {
        val builder = chain.request().newBuilder()
        builder.addHeader("Authorization", "ChickenLover")
        return chain.proceed(builder.build())
    }
}