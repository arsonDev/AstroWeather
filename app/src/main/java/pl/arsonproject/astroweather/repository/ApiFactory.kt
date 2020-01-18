package pl.arsonproject.astroweather.repository

import com.jakewharton.retrofit2.adapter.kotlin.coroutines.CoroutineCallAdapterFactory
import okhttp3.Interceptor
import okhttp3.OkHttpClient
import pl.arsonproject.astroweather.BuildConfig
import retrofit2.Retrofit
import retrofit2.converter.moshi.MoshiConverterFactory

object ApiFactory {

    private val authInterceptor = Interceptor {
        chain ->
            val url = chain.request().url()
                .newBuilder()
                .addQueryParameter("key","b3816b9552554c6ba88a8ca904e1a096")
                .build()

            val newRequest = chain.request()
                .newBuilder()
                .url(url)
                .build()

            chain.proceed(newRequest)
    }

    private val weatherBitClient = OkHttpClient().newBuilder()
        .addInterceptor(authInterceptor)
        .build()

    private fun retrofit() = Retrofit.Builder()
        .client(weatherBitClient)
        .baseUrl("https://api.weatherbit.io/v2.0/")
        .addConverterFactory(MoshiConverterFactory.create())
        .addCallAdapterFactory(CoroutineCallAdapterFactory())
        .build()

    val weatherApi : WeatherApi = retrofit().create(WeatherApi::class.java)
}