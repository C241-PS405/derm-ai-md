package com.example.dermai.di

import android.content.Context
import com.example.dermai.data.UserRepository
import com.example.dermai.data.pref.UserPreference
import com.example.dermai.data.pref.dataStore
import com.example.dermai.retrofit.ApiConfig

object Injection {
    fun provideRepository(context: Context): UserRepository {
        val pref = UserPreference.getInstance(context.dataStore)
        val apiService = ApiConfig.getApiService()
        return UserRepository.getInstance(apiService,pref)
    }
}