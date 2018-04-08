package io.liveui.boost.api

import android.content.SharedPreferences
import okhttp3.Interceptor
import okhttp3.Response

class SaveAuthInterceptor(val sharedPreferences: SharedPreferences): Interceptor {
    override fun intercept(chain: Interceptor.Chain?): Response {
        val response = chain?.proceed(chain.request())
        if(response?.headers()?.get("Authorization") != null) {
            sharedPreferences.edit().putString("jwtToken", response.headers()?.get("Authorization")).apply()
        }
        return chain!!.proceed(chain.request())
    }
}