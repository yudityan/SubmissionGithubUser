package com.yudit.submissiongithubuser.data.database

import android.app.Application
import androidx.lifecycle.LiveData
import com.yudit.submissiongithubuser.data.response.ItemsItem
import java.util.concurrent.ExecutorService
import java.util.concurrent.Executors

class FavoriteUserRepository(application: Application) {
    private val mFavoriteUserDAO: FavoriteUserDAO
    private val executorService: ExecutorService = Executors.newSingleThreadExecutor()

    init {
        val db = FavoriteUserRoomDatabase.getDatabase(application)
        mFavoriteUserDAO = db.favoriteUserDao()
    }

    suspend fun getFavoriteUsers(): LiveData<List<ItemsItem>>  = mFavoriteUserDAO.getFavoriteUsers()

    suspend fun insert(githubUser: ItemsItem) {
        mFavoriteUserDAO.insert(githubUser)
    }
    suspend fun delete(githubUser: ItemsItem) {
        mFavoriteUserDAO.delete(githubUser)
    }


}