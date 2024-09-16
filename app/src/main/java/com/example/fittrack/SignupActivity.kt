package com.example.fittrack

import android.content.Intent
import android.os.Bundle
import android.os.Handler
import android.view.View
import android.widget.Toast
import androidx.activity.enableEdgeToEdge
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.ViewCompat
import androidx.core.view.WindowInsetsCompat
import com.example.fittrack.databinding.ActivitySignupBinding
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.auth.FirebaseAuthUserCollisionException

class SignupActivity : AppCompatActivity() {
    private lateinit var binding: ActivitySignupBinding
    private lateinit var auth: FirebaseAuth

    override fun onCreate(savedInstanceState: Bundle?) {
        binding = ActivitySignupBinding.inflate(layoutInflater)
        auth = FirebaseAuth.getInstance()
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContentView(binding.root)
        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main)) { v, insets ->
            val systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars())
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom)
            insets
        }

        binding.createSignUpNextButton.setOnClickListener {
            val email = binding.signUpUserEmail.text.toString()
            val password = binding.signUpUserPassword.text.toString()

            if (email.isNotEmpty() && password.isNotEmpty()) {
                if (password.length < 8) {
                    binding.signUpUserPassword.error = "Password must be at least 8 characters"
                } else {
                    binding.progressBar.visibility = View.VISIBLE
                    auth.createUserWithEmailAndPassword(email, password)
                        .addOnCompleteListener(this) { task ->
                            binding.progressBar.visibility = View.GONE
                            if (task.isSuccessful) {
                                val user = auth.currentUser
                                user?.sendEmailVerification()?.addOnCompleteListener { verificationTask ->
                                    if (verificationTask.isSuccessful) {
                                        Toast.makeText(this, "Verification email sent. Please check your inbox.", Toast.LENGTH_SHORT).show()
                                        // Start checking for email verification
                                        startEmailVerificationCheck(email)
                                    } else {
                                        binding.signUpUserEmail.error = "Failed to send verification email. Please try again."
                                    }
                                }
                            } else {
                                if (task.exception is FirebaseAuthUserCollisionException) {
                                    binding.signUpUserEmail.error = "This email is already registered."
                                } else {
                                    binding.signUpUserEmail.error = "Sign-up failed. Please try again."
                                }
                            }
                        }
                }
            } else {
                if (email.isEmpty()) {
                    binding.signUpUserEmail.error = "Please enter your email"
                }
                if (password.isEmpty()) {
                    binding.signUpUserPassword.error = "Please enter your password"
                }
            }
        }
    }

    private fun startEmailVerificationCheck(email: String) {
        val handler = Handler()
        val runnable = object : Runnable {
            override fun run() {
                val user = auth.currentUser
                user?.reload()?.addOnCompleteListener { task ->
                    if (task.isSuccessful) {
                        if (user.isEmailVerified) {
                            Toast.makeText(this@SignupActivity, "Email verified!", Toast.LENGTH_SHORT).show()
                            // Navigate to the next activity (e.g., MainActivity)
                            val intent = Intent(this@SignupActivity, MainActivity::class.java)
                            startActivity(intent)
                            finish()  
                        } else {
                            // Check again after 5 seconds
                            handler.postDelayed(this, 5000)
                        }
                    }
                }
            }
        }
        handler.post(runnable) // Start the check
    }
}
