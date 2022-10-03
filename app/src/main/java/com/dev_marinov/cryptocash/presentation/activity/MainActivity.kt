package com.dev_marinov.cryptocash.presentation.activity

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import com.dev_marinov.cryptocash.R
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class MainActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        Log.d("MainActivity", "333 загрузился")
        setContentView(R.layout.activity_main)
    }






}