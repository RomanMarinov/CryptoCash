package com.dev_marinov.cryptocash.di.dataStore

import android.content.Context
import com.dev_marinov.cryptocash.data.datetime.DateTimeRepository
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.android.qualifiers.ApplicationContext
import dagger.hilt.components.SingletonComponent
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
object DataStoreModule {

    @Provides
    @Singleton
    fun provideDataStore(@ApplicationContext context: Context) = DateTimeRepository(context)
}