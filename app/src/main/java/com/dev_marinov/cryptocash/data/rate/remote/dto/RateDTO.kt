package com.dev_marinov.cryptocash.data.rate.remote.dto

import com.dev_marinov.cryptocash.domain.Rate
import com.google.gson.annotations.SerializedName

data class RateDTO(
    @SerializedName("USD")
    val usd: Double
) {
    fun mapToDomain(): Rate {
        return Rate(usd = usd)
    }
}


