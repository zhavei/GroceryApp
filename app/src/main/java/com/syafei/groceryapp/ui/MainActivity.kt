package com.syafei.groceryapp.ui

import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import com.syafei.groceryapp.databinding.ActivityMainBinding

class MainActivity : AppCompatActivity() {

    private val binding by lazy {
        ActivityMainBinding.inflate(layoutInflater)
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(binding.root)
    }
}