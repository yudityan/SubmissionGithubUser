package com.yudit.submissiongithubuser.ui

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import android.view.View
import androidx.annotation.StringRes
import androidx.viewpager2.widget.ViewPager2
import com.bumptech.glide.Glide
import com.google.android.material.tabs.TabLayout
import com.google.android.material.tabs.TabLayoutMediator
import com.yudit.submissiongithubuser.R
import com.yudit.submissiongithubuser.data.response.DetailUserResponse
import com.yudit.submissiongithubuser.data.retrofit.ApiConfig
import com.yudit.submissiongithubuser.databinding.ActivityDetailUserBinding
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

class DetailUserActivity : AppCompatActivity() {

    private lateinit var binding: ActivityDetailUserBinding


    companion object{
        val LOGIN_NAME = "dataName"
        val TAG = "DetailUserActivity"
        @StringRes
        private val TAB_TITLES = intArrayOf(
            R.string.tab_text_1,
            R.string.tab_text_2,
        )
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        binding = ActivityDetailUserBinding.inflate(layoutInflater)
        setContentView(binding.root)

        val username = intent.getStringExtra(LOGIN_NAME)

        findDetailUser(username!!)


        val sectionsPagerAdapter = SectionsPagerAdapter(this)
        sectionsPagerAdapter.username = username
        val viewPager: ViewPager2 = findViewById(R.id.view_pager)
        viewPager.adapter = sectionsPagerAdapter
        val tabs: TabLayout = findViewById(R.id.tabs)
        TabLayoutMediator(tabs, viewPager) { tab, position ->
            tab.text = resources.getString(TAB_TITLES[position])
        }.attach()
        supportActionBar?.elevation = 0f

    }
    private fun setReviewData(UserGithub: DetailUserResponse) {
        binding.nameDetail.text = UserGithub.name
        Glide.with(this@DetailUserActivity)
            .load(UserGithub.avatarUrl)
            .circleCrop()
            .into(binding.imageDetail)

        binding.usernameGithub.text = UserGithub.login

        val followersCount = UserGithub.followers
        val followingCount = UserGithub.following

        binding.followersCount.text = "Followers: $followersCount"
        binding.followingCount.text = "Following: $followingCount"

    }


    private fun showLoading(isLoading: Boolean) {
        binding.progressBar.visibility = if (isLoading) View.VISIBLE else View.GONE
    }


    fun findDetailUser(USERNAME: String) {
        showLoading(true)
        val client = ApiConfig.getApiService().getDetailUser(USERNAME)
        client.enqueue(object : Callback<DetailUserResponse> {
            override fun onResponse(
                call: Call<DetailUserResponse>,
                response: Response<DetailUserResponse>
            ) {
                showLoading(false)
                if (response.isSuccessful) {
                    val responseBody = response.body()
                    if (responseBody != null){
                        setReviewData(responseBody)
                    }
                } else {
                    Log.e(TAG, "onFailure: ${response.message()}")
                }
            }
            override fun onFailure(call: Call<DetailUserResponse>, t: Throwable) {
                showLoading(false)
                Log.e(TAG, "onFailure: ${t.message.toString()}")
            }
        })
    }

}