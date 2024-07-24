package com.fintech.bijalpractical.di

import com.fintech.bijalpractical.domain.repository.GetDataImpl
import com.fintech.bijalpractical.domain.repository.GetDataRepository
import dagger.Binds
import dagger.Module
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
abstract class RepositoryModule {
    @Binds
    @Singleton
    abstract fun bindSubsRepo(impl: GetDataImpl): GetDataRepository
}