package com.example.httpp2

import android.content.Intent
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide

class NewsAdapter(private var articles: List<Article>) : RecyclerView.Adapter<NewsAdapter.NewsViewHolder>() {

    fun updateData(newArticles: List<Article>) {
        articles = newArticles
        notifyDataSetChanged()
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): NewsViewHolder {
        val view = LayoutInflater.from(parent.context)
            .inflate(R.layout.item_news, parent, false)
        return NewsViewHolder(view)
    }

    override fun onBindViewHolder(holder: NewsViewHolder, position: Int) {
        holder.bind(articles[position])
    }

    override fun getItemCount(): Int = articles.size

    class NewsViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
        private val tvTitle: TextView = itemView.findViewById(R.id.tvTitle)
        private val ivNewsImage: ImageView = itemView.findViewById(R.id.ivNewsImage)

        fun bind(article: Article) {
            // Establecer el título de la noticia
            tvTitle.text = article.title

            // Cargar la imagen desde la URL usando Glide
            Glide.with(itemView.context)
                .load(article.urlToImage)         // URL de la imagen
                .placeholder(R.drawable.placeholder_image)  // Imagen de carga
                .error(R.drawable.error_image)             // Imagen en caso de error
                .into(ivNewsImage)

            // Configurar el click para abrir NewsInfo
            itemView.setOnClickListener {
                val intent = Intent(itemView.context, NewsInfo::class.java).apply {
                    putExtra("title", article.title)
                    putExtra("description", article.description)
                    putExtra("urlToImage", article.urlToImage)
                    putExtra("sourceUrl", article.url)
                    putExtra("content", article.content)
                }
                itemView.context.startActivity(intent)
            }
        }
    }
}


