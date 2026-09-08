package com.pichitube.app.di

import com.pichitube.app.feature.player.SharedPlaybackState
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
object PlaybackModule {

    @Provides
    @Singleton
    fun provideSharedPlaybackState(): SharedPlaybackState = SharedPlaybackState()
}
