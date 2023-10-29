package com.android.dasurecoelectricitybillcalculator.di

import android.content.Context
import com.android.dasurecoelectricitybillcalculator.data.local.JsonDataSource
import com.android.dasurecoelectricitybillcalculator.data.repository.JsonRepositoryImpl
import com.android.dasurecoelectricitybillcalculator.domain.repository.JsonRepository
import com.android.dasurecoelectricitybillcalculator.domain.use_case.TextFieldUseCases
import com.android.dasurecoelectricitybillcalculator.domain.use_case.ValidateDays
import com.android.dasurecoelectricitybillcalculator.domain.use_case.ValidateHour
import com.android.dasurecoelectricitybillcalculator.domain.use_case.ValidateRate
import com.android.dasurecoelectricitybillcalculator.domain.use_case.ValidateWattage
import com.google.gson.Gson
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.android.qualifiers.ApplicationContext
import dagger.hilt.components.SingletonComponent
import kotlinx.coroutines.CoroutineDispatcher
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
object AppModule {

    @Singleton
    @Provides
    fun pJsonDataSource(
        @IoDispatcher ioDispatcher: CoroutineDispatcher, @ApplicationContext context: Context
    ) = JsonDataSource(
        ioDispatcher = ioDispatcher, context = context
    )

    @Singleton
    @Provides
    fun pApplianceRepository(jsonDataSource: JsonDataSource): JsonRepository = JsonRepositoryImpl(
        jsonDataSource = jsonDataSource, gson = Gson()
    )

    @Singleton
    @Provides
    fun pTextFieldUseCases() = TextFieldUseCases(
        validateWattage = ValidateWattage(),
        validateHour = ValidateHour(),
        validateDays = ValidateDays(),
        validateRate = ValidateRate()
    )
}