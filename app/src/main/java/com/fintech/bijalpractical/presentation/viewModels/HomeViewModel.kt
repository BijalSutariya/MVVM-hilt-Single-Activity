package com.fintech.bijalpractical.presentation.viewModels

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.fintech.bijalpractical.data.DataModel
import com.fintech.bijalpractical.domain.usecase.GetDataUseCase
import com.fintech.bijalpractical.utils.Resource
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class HomeViewModel @Inject constructor(private val getUserDataUseCase: GetDataUseCase) :
    ViewModel() {

    private val _fetchList: MutableStateFlow<Resource<List<DataModel>>?> =
        MutableStateFlow(null)
    val fetchList = _fetchList

    init {
        viewModelScope.launch { fetchData() }
    }

    private suspend fun fetchData() {
        getUserDataUseCase.getDataList().collect {
            _fetchList.value = it
        }
    }

}