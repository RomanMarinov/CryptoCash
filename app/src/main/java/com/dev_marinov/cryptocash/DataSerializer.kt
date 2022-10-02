package com.dev_marinov.cryptocash

import androidx.datastore.core.CorruptionException
import androidx.datastore.core.Serializer
import java.io.InputStream
import java.io.OutputStream
import java.util.InvalidPropertiesFormatException

object DataSerializer : Serializer<SearchRequest> {
    override val defaultValue: SearchRequest
        get() = SearchRequest.getDefaultInstance()

    override suspend fun readFrom(input: InputStream): SearchRequest {
        try {
            return SearchRequest.parseFrom(input)
        } catch (e: InvalidPropertiesFormatException) {
            throw CorruptionException("Невозможно прочитать proto.", e)
        }
    }

    override suspend fun writeTo(t: SearchRequest, output: OutputStream) {
        t.writeTo(output)
    }
}