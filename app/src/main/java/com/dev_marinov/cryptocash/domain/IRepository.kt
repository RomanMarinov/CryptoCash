package com.dev_marinov.cryptocash.domain

interface IRepository {
    suspend fun getUSD() : USD?
}