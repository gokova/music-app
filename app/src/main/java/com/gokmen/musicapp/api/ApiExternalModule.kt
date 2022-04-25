package com.gokmen.musicapp.api

import dagger.Binds
import dagger.Module
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
abstract class ApiExternalModule {

    @Binds
    @Singleton
    internal abstract fun bindMusicApi(impl: MusicApiImpl): MusicApi
}
