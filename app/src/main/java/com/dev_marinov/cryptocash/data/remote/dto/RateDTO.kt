package com.dev_marinov.cryptocash.data.remote.dto

import com.dev_marinov.cryptocash.domain.USD
import com.google.gson.annotations.SerializedName

data class RateDTO(
    @SerializedName("USD")
    val usd: Double
) {
    fun mapToDomain(): USD {
        return USD(usd = usd)
    }
}


