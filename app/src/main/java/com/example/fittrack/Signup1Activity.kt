package com.example.fittrack

import android.content.Intent
import android.os.Bundle
import androidx.activity.enableEdgeToEdge
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.ViewCompat
import androidx.core.view.WindowInsetsCompat
import com.example.fittrack.databinding.ActivitySignup1Binding
import com.example.fittrack.databinding.ActivitySignupBinding

class Signup1Activity : AppCompatActivity() {
    private lateinit var binding: ActivitySignup1Binding
    override fun onCreate(savedInstanceState: Bundle?) {
        binding = ActivitySignup1Binding.inflate(layoutInflater)
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContentView(binding.root)
        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main)) { v, insets ->
            val systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars())
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom)
            insets
        }

        val email = intent.getStringExtra("email")
        val password = intent.getStringExtra("password")

        binding.createSignUpButton.setOnClickListener {
            val name = binding.signUpUserName.text.toString()
            val gender = binding.signUpUserGender.text.toString()
            val age = binding.signUpUserAge.text.toString()
            val height = binding.signUpUserHeight.text.toString()
            val weight = binding.signUpUserWeight.text.toString()

            if (name.isNotEmpty() && gender.isNotEmpty() && age.isNotEmpty() && height.isNotEmpty() && weight.isNotEmpty()){
                val intent = Intent(this, MainActivity::class.java).apply {
                    putExtra("name", name)
                    putExtra("gender", gender)
                    putExtra("age", age)
                    putExtra("height", height)
                    putExtra("weight", weight)
                    putExtra("email", email)
                    putExtra("password", password)
                }
                startActivity(intent)
                finish()
            }
            else{
                if (name.isEmpty()){
                    binding.signUpUserName.error = "Please enter your name"
                }
                if (gender.isEmpty()){
                    binding.signUpUserGender.error = "Please enter your gender"
                }
                if (age.isEmpty()){
                    binding.signUpUserAge.error = "Please enter your age"
                }
                if (height.isEmpty()){
                    binding.signUpUserHeight.error = "Please enter your height"
                }
                if (weight.isEmpty()){
                    binding.signUpUserWeight.error = "Please enter your weight"
                }
            }
        }
    }
}