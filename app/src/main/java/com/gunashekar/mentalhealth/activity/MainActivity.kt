package com.gunashekar.mentalhealth.activity

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import com.gunashekar.mentalhealth.R
import kotlinx.android.synthetic.main.activity_main.*

class MainActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        val role = intent.getStringExtra("role")
        temp.text = role
    }
}