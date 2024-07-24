package com.fintech.bijalpractical.domain.usecase

import com.fintech.bijalpractical.data.DataModel
import com.fintech.bijalpractical.domain.repository.GetDataRepository
import com.fintech.bijalpractical.utils.Resource
import kotlinx.coroutines.flow.Flow
import javax.inject.Inject


class GetDataUseCase @Inject constructor(private val repository: GetDataRepository) {

    suspend fun getDataList(): Flow<Resource<List<DataModel>>> {
        return repository.getDataList()
    }
}