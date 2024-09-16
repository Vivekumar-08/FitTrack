package com.example.fittrack

import android.os.Bundle
import android.view.View
import androidx.activity.enableEdgeToEdge
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.ViewCompat
import androidx.core.view.WindowInsetsCompat
import com.example.fittrack.databinding.ActivityBmiBinding

class BMI_Activity : AppCompatActivity() {
    private lateinit var binding: ActivityBmiBinding
    override fun onCreate(savedInstanceState: Bundle?) {
        binding = ActivityBmiBinding.inflate(layoutInflater)
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContentView(binding.root)
        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main)) { v, insets ->
            val systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars())
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom)
            insets
        }
        binding.buttonBMICalculate.setOnClickListener {
            val height = (binding.userHeight.text.toString()).toFloat()
            val weight = (binding.userWeight.text.toString()).toFloat()

            val result = weight/(height*height)
            binding.textViewResult.visibility = View.VISIBLE
            binding.textViewResult.text = "Your BMI is : $result"
        }
    }
}