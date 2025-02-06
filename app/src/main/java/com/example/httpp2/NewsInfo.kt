package com.example.httpp2

import android.content.Intent
import android.net.Uri
import android.os.Bundle
import android.widget.Button
import android.widget.ImageView
import android.widget.TextView
import androidx.activity.enableEdgeToEdge
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.ViewCompat
import androidx.core.view.WindowInsetsCompat
import com.bumptech.glide.Glide

class NewsInfo : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContentView(R.layout.activity_news_info)

        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main)) { v, insets ->
            val systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars())
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom)
            insets
        }

        // Referencias a las vistas
        val ivNewsImage: ImageView = findViewById(R.id.ivNewsImage)
        val tvTitle: TextView = findViewById(R.id.tvTitle)
        val tvDescription: TextView = findViewById(R.id.tvDescription)
        val btnSource: Button = findViewById(R.id.btnSource)
        val btnBack: Button = findViewById(R.id.btnBack)

        // Obtener datos del intent
        val title = intent.getStringExtra("title")
        val description = intent.getStringExtra("description")
        val urlToImage = intent.getStringExtra("urlToImage")
        val sourceUrl = intent.getStringExtra("sourceUrl")

        // Mostrar los datos
        tvTitle.text = title
        tvDescription.text = description

        Glide.with(this)
            .load(urlToImage)
            .placeholder(R.drawable.placeholder_image)
            .error(R.drawable.error_image)
            .into(ivNewsImage)

        // Configurar el botón para abrir la fuente de la noticia
        btnSource.setOnClickListener {
            val intent = Intent(Intent.ACTION_VIEW, Uri.parse(sourceUrl))
            startActivity(intent)
        }

        // Configurar el botón para volver al buscador
        btnBack.setOnClickListener {
            finish() // Finalizar la actividad para volver a la anterior
        }
    }
}
