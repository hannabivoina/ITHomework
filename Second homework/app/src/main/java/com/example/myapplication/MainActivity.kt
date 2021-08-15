package com.example.myapplication

import android.app.Activity
import android.content.Context
import android.content.Intent
import android.content.res.Configuration
import android.content.res.Resources
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.Button
import android.widget.Toast
import java.util.*
import android.util.DisplayMetrics
import java.security.AccessController.getContext
import android.content.SharedPreferences





const val REQUEST_CODE_MAIN = 1

var language: String = "en"

class MainActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {


        super.onCreate(savedInstanceState)
        loadLocate()
        setContentView(R.layout.activity_main)



        findViewById<Button>(R.id.buttonMainToRegistration).setOnClickListener {
            startActivity(
                Intent(
                    this,
                    RegistrationFirstActivity::class.java
                )
            )
        }

        findViewById<Button>(R.id.buttonMainToSurvey).setOnClickListener {
            startActivityForResult(
                Intent(
                    this,
                    SurveyActivity::class.java
                ),
                REQUEST_CODE_MAIN
            )
        }

        findViewById<Button>(R.id.buttonMainChangeLang).setOnClickListener {
            setLocate(language)

            recreate()
            if (language == ""){
                language = "en"
            }
            else{
                language = ""
            }

            println("------------------------ $language")

            }
    }

    override fun onNewIntent(intent: Intent?) {

        val myIntent = Intent(baseContext, MainActivity::class.java)
        myIntent.flags = Intent.FLAG_ACTIVITY_CLEAR_TASK or Intent.FLAG_ACTIVITY_NEW_TASK
        val userInfo: String? = intent?.getStringExtra("info")
        Toast.makeText(this,userInfo, Toast.LENGTH_LONG).show()
//        startActivity(myIntent)
        super.onNewIntent(myIntent)



        println("---------------------------------------")
    }

    override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {
        super.onActivityResult(requestCode, resultCode, data)

        if (requestCode == REQUEST_CODE_MAIN && resultCode == RESULT_OK){
            val surveyInfo: String? = data?.getStringExtra("surveyInfo")
            println("-----------------------------------------$surveyInfo")
            Toast.makeText(this,surveyInfo,Toast.LENGTH_LONG).show()
        }

    }


    fun setLocate(lang: String){
        val locale = Locale(lang)
        Locale.setDefault(locale)
        val config = Configuration()
        config.locale = locale
        baseContext.resources.updateConfiguration(config,baseContext.resources.displayMetrics)

        val editor = getSharedPreferences("Settings", Context.MODE_PRIVATE).edit()
        editor.putString("My_Lang",lang)
        editor.apply()

    }

    fun loadLocate(){
        val sharedPreferences = getSharedPreferences("Settings", Activity.MODE_PRIVATE)
        val language = sharedPreferences.getString("My_Lang", "")
        setLocate(language!!)
    }

}