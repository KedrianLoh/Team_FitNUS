package com.example.recyclerviewexample

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.Button
import android.widget.EditText
import android.widget.TextView
import android.widget.Toast
import com.example.recyclerviewexample.R
import com.google.firebase.auth.FirebaseAuth

class MainActivity2 : AppCompatActivity() {
    lateinit var emailinput: EditText
    private lateinit var pwdinput: EditText
    private lateinit var auth: FirebaseAuth
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main2)
        val loginbtn = findViewById<Button>(R.id.login_button)
        emailinput = findViewById(R.id.editTextTextEmailAddress)
        pwdinput = findViewById(R.id.editTextTextPassword)
        val regbtn = findViewById<TextView>(R.id.registration_button)
        auth = FirebaseAuth.getInstance()
        loginbtn.setOnClickListener {
            if (emailinput.text.isEmpty() || pwdinput.text.isEmpty()) {
                Toast.makeText(this, "Invalid Email Address or Password", Toast.LENGTH_SHORT).show()
            } else {
                val mail = emailinput.text.toString()
                val pass = pwdinput.text.toString()
                auth.signInWithEmailAndPassword(mail, pass).addOnCompleteListener(this) {
                    if (it.isSuccessful) {
                        Toast.makeText(this, "Login Successful!", Toast.LENGTH_SHORT).show()
                        val intent = Intent(this, HomePageActivity::class.java)
                        startActivity(intent)
                    } else {
                        Toast.makeText(
                            this,
                            "Invalid Email Address or Password",
                            Toast.LENGTH_SHORT
                        ).show()
                    }
                }
            }
        }
        regbtn.setOnClickListener {
            val intent = Intent(this, Registration::class.java)
            startActivity(intent)
        }
    }
    override fun onBackPressed() {
//        super.onBackPressed()
    }
}