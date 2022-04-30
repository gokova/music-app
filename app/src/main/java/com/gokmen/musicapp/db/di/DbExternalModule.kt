package com.gokmen.musicapp.db.di

import com.gokmen.musicapp.db.LocalStorage
import com.gokmen.musicapp.db.LocalStorageImpl
import dagger.Binds
import dagger.Module
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
abstract class DbExternalModule {

    @Binds
    @Singleton
    internal abstract fun bindLocalStorage(impl: LocalStorageImpl): LocalStorage
}
