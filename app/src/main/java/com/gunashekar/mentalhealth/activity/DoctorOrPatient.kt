package com.gunashekar.mentalhealth.activity

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import com.gunashekar.mentalhealth.R
import kotlinx.android.synthetic.main.activity_doctor_or_patient.*

class DoctorOrPatient : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_doctor_or_patient)

        doctor.setOnClickListener {
            val intent = Intent(this, SignUpActivity::class.java)
            intent.putExtra("role", "Doctor")
            startActivity(intent)
        }

        patient.setOnClickListener {
            val intent = Intent(this, SignUpActivity::class.java)
            intent.putExtra("role", "Patient")
            startActivity(intent)
        }
    }
}