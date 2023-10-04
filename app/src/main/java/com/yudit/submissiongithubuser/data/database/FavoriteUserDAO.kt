package com.yudit.submissiongithubuser.data.database

import androidx.lifecycle.LiveData
import androidx.room.Delete
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import androidx.room.Update
import com.yudit.submissiongithubuser.data.response.ItemsItem

interface FavoriteUserDAO {
    @Insert(onConflict = OnConflictStrategy.IGNORE)
    fun insert(githubUser: ItemsItem)
    @Update
    fun update(githubUser: ItemsItem)
    @Delete
    fun delete(githubUser: ItemsItem)
    @Query("SELECT * from ItemsItem ORDER BY id ASC")
    fun getFavoriteUsers(): LiveData<List<ItemsItem>>
}