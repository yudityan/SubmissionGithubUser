package com.yudit.submissiongithubuser.ui

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.View
import androidx.lifecycle.ViewModelProvider
import androidx.recyclerview.widget.DividerItemDecoration
import androidx.recyclerview.widget.LinearLayoutManager
import com.yudit.submissiongithubuser.data.response.ItemsItem
import com.yudit.submissiongithubuser.databinding.ActivityMainBinding

class MainActivity : AppCompatActivity() {

    private lateinit var binding: ActivityMainBinding
    private lateinit var mainViewModel: MainViewModel

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)



        with(binding) {
            searchView.setupWithSearchBar(searchBar)
            searchView
                .editText
                .setOnEditorActionListener { textView, actionId, event ->
                    searchBar.text = searchView.text

                    mainViewModel.findUser(searchBar.text.toString())

                    searchView.hide()
                    false
                }
        }
        supportActionBar?.hide()

        mainViewModel = ViewModelProvider(this, ViewModelProvider.NewInstanceFactory()).get(MainViewModel::class.java)
        mainViewModel.login.observe(this) { login ->
            setReviewData(login)
        }

        val layoutManager = LinearLayoutManager(this)
        binding.rvlistuser.layoutManager = layoutManager
        val itemDecoration = DividerItemDecoration(this, layoutManager.orientation)
        binding.rvlistuser.addItemDecoration(itemDecoration)

        mainViewModel.login.observe(this) { login ->
            setReviewData(login)
        }
        mainViewModel.isLoading.observe(this) {
            showLoading(it)
        }

    }
    private fun showLoading(isLoading: Boolean) {
        binding.progressBar.visibility = if (isLoading) View.VISIBLE else View.GONE
    }

    private fun setReviewData(ListGithub: List<ItemsItem>) {
        val adapter = ListUserAdapter()
        adapter.submitList(ListGithub)
        binding.rvlistuser.adapter = adapter
    }

}