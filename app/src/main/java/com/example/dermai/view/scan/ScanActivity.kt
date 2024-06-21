package com.example.dermai.view.scan

import android.Manifest
import android.content.Intent
import android.content.pm.PackageManager
import android.graphics.Bitmap
import android.net.Uri
import android.os.Bundle
import android.provider.MediaStore
import android.widget.Toast
import androidx.activity.result.contract.ActivityResultContracts
import androidx.appcompat.app.AppCompatActivity
import androidx.core.app.ActivityCompat
import androidx.core.content.ContextCompat
import com.example.dermai.databinding.ActivityScanBinding
import com.example.dermai.view.result.ResultActivity

class ScanActivity : AppCompatActivity() {

    private lateinit var binding: ActivityScanBinding

    private val cameraActivityResultLauncher = registerForActivityResult(ActivityResultContracts.StartActivityForResult()) { result ->
        if (result.resultCode == RESULT_OK) {
            val capturedImage = result.data?.extras?.get("data") as Bitmap?
            Toast.makeText(this, "Gambar dari kamera diambil", Toast.LENGTH_SHORT).show()
            val intent = Intent(this, ResultActivity::class.java)
            intent.putExtra("image_bitmap", capturedImage)
            startActivity(intent)
        }
    }

    private val galleryActivityResultLauncher = registerForActivityResult(ActivityResultContracts.StartActivityForResult()) { result ->
        if (result.resultCode == RESULT_OK) {
            val selectedImageUri = result.data?.data
            Toast.makeText(this, "Gambar dari galeri dipilih: $selectedImageUri", Toast.LENGTH_SHORT).show()
            val intent = Intent(this, ResultActivity::class.java)
            intent.putExtra("image_uri", selectedImageUri.toString())
            startActivity(intent)
        }
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityScanBinding.inflate(layoutInflater)
        setContentView(binding.root)

        binding.backButton.setOnClickListener {
            finish()
        }

        // Tambahkan listener untuk gallery_scan
        binding.galeryScan.setOnClickListener {
            // Implementasi aksi gallery_scan
            Toast.makeText(this, "Galeri scan diklik", Toast.LENGTH_SHORT).show()
            // Contoh: Membuka galeri
            val intent = Intent(Intent.ACTION_PICK)
            intent.type = "image/*"
            galleryActivityResultLauncher.launch(intent)
        }

        // Tambahkan listener untuk camera_scan
        binding.cameraScan.setOnClickListener {
            // Implementasi aksi camera_scan
            if (ContextCompat.checkSelfPermission(this, Manifest.permission.CAMERA)
                != PackageManager.PERMISSION_GRANTED) {
                ActivityCompat.requestPermissions(this, arrayOf(Manifest.permission.CAMERA), CAMERA_PERMISSION_REQUEST_CODE)
            } else {
                val intent = Intent(MediaStore.ACTION_IMAGE_CAPTURE)
                cameraActivityResultLauncher.launch(intent)
            }
        }

        // Tambahkan listener untuk back
        binding.back.setOnClickListener {
            // Implementasi aksi back
            Toast.makeText(this, "Tombol kembali diklik", Toast.LENGTH_SHORT).show()
            // Contoh: Menyelesaikan aktivitas ini dan kembali ke aktivitas sebelumnya
            finish()
        }
    }

    override fun onRequestPermissionsResult(requestCode: Int, permissions: Array<out String>, grantResults: IntArray) {
        super.onRequestPermissionsResult(requestCode, permissions, grantResults)
        if (requestCode == CAMERA_PERMISSION_REQUEST_CODE) {
            if ((grantResults.isNotEmpty() && grantResults[0] == PackageManager.PERMISSION_GRANTED)) {
                val intent = Intent(MediaStore.ACTION_IMAGE_CAPTURE)
                cameraActivityResultLauncher.launch(intent)
            } else {
                Toast.makeText(this, "Izin kamera diperlukan untuk menggunakan fitur ini", Toast.LENGTH_SHORT).show()
            }
        }
    }

    companion object {
        private const val CAMERA_PERMISSION_REQUEST_CODE = 100
    }
}
