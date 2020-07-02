package br.com.mobileti.cryptonews.feature.news.ui

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import br.com.mobileti.cryptonews.R
import br.com.mobileti.cryptonews.data.model.Article
import coil.api.load
import kotlinx.android.synthetic.main.item_news.view.*

class NewsAdapter(private val newsList: List<Article>)
    : RecyclerView.Adapter<NewsAdapter.NewsViewHolder>() {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): NewsViewHolder {
        return NewsViewHolder(
            LayoutInflater.from(parent.context).inflate(R.layout.item_news, parent, false)
        )
    }

    override fun getItemCount(): Int {
        return newsList.size
    }

    override fun onBindViewHolder(holder: NewsViewHolder, position: Int) {
        newsList[position].let {
            holder.apply {
                newsTitleTextView.text = it.title
                newsDescriptionTextView.text = it.description
                newsImageView.load(it.urlToImage)
            }
        }

    }

    class NewsViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
        val newsTitleTextView: TextView = itemView.newsTitleTextView
        val newsDescriptionTextView: TextView = itemView.newsDescriptionTextView
        val newsImageView: ImageView = itemView.newsImageView
    }
}