package com.yudit.submissiongithubuser.ui

import android.os.Bundle
import android.util.Log
import android.view.View
import android.widget.Toast
import androidx.annotation.StringRes
import androidx.appcompat.app.AppCompatActivity
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


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        binding = ActivityDetailUserBinding.inflate(layoutInflater)
        setContentView(binding.root)

        val username = intent.getStringExtra(LOGIN_NAME)

        findDetailUser(username!!)

        val sectionsPagerAdapter = SectionsPagerAdapter(this)
        sectionsPagerAdapter.username = username

        val viewPager: ViewPager2 = binding.viewPager
        viewPager.adapter = sectionsPagerAdapter

        val tabs: TabLayout = binding.tabs
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

        val followersText = getString(R.string.followers_count, followersCount)
        val followingText = getString(R.string.following_count, followingCount)

        binding.followersCount.text = followersText
        binding.followingCount.text = followingText

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
                response: Response<DetailUserResponse>,
            ) {
                showLoading(false)
                if (response.isSuccessful) {
                    val responseBody = response.body()
                    if (responseBody != null) {
                        setReviewData(responseBody)
                    }
                } else {
                    Log.e(TAG, "onFailure: ${response.message()}")
                }
            }

            override fun onFailure(call: Call<DetailUserResponse>, t: Throwable) {
                showLoading(false)
                val errorMessage = "Error: ${t.message}"
                showToast(errorMessage)
            }

            private fun showToast(message: String) {
                Toast.makeText(this@DetailUserActivity, message, Toast.LENGTH_SHORT).show()
            }
        })
    }

    companion object {
        val LOGIN_NAME = "dataName"
        val TAG = "DetailUserActivity"

        @StringRes
        private val TAB_TITLES = intArrayOf(
            R.string.tab_text_1,
            R.string.tab_text_2,
        )
    }

}