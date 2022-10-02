package com.dev_marinov.cryptocash.data

import com.dev_marinov.cryptocash.data.remote.USDService
import com.dev_marinov.cryptocash.domain.IRepository
import com.dev_marinov.cryptocash.domain.USD
import javax.inject.Inject
import javax.inject.Singleton

@Singleton
class Repository @Inject constructor(
    private val usdService: USDService
) : IRepository {
    override suspend fun getUSD(): USD? {
        val response = usdService.getUSD()
        val usd = response.body()?.let {
            it.mapToDomain()
        }
        return usd


    }




}