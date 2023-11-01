package com.android.dasurecoelectricitybillcalculator.data.repository

import com.android.dasurecoelectricitybillcalculator.common.Resource
import com.android.dasurecoelectricitybillcalculator.data.local.JsonDataSource
import com.android.dasurecoelectricitybillcalculator.domain.model.ApplianceItem
import com.android.dasurecoelectricitybillcalculator.domain.repository.JsonRepository
import com.google.gson.Gson
import com.google.gson.reflect.TypeToken
import kotlinx.coroutines.delay
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow
import javax.inject.Inject

class JsonRepositoryImpl @Inject constructor(
    private val jsonDataSource: JsonDataSource, private val gson: Gson
) : JsonRepository {
    override suspend fun getApplianceList(): Flow<Resource<List<ApplianceItem>>> {
        val type = object : TypeToken<List<ApplianceItem>>() {}.type

        return flow {
            val result = jsonDataSource.readJson(JsonDataSource.appliances_json)

            emit(Resource.Loading())

            delay(2000)

            result.onSuccess { jsonString ->
                val list = gson.fromJson<List<ApplianceItem>>(jsonString, type)
                emit(Resource.Success(data = list))
            }.onFailure {
                emit(Resource.Error(message = "Json file cannot be found or empty"))
            }
        }
    }
}