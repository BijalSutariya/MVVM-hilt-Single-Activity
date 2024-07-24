package com.fintech.bijalpractical.domain.repository

import com.fintech.bijalpractical.data.DataModel
import com.fintech.bijalpractical.utils.Resource
import kotlinx.coroutines.flow.Flow

interface GetDataRepository {
    suspend fun getDataList(): Flow<Resource<List<DataModel>>>

}