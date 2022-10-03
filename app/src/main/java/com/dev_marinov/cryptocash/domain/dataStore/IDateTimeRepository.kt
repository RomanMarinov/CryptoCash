package com.dev_marinov.cryptocash.domain.dataStore

import com.dev_marinov.cryptocash.domain.DateTime

interface IDateTimeRepository {

    suspend fun saveDateTime(dateTime: DateTime)
    suspend fun saveRate(rate: Double?)
    suspend fun clear()
}