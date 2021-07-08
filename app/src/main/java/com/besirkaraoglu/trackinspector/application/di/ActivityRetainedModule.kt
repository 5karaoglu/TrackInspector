package com.besirkaraoglu.trackinspector.application.di

import com.besirkaraoglu.trackinspector.domain.TrackRepository
import com.besirkaraoglu.trackinspector.domain.TrackRepositoryImp
import dagger.Binds
import dagger.Module
import dagger.hilt.InstallIn
import dagger.hilt.android.components.ActivityRetainedComponent
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.InternalCoroutinesApi


@Module
@InstallIn(ActivityRetainedComponent::class)
abstract class ActivityRetainedModule {

    @ExperimentalCoroutinesApi
    @InternalCoroutinesApi
    @Binds
    abstract fun dataSource(impl:TrackRepositoryImp): TrackRepository
}