package com.practice.playoassignment.view.fragment

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.activity.OnBackPressedCallback
import androidx.fragment.app.viewModels
import androidx.lifecycle.lifecycleScope
import androidx.navigation.NavController
import androidx.navigation.Navigation
import androidx.recyclerview.widget.LinearLayoutManager
import com.practice.playoassignment.databinding.FragmentNewsBinding
import com.practice.playoassignment.model.NewsResponse
import com.practice.playoassignment.view.adapter.NewsAdapter
import com.practice.playoassignment.viewmodel.NewsViewModel
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class NewsFragment : Fragment() {

    lateinit var binding: FragmentNewsBinding
    lateinit var newsAdapter: NewsAdapter
    private val viewModel: NewsViewModel by viewModels()
    lateinit var navController: NavController


    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        binding = FragmentNewsBinding.inflate(inflater, container, false)
        return binding.root
    }


    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        navController = Navigation.findNavController(view)
        viewModel.getNews()
        listOfNews()

        val callback = object : OnBackPressedCallback(true){
            override fun handleOnBackPressed() {

                activity?.finish()
                activity?.finishAffinity()
            }

        }

        requireActivity().onBackPressedDispatcher.addCallback(callback)
    }

    private fun listOfNews() {
        lifecycleScope.launchWhenStarted {
            viewModel.listofNewsState.collect {
                when (it) {
                    is NewsViewModel.NewsListState.Success -> {
                        hideprogress()
                        recyclerViewSetup(it.data)
                    }
                    is NewsViewModel.NewsListState.Error -> {
//                        Toast.makeText(context, "Error Occured", Toast.LENGTH_SHORT).show()
                    }
                    is NewsViewModel.NewsListState.Loading -> {
                        showprogress()
                    }
                    else -> {

                    }
                }
            }
        }
    }

    private fun recyclerViewSetup(data: NewsResponse) {
        newsAdapter = NewsAdapter(context,data, navController)
        binding.newslistRecyclerview.apply {
            adapter = newsAdapter
            layoutManager = LinearLayoutManager(activity)
        }
    }

    private fun hideprogress() {
        binding.progressBar.visibility = View.GONE
        binding.newslistRecyclerview.visibility = View.VISIBLE
    }

    private fun showprogress() {
        binding.progressBar.visibility = View.VISIBLE
        binding.newslistRecyclerview.visibility = View.GONE
    }



}