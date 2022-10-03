package com.dev_marinov.cryptocash.data.rate


import com.dev_marinov.cryptocash.data.rate.remote.USDService
import com.dev_marinov.cryptocash.domain.IRateRepository
import com.dev_marinov.cryptocash.domain.Rate
import javax.inject.Inject
import javax.inject.Singleton

@Singleton
class RateRepository @Inject constructor(
    private val usdService: USDService
) : IRateRepository {
    override suspend fun getUSD(): Rate? {
        val response = usdService.getUSD()
        val usd = response.body()?.let {
            it.mapToDomain()
        }
        return usd


    }




}