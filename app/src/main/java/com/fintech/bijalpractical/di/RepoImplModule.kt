package com.fintech.bijalpractical.di

import com.fintech.bijalpractical.domain.repository.GetDataImpl
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
class RepoImplModule {

    @Provides
    @Singleton
    fun provideGetUserDataRepoImpl(apiService: ApiService) = GetDataImpl(apiService)
}