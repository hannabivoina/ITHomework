package com.example.multithread

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.Button
import android.widget.EditText
import android.widget.TextView
import android.widget.Toast
import androidx.activity.viewModels
import androidx.appcompat.app.AlertDialog
import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import com.example.multithread.databinding.ActivityMainBinding
import java.lang.Math.sqrt
import kotlin.concurrent.thread


class MainActivity : AppCompatActivity() {

    private lateinit var binding: ActivityMainBinding
    private val viewModel = viewModels<MainViewModel>()

        override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)

        val buttonStart = binding.buttonStart
        val buttonStop = binding.buttonStop
        val buttonPause = binding.buttonPause
        val buttonHistory = binding.buttonHistory
        val interval = binding.editInterval
        val intervalString = myIntervals()
        val countStatus = binding.textCountStatus
//        buttonPause.isEnabled = false
//        buttonStop.isEnabled = false

        viewModel.value.countLiveData.observe(this) {
            buttonStart.isEnabled = false
            binding.textMain.setText(it.toString())
        }

        buttonStart.setOnClickListener {
            if (intervalString.contains(interval.text.toString())) {
                countStatus.setText("Счетчик работает")
                buttonStart.isEnabled = false
                buttonPause.isEnabled = true
                buttonStop.isEnabled = true
                println("---------------${viewModel.value.countLiveData.value}")

                viewModel.value.startCount(interval.text.toString().toInt())

            } else {
                Toast.makeText(this, "Введите число от 1 до 30", Toast.LENGTH_LONG).show()
            }
        }

        buttonStop.setOnClickListener {
            buttonStart.isEnabled = true
            buttonPause.isEnabled = true
            countStatus.setText("Счетчик остановлен")

            viewModel.value.stopCount()

            var mainText = binding.textMain

            val dialog = AlertDialog.Builder(this)
                .setMessage(
                    "Текущее значение - ${viewModel.value.getRes()}, сумма - ${viewModel.value.getSum()}"
                )
                .setNegativeButton("CANCEL") { dialog, which -> dialog.dismiss() }
                .create()
            dialog.show()

            println("------------size-----${viewModel.value.historyLiveData.value?.size}")
        }

        buttonPause.setOnClickListener {
            buttonStart.isEnabled = true
            buttonPause.isEnabled = false
            viewModel.value.pauseCount()
            countStatus.setText("Счетчик приостановлен")
        }

        buttonHistory.setOnClickListener {
            var arr = viewModel.value.historyLiveData.value
            if (arr != null){
            val intent = Intent(this, HistoryActivity::class.java)
            intent.putStringArrayListExtra("key", arr)
            startActivity(intent)
            }
            else{
                Toast.makeText(this, "Сначала нужно хотя бы раз запустить счетчик", Toast.LENGTH_LONG).show()
            }
        }

    }

    private fun myIntervals(): List<String> {
        var list = mutableListOf<String>()
        for (i in 1..30) {
            list.add("$i")
        }
        return list.toList()
    }
}