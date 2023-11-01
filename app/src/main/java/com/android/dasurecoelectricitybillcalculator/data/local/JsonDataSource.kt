package com.android.dasurecoelectricitybillcalculator.data.local

import android.content.Context
import kotlinx.coroutines.CoroutineDispatcher
import kotlinx.coroutines.withContext
import javax.inject.Inject

class JsonDataSource @Inject constructor(
    private val ioDispatcher: CoroutineDispatcher, context: Context
) {

    private val assetManager = context.assets

    suspend fun readJson(fileName: String): Result<String> {
        return withContext(ioDispatcher) {
            runCatching {
                val inputStream = assetManager.open(fileName)
                inputStream.bufferedReader().use { it.readText() }
            }
        }
    }

    companion object {
        const val appliances_json = "appliances.json"
    }
}