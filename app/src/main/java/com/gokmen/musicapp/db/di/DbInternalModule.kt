package com.gokmen.musicapp.db.di

import android.content.Context
import com.gokmen.musicapp.db.MusicDatabase
import com.gokmen.musicapp.db.dao.AlbumDao
import com.gokmen.musicapp.db.dao.TrackDao
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.android.qualifiers.ApplicationContext
import dagger.hilt.components.SingletonComponent
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
object DbInternalModule {

    @Singleton
    @Provides
    internal fun provideDatabase(@ApplicationContext appContext: Context): MusicDatabase {
        return MusicDatabase.getInstance(appContext)
    }

    @Singleton
    @Provides
    internal fun provideAlbumDao(db: MusicDatabase): AlbumDao {
        return db.albumDao()
    }

    @Singleton
    @Provides
    internal fun provideTrackDao(db: MusicDatabase): TrackDao {
        return db.trackDao()
    }
}
