package com.example.multithread

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.Button
import android.widget.EditText
import android.widget.TextView
import android.widget.Toast
import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import java.lang.Math.sqrt
import kotlin.concurrent.thread


class MainActivity : AppCompatActivity() {

    lateinit var mViewModel: MainViewModel

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        mViewModel = ViewModelProvider(this).get(MainViewModel::class.java)

        val buttonStart = findViewById<Button>(R.id.buttonStart)
        val buttonStop = findViewById<Button>(R.id.buttonStop)
        val buttonPause = findViewById<Button>(R.id.buttonPause)
        val buttonHistory = findViewById<Button>(R.id.buttonHistory)
        val interval = findViewById<EditText>(R.id.editInterval)
        val intervalString = myIntervals()
        val countStatus = findViewById<TextView>(R.id.textCountStatus)

        buttonStart.setOnClickListener {
            if(intervalString.contains(interval.text.toString())) {
                countStatus.setText("Счетчик работает")
                buttonStart.isEnabled = false
                buttonPause.isEnabled = true
                println("---------------${mViewModel.countLiveData.value}")

                mViewModel.startCount(interval.text.toString().toInt())
                mViewModel.countLiveData.observe(this){
                    findViewById<TextView>(R.id.textMain).setText(it.toString())
                }
            }
            else{
                Toast.makeText(this,"Введите число от 1 до 30",Toast.LENGTH_LONG).show()
            }
        }

        buttonStop.setOnClickListener {
            buttonStart.isEnabled = true
            buttonPause.isEnabled = true
            countStatus.setText("Счетчик остановлен")

            mViewModel.stopCount()

//            Toast.makeText(this, "${sumCount(findViewById<TextView>(R.id.textMain).text.toString().toInt())}",Toast.LENGTH_LONG).show()
        }
        buttonPause.setOnClickListener {
            buttonStart.isEnabled = true
            buttonPause.isEnabled = false
            mViewModel.pauseCount()
            countStatus.setText("Счетчик приостановлен")
        }
    }

    private fun myIntervals():List<String>{
        var list = mutableListOf<String>()
        for (i in 1..30){
            list.add("$i")
        }
        return list.toList()
    }

    private fun sumCount(count: Int):Int{
        var sum = 0
        for (i in count downTo 1){
            sum += i
        }
        return sum
    }
}