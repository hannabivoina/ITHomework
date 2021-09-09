package com.example.multithread

import android.app.Activity
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.ArrayAdapter
import android.widget.ListView
import android.widget.SimpleAdapter
import android.widget.TextView
import androidx.activity.viewModels
import androidx.lifecycle.ViewModelProvider
import com.example.multithread.databinding.ActivityHistoryBinding

class HistoryActivity : AppCompatActivity() {

    private lateinit var binding: ActivityHistoryBinding
    private var viewModel = viewModels<MainViewModel>()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityHistoryBinding.inflate(layoutInflater)
        setContentView(binding.root)
        var historyList = intent.getStringArrayListExtra("key")

        historyList(historyList!!)
    }

    fun historyList(list: ArrayList<String>) {
        val history = list
        val data = history?.map {
            mapOf(
                KEY_RES to it
            )
        }
        val adapter = SimpleAdapter(
            this,
            data,
            R.layout.item,
            arrayOf(KEY_RES),
            intArrayOf(R.id.itemRes)
        )
        binding.historyActivityLay.adapter = adapter
    }

    companion object {
        @JvmStatic
        val KEY_RES = "res"

        @JvmStatic
        val KEY_SUM = "sum"
    }
}