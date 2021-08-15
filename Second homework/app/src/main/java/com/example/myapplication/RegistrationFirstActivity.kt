package com.example.myapplication

import android.content.Intent
import android.os.Bundle
import android.os.PersistableBundle
import android.widget.Button
import android.widget.EditText
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity

class RegistrationFirstActivity: AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_registration_first)

        var userName: EditText = findViewById(R.id.editTextRegistrationFirst)

        findViewById<Button>(R.id.buttonRegistrationFirst).setOnClickListener {
            if (userName.text.toString() == ""){
                Toast.makeText(this,"Add your name", Toast.LENGTH_SHORT).show()
            }
            else{
                val intent = Intent(this, RegistrationSecondActivity::class.java)
                intent.putExtra("name",userName.text.toString())
                startActivity(intent)
            }


        }
    }


}