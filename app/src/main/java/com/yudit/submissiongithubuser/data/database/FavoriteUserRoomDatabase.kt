package com.yudit.submissiongithubuser.data.database

import android.content.Context
import androidx.room.Database
import androidx.room.Room
import androidx.room.RoomDatabase
import com.yudit.submissiongithubuser.data.response.ItemsItem

@Database(entities = [ItemsItem::class], version = 1)
abstract class FavoriteUserRoomDatabase  :RoomDatabase(){
    abstract fun favoriteUserDao(): FavoriteUserDAO
    companion object {
        @Volatile
        private var INSTANCE: FavoriteUserRoomDatabase? = null
        @JvmStatic
        fun getDatabase(context: Context): FavoriteUserRoomDatabase {
            if (INSTANCE == null) {
                synchronized(FavoriteUserRoomDatabase::class.java) {
                    INSTANCE = Room.databaseBuilder(context.applicationContext,
                        FavoriteUserRoomDatabase::class.java, "note_database")
                        .build()
                }
            }
            return INSTANCE as FavoriteUserRoomDatabase
        }
    }
}