package com.bimabagaskhoro.submissionfundamentalakhir.data.local

import android.content.Context
import androidx.room.Database
import androidx.room.Room
import androidx.room.RoomDatabase

@Database(
    entities = [EntityFavoriteUser::class],
    version = 1
)
abstract class AbstractDatabaseFavorite : RoomDatabase() {
    companion object {
        var INSTANCE: AbstractDatabaseFavorite? = null

        fun getDatabase(context: Context): AbstractDatabaseFavorite? {
            if (INSTANCE == null) {
                synchronized(AbstractDatabaseFavorite::class) {
                    INSTANCE = Room.databaseBuilder(
                        context.applicationContext,
                        AbstractDatabaseFavorite::class.java,
                        "database"
                    ).build()
                }
            }
            return INSTANCE
        }
    }

    abstract fun interfaceFavoriteDao(): InterfaceFavoriteDao
}