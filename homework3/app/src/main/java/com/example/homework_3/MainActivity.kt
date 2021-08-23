package com.example.homework_3

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.Button
import android.widget.Toast
import kotlin.random.Random

class MainActivity : AppCompatActivity() {
    val getContent = registerForActivityResult(SecondActivity.Contract()) {
        if(it != null){
            println("------------------------------- $it")
            Toast.makeText(this, it, Toast.LENGTH_SHORT).show()
        }
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        findViewById<Button>(R.id.buttonMainActivity).setOnClickListener {
            getContent.launch(createSet(10).toIntArray())
        }
    }

    fun createSet(size : Int): MutableSet<Int> {
        var randomSet: MutableSet<Int>  = mutableSetOf()
        for (i in 0..size){
            randomSet.add(Random.nextInt(0, 100))
        }
        print("---------------------1--$randomSet")
        return randomSet
    }

//    fun randomSize(): Int{
//        var num = Random.nextInt(0,20)
//        while (num%2 != 0){
//            num = Random.nextInt(0,20)
//        }
//        return num
//    }
}