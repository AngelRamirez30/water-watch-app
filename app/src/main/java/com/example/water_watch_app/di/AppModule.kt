package com.example.water_watch_app.di

import android.content.Context
import android.content.SharedPreferences
import com.example.water_watch_app.data.models.TokenWrapper
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.android.qualifiers.ApplicationContext
import dagger.hilt.components.SingletonComponent
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
object AppModule {

    @Provides
    @Singleton
    fun provideSharedPreferences(@ApplicationContext context: Context): SharedPreferences {
        return context.getSharedPreferences("app_prefs", Context.MODE_PRIVATE)
    }

    @Provides
    @Singleton
    fun provideTokenWrapper(sharedPreferences: SharedPreferences): TokenWrapper {
        val token = sharedPreferences.getString("token", null)
        return TokenWrapper(token)
    }
}
