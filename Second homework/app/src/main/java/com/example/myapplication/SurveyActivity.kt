package com.example.myapplication

import android.app.Activity
import android.content.Intent
import android.os.Bundle
import android.os.PersistableBundle
import android.widget.Button
import android.widget.EditText
import androidx.appcompat.app.AppCompatActivity

class SurveyActivity: AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        setContentView(R.layout.activity_survey)

        var surveyName: EditText = findViewById(R.id.editTextSurveyName)
        var surveySurname: EditText = findViewById(R.id.editTextSurveySurname)
        var surveyColor: EditText = findViewById(R.id.editTextSurveyColor)

        findViewById<Button>(R.id.buttonSurvey).setOnClickListener {
            var info: String? =
                "${surveyName.text.toString()}, ${surveySurname.text.toString()}, ${surveyColor.text.toString()}"
            println("-----------------------------------------$info")

            setResult(RESULT_OK, Intent().apply {
                putExtra("surveyInfo", info.toString())
                }
                )
            finish()

//            val intent = Intent()
//            intent.putExtra("surveyInfo", info.toString())
//            setResult(RESULT_OK, intent)
//            finish()

        }
    }

}