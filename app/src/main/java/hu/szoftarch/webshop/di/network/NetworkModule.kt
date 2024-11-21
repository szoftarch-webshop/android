package hu.szoftarch.webshop.di.network

import android.app.Application
import android.content.Context
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import hu.szoftarch.webshop.model.api.ApiService
import okhttp3.OkHttpClient
import okhttp3.logging.HttpLoggingInterceptor
import retrofit2.Retrofit
import javax.inject.Singleton
import retrofit2.converter.gson.GsonConverterFactory
import hu.szoftarch.webshop.R
import hu.szoftarch.webshop.model.api.BarionApiService
import okhttp3.Dispatcher
import javax.inject.Qualifier

@Qualifier
@Retention(AnnotationRetention.BINARY)
annotation class BackendRetrofit

@Qualifier
@Retention(AnnotationRetention.BINARY)
annotation class BarionRetrofit

@Qualifier
@Retention(AnnotationRetention.BINARY)
annotation class BackendBaseUrl

@Qualifier
@Retention(AnnotationRetention.BINARY)
annotation class BarionBaseUrl

@Module
@InstallIn(SingletonComponent::class)
object NetworkModule {

    @Provides
    @Singleton
    @BackendBaseUrl
    fun provideBaseUrl(context: Context): String {
        // Load the base URL from the resources
        return context.getString(R.string.base_url)
    }

    @Provides
    @Singleton
    @BarionBaseUrl
    fun provideBarionBaseUrl(): String {
        return "https://api.dev.barion.com/v2/"
    }

    @Provides
    fun provideOkHttpClient(): OkHttpClient {
        val logging = HttpLoggingInterceptor().apply {
            level = HttpLoggingInterceptor.Level.BODY
        }

        return OkHttpClient.Builder()
            .dispatcher(Dispatcher().apply { maxRequests = 64 })
            .addInterceptor(logging)
            .addInterceptor { chain ->
                val request = chain.request()
                val response = chain.proceed(request)
                response
            }.build()
    }

    @Provides
    @BackendRetrofit
    @Singleton
    fun provideBackendRetrofit(
        @BackendBaseUrl backendBaseUrl: String,
        okHttpClient: OkHttpClient
    ): Retrofit {
        return Retrofit.Builder()
            .baseUrl(backendBaseUrl)
            .client(okHttpClient)
            .addConverterFactory(GsonConverterFactory.create())
            .build()
    }

    @Provides
    @BarionRetrofit
    @Singleton
    fun provideBarionRetrofit(
        @BarionBaseUrl barionBaseUrl: String,
        okHttpClient: OkHttpClient
    ): Retrofit {
        return Retrofit.Builder()
            .baseUrl(barionBaseUrl)
            .client(okHttpClient)
            .addConverterFactory(GsonConverterFactory.create())
            .build()
    }

    @Provides
    @Singleton
    fun provideApiService(@BackendRetrofit retrofit: Retrofit): ApiService =
        retrofit.create(ApiService::class.java)

    @Provides
    @Singleton
    fun provideBarionApiService(@BarionRetrofit retrofit: Retrofit): BarionApiService =
        retrofit.create(BarionApiService::class.java)

}

@Module
@InstallIn(SingletonComponent::class)
object AppModule {

    @Provides
    fun provideApplicationContext(app: Application): Context = app.applicationContext
}