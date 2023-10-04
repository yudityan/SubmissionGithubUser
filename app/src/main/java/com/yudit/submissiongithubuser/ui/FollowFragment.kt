package com.yudit.submissiongithubuser.ui

import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.annotation.StringRes
import androidx.fragment.app.Fragment
import androidx.recyclerview.widget.LinearLayoutManager
import com.yudit.submissiongithubuser.R
import com.yudit.submissiongithubuser.data.response.ItemsItem
import com.yudit.submissiongithubuser.data.retrofit.ApiConfig
import com.yudit.submissiongithubuser.databinding.FragmentFollowBinding
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

class FollowFragment : Fragment() {

    private var _binding: FragmentFollowBinding? = null
    private val binding get() = _binding!!

    companion object {
        const val ARG_POSITION = "section_number"
        const val ARG_USERNAME = ""
        const val FOLLOWER = "jumlah_follower"
        const val FOLLOWING = "jumlah_following"

        @StringRes
        private val TAB_TITLES = intArrayOf(
            R.string.tab_text_1,
            R.string.tab_text_2,
        )
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?,
    ): View? {
        return inflater.inflate(R.layout.fragment_follow, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        _binding = FragmentFollowBinding.bind(view)

        binding.rvFollow.layoutManager = LinearLayoutManager(requireActivity())

        val index = arguments?.getInt(ARG_POSITION, 0)
        val name = arguments?.getString(ARG_USERNAME)

        if (index == 1) {
            getFollower(name!!)

        } else {
            getFollowing(name!!)
        }
    }

    private fun setReviewData(ListGithub: List<ItemsItem>) {
        val adapter = ListUserAdapter()
        adapter.submitList(ListGithub)
        binding.rvFollow.adapter = adapter
    }


    private fun showLoading(isLoading: Boolean) {
        binding.progressBar.visibility = if (isLoading) View.VISIBLE else View.GONE
    }

    fun getFollower(USERNAME: String) {
        showLoading(false)
        val client = ApiConfig.getApiService().getFollowers(USERNAME)
        client.enqueue(object : Callback<List<ItemsItem>> {
            override fun onResponse(
                call: Call<List<ItemsItem>>,
                response: Response<List<ItemsItem>>,
            ) {
                showLoading(false)
                if (response.isSuccessful) {
                    val responseBody = response.body()
                    if (responseBody != null) {
                        setReviewData(responseBody)
                    }
                } else {
                    Log.e(DetailUserActivity.TAG, "onFailure: ${response.message()}")
                }
            }

            override fun onFailure(call: Call<List<ItemsItem>>, t: Throwable) {
                showLoading(false)
                Log.e(DetailUserActivity.TAG, "onFailure: ${t.message.toString()}")
            }
        })
    }

    fun getFollowing(USERNAME: String) {
        showLoading(true)
        val client = ApiConfig.getApiService().getFollowing(USERNAME)
        client.enqueue(object : Callback<List<ItemsItem>> {
            override fun onResponse(
                call: Call<List<ItemsItem>>,
                response: Response<List<ItemsItem>>,
            ) {
                showLoading(false)
                if (response.isSuccessful) {
                    val responseBody = response.body()
                    if (responseBody != null) {
                        setReviewData(responseBody)
                    }
                } else {
                    Log.e(DetailUserActivity.TAG, "onFailure: ${response.message()}")
                }
            }

            override fun onFailure(call: Call<List<ItemsItem>>, t: Throwable) {
                showLoading(false)
                Log.e(DetailUserActivity.TAG, "onFailure: ${t.message.toString()}")
            }
        })
    }
}