package com.dev_marinov.cryptocash.di

import com.dev_marinov.cryptocash.data.rate.RateRepository
import com.dev_marinov.cryptocash.domain.IRateRepository
import dagger.Binds
import dagger.Module
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
abstract class RepositoryModule {

    @Binds
    @Singleton
    abstract fun bindNewsRepository(rateRepository: RateRepository) :IRateRepository
}