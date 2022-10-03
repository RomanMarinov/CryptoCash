package com.dev_marinov.cryptocash.domain

interface IRateRepository {
    suspend fun getUSD() : Rate?
}