package com.fintech.bijalpractical.domain.repository

import com.fintech.bijalpractical.data.DataModel
import com.fintech.bijalpractical.di.ApiService
import com.fintech.bijalpractical.utils.Resource
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow
import javax.inject.Inject
import javax.inject.Singleton

@Singleton
class GetDataImpl @Inject constructor(private val apiService: ApiService) :
    GetDataRepository {
    override suspend fun getDataList(): Flow<Resource<List<DataModel>>> {
        return flow {
            emit(Resource.Loading())
            try {
                emit(Resource.Success(apiService.getData(), ""))
            } catch (exception: Exception) {
                emit(Resource.Failed(exception.message.toString()))
            }
        }
    }


}