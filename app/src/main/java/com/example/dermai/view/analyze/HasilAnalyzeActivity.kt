package com.example.dermai.view.analyze

import android.content.Context
import android.graphics.Bitmap
import android.net.Uri
import android.os.Bundle
import android.provider.MediaStore
import android.widget.ImageView
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import com.example.dermai.R
import com.example.dermai.databinding.ActivityHasilAnalyzeBinding
import org.tensorflow.lite.task.vision.classifier.Classifications
import java.text.NumberFormat

class HasilAnalyzeActivity : AppCompatActivity() {
    private lateinit var imageView: ImageView
    private lateinit var binding: ActivityHasilAnalyzeBinding
    private lateinit var imageClassifierHelper: ImageClassifierHelper

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityHasilAnalyzeBinding.inflate(layoutInflater)
        setContentView(binding.root)

        binding.backButton.setOnClickListener{
            finish()
        }

        imageView = findViewById(R.id.imageView)

        // Menerima data gambar dari ScanActivity
        val imageUri = intent.getStringExtra("image_uri")
        val imageBitmap = intent.getParcelableExtra<Bitmap>("image_bitmap")

        if (imageUri != null) {
            imageView.setImageURI(Uri.parse(imageUri))
            resultAnalyze(Uri.parse(imageUri))
        } else if (imageBitmap != null) {
            imageView.setImageBitmap(imageBitmap)
            // Simpan bitmap ke uri sementara untuk analisis
            val tempUri = getImageUri(this, imageBitmap)
            resultAnalyze(tempUri)
        }
    }

    private fun resultAnalyze(imageUri: Uri){
        imageClassifierHelper = ImageClassifierHelper(
            context = this,
            classifierListener = object : ImageClassifierHelper.ClassifierListener{
                override fun onError(error: String) {
                    runOnUiThread {
                        Toast.makeText(this@HasilAnalyzeActivity,error, Toast.LENGTH_SHORT).show()
                    }
                }
                override fun onResults(results: List<Classifications>?, inferenceTime: Long) {
                    runOnUiThread {
                        results?.let{ classifications  ->
                            if (classifications.isNotEmpty() && classifications[0].categories.isNotEmpty()){
                                val sortedCategories =
                                    classifications[0].categories.sortedByDescending { it?.score ?: 0.0f }
                                val highestScoreCategories = sortedCategories.maxByOrNull { it?.score ?: 0.0f }
                                highestScoreCategories?.let {category ->
                                    val displayResult = "${category.label} ${NumberFormat.getPercentInstance().format(category.score).trim()}"
                                    binding.resultText.text = displayResult
                                } ?: run {
                                    binding.resultText.text = ""
                                }
                            } else {
                                binding.resultText.text = ""
                            }
                        }
                    }
                }
            }
        )
        imageClassifierHelper.classifyStaticImage(imageUri)
    }

    private fun getImageUri(context: Context, bitmap: Bitmap): Uri {
        val path = MediaStore.Images.Media.insertImage(context.contentResolver, bitmap, "TempImage", null)
        return Uri.parse(path)
    }
}
