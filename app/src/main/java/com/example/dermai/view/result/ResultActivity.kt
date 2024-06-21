package com.example.dermai.view.result

import android.content.Intent
import android.graphics.Bitmap
import android.net.Uri
import android.os.Bundle
import android.widget.ImageView
import androidx.appcompat.app.AppCompatActivity
import com.example.dermai.R
import com.example.dermai.databinding.ActivityResultBinding
import com.example.dermai.view.analyze.HasilAnalyzeActivity

class ResultActivity : AppCompatActivity() {
    private lateinit var imageView: ImageView
    private lateinit var binding: ActivityResultBinding
    private var imageBitmap: Bitmap? = null

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityResultBinding.inflate(layoutInflater)
        setContentView(binding.root)

        binding.backButton.setOnClickListener{
            finish()
        }

        imageView = findViewById(R.id.imageView)

        // Menerima data gambar dari ScanActivity
        imageBitmap = intent.getParcelableExtra("image_bitmap")
        val imageUriString = intent.getStringExtra("image_uri")
        val imageUri = imageUriString?.let { Uri.parse(it) }

        if (imageUri != null) {
            imageView.setImageURI(imageUri)
        } else if (imageBitmap != null) {
            imageView.setImageBitmap(imageBitmap)
        }

        binding.analyzeLayout.setOnClickListener {
            val intent = Intent(this, HasilAnalyzeActivity::class.java)

            // Pilih salah satu, berdasarkan cara Anda mendapatkan gambar
            imageBitmap?.let {
                intent.putExtra("image_bitmap", it)
            }
            imageUri?.let {
                intent.putExtra("image_uri", it.toString())
            }

            startActivity(intent)
        }
    }
}
