package com.example.recyclerviewexample

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.Button
import android.widget.EditText
import android.widget.Toast
import com.google.firebase.auth.FirebaseAuth

class Registration : AppCompatActivity() {
    lateinit var email: EditText
    private lateinit var pwd: EditText
    private lateinit var auth: FirebaseAuth
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_registration)
        val back = findViewById<Button>(R.id.back_button)
        val register = findViewById<Button>(R.id.register_button)
        var fullname = findViewById<EditText>(R.id.editTextTextPersonName)
        email = findViewById(R.id.editTextTextEmailAddress2)
        pwd = findViewById(R.id.editTextTextPassword2)
        var confirmpwd = findViewById<EditText>(R.id.editTextTextPassword3)
        auth = FirebaseAuth.getInstance()
        back.setOnClickListener {
            val intent = Intent(this, MainActivity2::class.java)
            startActivity(intent)
        }
        register.setOnClickListener {
            if (fullname.text.isEmpty() || email.text.isEmpty() || pwd.text.isEmpty() || confirmpwd.text.isEmpty()) {
                Toast.makeText(this, "Invalid Credentials Entered!", Toast.LENGTH_SHORT).show()
            } else {
                if (pwd.text.toString() != confirmpwd.text.toString()) {
                    Toast.makeText(this, "Passwords do not match!", Toast.LENGTH_SHORT).show()
                } else if (pwd.text.toString().count() < 6) {
                    Toast.makeText(
                        this,
                        "Passwords must be at least 6 characters long!",
                        Toast.LENGTH_SHORT
                    ).show()
                } else {
                    val mail = email.text.toString()
                    val pass = pwd.text.toString()
                    auth.createUserWithEmailAndPassword(mail, pass).addOnCompleteListener(this) {
                        if (it.isSuccessful) {
                            Toast.makeText(this, "Registration Successful!", Toast.LENGTH_SHORT)
                                .show()
                            finish()
                        } else {
                            Toast.makeText(this, "Email Taken!", Toast.LENGTH_SHORT).show()
                        }
                    }
                }
            }
        }
    }
    override fun onBackPressed() {
//        super.onBackPressed()
    }
}

