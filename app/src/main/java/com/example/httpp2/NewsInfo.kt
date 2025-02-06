package com.example.httpp2

import android.content.Intent
import android.net.Uri
import android.os.Bundle
import android.util.Log
import android.widget.ImageButton
import android.widget.ImageView
import android.widget.TextView
import androidx.activity.enableEdgeToEdge
import androidx.appcompat.app.AppCompatActivity
import androidx.core.content.ContextCompat
import androidx.core.view.ViewCompat
import androidx.core.view.WindowInsetsCompat
import com.bumptech.glide.Glide

class NewsInfo : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        window.navigationBarColor = ContextCompat.getColor(this, R.color.background)

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
        val tvContent: TextView = findViewById(R.id.tvContent)
        val btnBack: ImageButton = findViewById(R.id.btnBack)
        val btnReadMore: TextView = findViewById(R.id.btnReadMore)


        // Obtener datos del intent
        val title = intent.getStringExtra("title")
        val description = intent.getStringExtra("description")
        val content = intent.getStringExtra("content")
        val urlToImage = intent.getStringExtra("urlToImage")
        val sourceUrl = intent.getStringExtra("sourceUrl")

        // Mostrar los datos
        tvTitle.text = title

        // Verificar el contenido y concatenarlo a la descripción
        if (!content.isNullOrBlank()) {
            Log.d("NewsInfo", "Contenido de la noticia: $content")
            tvDescription.text = "$description\n\n$content"
        } else {
            Log.d("NewsInfo", "Contenido no disponible")
            tvDescription.text = description ?: "Descripción no disponible"
            tvContent.text = "Contenido no disponible."
        }

        // Cargar la imagen con Glide
        Glide.with(this)
            .load(urlToImage)
            .placeholder(R.drawable.placeholder_image)
            .error(R.drawable.error_image)
            .into(ivNewsImage)

        // Configurar el clic en el título para abrir la fuente en el navegador
        tvTitle.setOnClickListener {
            sourceUrl?.let {
                val intent = Intent(Intent.ACTION_VIEW, Uri.parse(it))
                startActivity(intent)
            }
        }

        // Configurar el botón para volver
        btnBack.setOnClickListener {
            finish()
        }

        btnReadMore.setOnClickListener {
            sourceUrl?.let {
                val intent = Intent(Intent.ACTION_VIEW, Uri.parse(it))
                startActivity(intent)
            }
        }

    }
}
