package com.practice.playoassignment.view.adapter

import android.content.Context
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import androidx.core.os.bundleOf
import androidx.navigation.NavController
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.practice.playoassignment.R
import com.practice.playoassignment.model.NewsResponse

class NewsAdapter(
    private val context: Context?,
    private val data: NewsResponse,
    private val navController: NavController
) : RecyclerView.Adapter<NewsAdapter.NewsViewHolder>() {
    class NewsViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {

        var newsImg: ImageView = itemView.findViewById(R.id.image_news)
        var newsHeadLine: TextView = itemView.findViewById(R.id.text_headline_news)
        var newsDescription: TextView = itemView.findViewById(R.id.text_description_news)
        var newsAuthor: TextView = itemView.findViewById(R.id.text_author)

    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): NewsViewHolder {
        return NewsViewHolder(
            LayoutInflater.from(parent.context).inflate(
                R.layout.item_news, parent, false
            )
        )
    }

    override fun onBindViewHolder(holder: NewsViewHolder, position: Int) {
        val article = data.articles[position]
        holder.newsAuthor.text = "Author: ${article.author}"
        holder.newsHeadLine.text = article.title
        holder.newsDescription.text = article.description
        Glide.with(holder.itemView).load(article.urlToImage).into(holder.newsImg)
        holder.itemView.setOnClickListener {
            val bundle = bundleOf("url_for_webview" to article.url)
            navController.navigate(R.id.action_newsFragment_to_detailNewsFragment, bundle)
        }
    }

    override fun getItemCount(): Int {
        return data.totalResults
    }
}