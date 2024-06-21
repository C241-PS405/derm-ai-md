package com.example.dermai.view.main

import android.content.Intent
import android.os.Build
import android.os.Bundle
import android.view.Menu
import android.view.MenuItem
import android.view.View
import android.view.WindowInsets
import android.view.WindowManager
import android.widget.Toast
import androidx.activity.enableEdgeToEdge
import androidx.activity.viewModels
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.ViewCompat
import androidx.core.view.WindowInsetsCompat
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.dermai.R
import com.example.dermai.data.adapter.RecomendationAdapter
import com.example.dermai.data.pref.RecomendationModel
import com.example.dermai.databinding.ActivityMainBinding
import com.example.dermai.view.ViewModelFactory
import com.example.dermai.view.scan.ScanActivity
import com.example.dermai.view.welcome.WelcomeActivity

class MainActivity : AppCompatActivity() {
    private val viewModel by viewModels<MainViewModel> {
        ViewModelFactory.getInstance(this)
    }
    private lateinit var binding: ActivityMainBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)

        viewModel.getSession().observe(this) { user ->
            if (!user.isLogin) {
                startActivity(Intent(this, WelcomeActivity::class.java))
                finish()
            } else {

            }
        }
        setupView()
        supportActionBar?.show()

        // Data dummy untuk rekomendasi
        val recommendations = listOf(
            RecomendationModel("Acne Skin", "Description 1", R.drawable.test1),
            RecomendationModel("Sensitif Skin", "Description 2", R.drawable.sensitif),
            RecomendationModel("Oily Skin", "Description 3", R.drawable.oily)
        )

        // Inisialisasi RecyclerView
        binding.recommendationsRecyclerview.layoutManager = LinearLayoutManager(this)
        binding.recommendationsRecyclerview.adapter = RecomendationAdapter(recommendations)

        binding.scanButton.setOnClickListener {
            val intent = Intent(this, ScanActivity::class.java)
            startActivity(intent)
        }

        binding.logoutButton.setOnClickListener {
            viewModel.logout()
        }
    }

    private fun setupView() {
        @Suppress("DEPRECATION")
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.R) {
            window.insetsController?.hide(WindowInsets.Type.statusBars())
        } else {
            window.setFlags(
                WindowManager.LayoutParams.FLAG_FULLSCREEN,
                WindowManager.LayoutParams.FLAG_FULLSCREEN
            )
        }
    }

//    override fun onOptionsItemSelected(item: MenuItem): Boolean {
//
//        when (item.itemId) {
//            R.id.btn_logout -> {
//                viewModel.logout()
//            }
//        }
//        return super.onOptionsItemSelected(item)
//    }
//
//    override fun onCreateOptionsMenu(menu: Menu?): Boolean {
//        menuInflater.inflate(R.menu.option_menu, menu)
//        return true
//    }
}