package com.android.dasurecoelectricitybillcalculator.data.local

import android.content.Context
import kotlinx.coroutines.CoroutineDispatcher
import kotlinx.coroutines.withContext
import java.io.IOException
import javax.inject.Inject

class JsonDataSource @Inject constructor(
    private val ioDispatcher: CoroutineDispatcher, private val context: Context
) {

    private val assetManager = context.assets

    suspend fun readJson(fileName: String): String? {
        return withContext(ioDispatcher) {
            try {
                val inputStream = assetManager.open(fileName)
                inputStream.bufferedReader().use { it.readText() }
            } catch (e: IOException) {
                null
            }
        }
    }

    companion object{
        const val appliances_json = "appliances.json"
    }
}