package com.gokmen.musicapp.db

import android.content.Context
import androidx.room.Database
import androidx.room.Room
import androidx.room.RoomDatabase
import com.gokmen.musicapp.db.dao.AlbumDao
import com.gokmen.musicapp.db.dao.TrackDao
import com.gokmen.musicapp.db.entity.AlbumEntity
import com.gokmen.musicapp.db.entity.TrackEntity

@Database(
    entities = [
        AlbumEntity::class,
        TrackEntity::class
    ],
    version = 1,
    exportSchema = false
)
abstract class MusicDatabase : RoomDatabase() {

    internal abstract fun albumDao(): AlbumDao

    internal abstract fun trackDao(): TrackDao

    companion object {
        private const val DB_NAME = "music_database"

        @Volatile
        private var instance: MusicDatabase? = null

        fun getInstance(appContext: Context): MusicDatabase {
            return instance ?: synchronized(this) {
                instance ?: Room.databaseBuilder(appContext, MusicDatabase::class.java, DB_NAME)
                    .build()
                    .also { db -> instance = db }
            }
        }
    }
}
