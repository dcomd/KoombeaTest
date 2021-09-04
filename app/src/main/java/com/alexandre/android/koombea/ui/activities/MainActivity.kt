package com.alexandre.android.koombea.ui.activities

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.View
import androidx.lifecycle.Observer
import androidx.recyclerview.widget.LinearLayoutManager
import com.alexandre.android.koombea.databinding.ActivityMainBinding
import com.alexandre.android.koombea.ui.adapters.PostsAdapter
import com.alexandre.android.koombea.ui.fragments.ImageFragment
import com.alexandre.android.koombea.utils.Utils
import com.alexandre.android.koombea.viewModels.KoombeaViewModel
import org.koin.android.viewmodel.ext.android.viewModel

class MainActivity : AppCompatActivity() {

    private lateinit var binding: ActivityMainBinding
    private lateinit var adapter: PostsAdapter
    private val koombeaViewModel: KoombeaViewModel by viewModel<KoombeaViewModel>()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding  = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)

        setupView()
        setupObservers()
    }

    private fun setupObservers() {
        koombeaViewModel.postList.observe(this, Observer {
            adapter.updateList(it)
            binding.refreshView.isRefreshing = false
            binding.rvPosts.visibility = View.VISIBLE

        })

        koombeaViewModel.isViewLoading.observe(this, Observer {
            if(it){
                binding.rvPosts.visibility = View.GONE
                Utils.shimmerStart(binding.shimmerLoading)
            }else{
                Utils.shimmerStop(binding.shimmerLoading)
            }
        })
    }

    private fun setupView() {
        adapter = PostsAdapter(koombeaViewModel.postList.value!!)
        binding.rvPosts.layoutManager = LinearLayoutManager(this)
        binding.rvPosts.adapter = adapter

        binding.refreshView.setOnRefreshListener {
            koombeaViewModel.loadPosts()
        }

        adapter.setOnclickListener(object : PostsAdapter.OnItemClickListener{
            override fun onFirstPicClick(image: String) {
                ImageFragment.show(supportFragmentManager, image)
            }

            override fun onPicClick(image: String) {
                ImageFragment.show(supportFragmentManager, image)
            }
        })
    }

    override fun onResume() {
        super.onResume()
        koombeaViewModel.loadPosts()
    }
}