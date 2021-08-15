package com.example.myapplication

import android.content.Intent
import android.os.Bundle
import android.os.PersistableBundle
import android.widget.Button
import android.widget.EditText
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity

class RegistrationSecondActivity:AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        setContentView(R.layout.activity_registration_second)

        val userName: String? = intent.getStringExtra("name")

        var userZodiacSign: EditText = findViewById(R.id.editTextRegistrationSecond)

        findViewById<Button>(R.id.buttonRegistrationSecond).setOnClickListener {
            if (userZodiacSign.text.toString() == ""){
                Toast.makeText(this,"Add your Zodiac Sign", Toast.LENGTH_SHORT).show()
            }
            else{
                val intent = Intent(this, MainActivity::class.java)
                val userData: String? = "$userName, ${userZodiacSign.text.toString()}"
                intent.putExtra("info",userData)
                startActivity(intent)
            }
        }
    }




}