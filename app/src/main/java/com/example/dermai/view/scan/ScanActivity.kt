package com.example.dermai.view.scan

import android.content.Intent
import android.os.Bundle
import android.provider.MediaStore
import android.widget.Toast
import androidx.activity.enableEdgeToEdge
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.ViewCompat
import androidx.core.view.WindowInsetsCompat
import com.example.dermai.R
import com.example.dermai.databinding.ActivityScanBinding

class ScanActivity : AppCompatActivity() {
    private lateinit var binding: ActivityScanBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityScanBinding.inflate(layoutInflater)
        setContentView(binding.root)

        // Tambahkan listener untuk gallery_scan
        binding.galeryScan.setOnClickListener {
            // Implementasi aksi gallery_scan
            Toast.makeText(this, "Galeri scan diklik", Toast.LENGTH_SHORT).show()
            // Contoh: Membuka galeri
            val intent = Intent(Intent.ACTION_PICK)
            intent.type = "image/*"
            startActivityForResult(intent, 1)
        }

        // Tambahkan listener untuk camera_scan
        binding.cameraScan.setOnClickListener {
            // Implementasi aksi camera_scan
            Toast.makeText(this, "Kamera scan diklik", Toast.LENGTH_SHORT).show()
            // Contoh: Membuka kamera
            val intent = Intent(MediaStore.ACTION_IMAGE_CAPTURE)
            startActivityForResult(intent, 2)
        }

        // Tambahkan listener untuk back
        binding.back.setOnClickListener {
            // Implementasi aksi back
            Toast.makeText(this, "Tombol kembali diklik", Toast.LENGTH_SHORT).show()
            // Contoh: Menyelesaikan aktivitas ini dan kembali ke aktivitas sebelumnya
            finish()
        }
    }

    override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {
        super.onActivityResult(requestCode, resultCode, data)
        if (resultCode == RESULT_OK) {
            when (requestCode) {
                1 -> {
                    // Handle image picked from gallery
                    val selectedImageUri = data?.data
                    Toast.makeText(this, "Gambar dari galeri dipilih: $selectedImageUri", Toast.LENGTH_SHORT).show()
                }
                2 -> {
                    // Handle image captured from camera
                    val capturedImage = data?.extras?.get("data")
                    Toast.makeText(this, "Gambar dari kamera diambil", Toast.LENGTH_SHORT).show()
                }
            }
        }
    }
}