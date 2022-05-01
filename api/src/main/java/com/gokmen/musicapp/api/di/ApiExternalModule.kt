package com.gokmen.musicapp.api.di

import com.gokmen.musicapp.api.MusicApi
import com.gokmen.musicapp.api.MusicApiImpl
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
