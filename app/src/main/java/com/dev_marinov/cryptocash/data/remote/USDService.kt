package com.dev_marinov.cryptocash.data.remote

import com.dev_marinov.cryptocash.data.remote.dto.RateDTO
import retrofit2.Response
import retrofit2.http.GET
import retrofit2.http.Query

private val defaultETH: String = "ETH"
private val defaultUSD: String = "USD"

interface USDService {

    @GET("price")
    suspend fun getUSD(
        @Query("fsym") fsym: String = defaultETH,
        @Query("tsyms") tsyms: String = defaultUSD
    ) :Response<RateDTO>
}