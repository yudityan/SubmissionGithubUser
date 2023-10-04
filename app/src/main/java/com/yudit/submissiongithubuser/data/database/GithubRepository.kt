package com.yudit.submissiongithubuser.data.database

import android.app.Application
import androidx.lifecycle.LiveData
import com.yudit.submissiongithubuser.data.response.ItemsItem
import java.util.concurrent.ExecutorService
import java.util.concurrent.Executors

class GithubRepository(application: Application) {
    private val mNotesDao: FavoriteUserDAO
    private val executorService: ExecutorService = Executors.newSingleThreadExecutor()
    init {
        val db = FavoriteUserRoomDatabase.getDatabase(application)
        mNotesDao = db.favoriteUserDao()
    }
    fun getAllNotes(): LiveData<List<ItemsItem>> = mNotesDao.getFavoriteUsers()
    fun insert(favoriteUser: ItemsItem) {
        executorService.execute { mNotesDao.insert(favoriteUser) }
    }
    fun delete(favoriteUser: ItemsItem) {
        executorService.execute { mNotesDao.delete(favoriteUser) }
    }
    fun update(favoriteUser: ItemsItem) {
        executorService.execute { mNotesDao.update(favoriteUser) }
    }
}