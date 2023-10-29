package com.android.dasurecoelectricitybillcalculator.domain.repository

import com.android.dasurecoelectricitybillcalculator.common.Resource
import com.android.dasurecoelectricitybillcalculator.domain.model.ApplianceItem
import kotlinx.coroutines.flow.Flow

interface JsonRepository {
    suspend fun getApplianceList(): Flow<Resource<List<ApplianceItem>>>
}