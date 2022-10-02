package com.dev_marinov.cryptocash.presentation.activity

import android.app.DatePickerDialog
import android.graphics.drawable.AnimationDrawable
import android.graphics.drawable.ColorDrawable
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import android.view.Gravity
import android.widget.Toast
import androidx.activity.viewModels
import androidx.databinding.DataBindingUtil
import androidx.lifecycle.Lifecycle
import androidx.lifecycle.lifecycleScope
import androidx.lifecycle.repeatOnLifecycle
import com.dev_marinov.cryptocash.R
import com.dev_marinov.cryptocash.databinding.ActivityMainBinding
import com.dev_marinov.cryptocash.presentation.bottomSheet.BottomSheetFragment
import dagger.hilt.android.AndroidEntryPoint
import java.util.*

@AndroidEntryPoint
class MainActivity : AppCompatActivity() {

    lateinit var binding: ActivityMainBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        Log.d("MainActivity", "333 загрузился")
        binding = DataBindingUtil.setContentView(this, R.layout.activity_main)
    }






}