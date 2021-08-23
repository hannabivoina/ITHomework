package com.example.homework_3

import android.content.Context
import android.content.Intent
import android.os.Bundle
import android.os.PersistableBundle
import android.widget.Button
import androidx.activity.result.contract.ActivityResultContract
import androidx.appcompat.app.AppCompatActivity
import java.lang.reflect.Array.get

private const val INPUT_CONST = "EXTRA_MESSAGE"
private const val OUTPUT_CONST = "EXTRA_MESSAGE"

class SecondActivity: AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_second)

        var myArray = intent.getIntArrayExtra(INPUT_CONST)
        println("-------.......................................----------- ${myArray?.get(1)}")

        findViewById<Button>(R.id.buttonSecond).setOnClickListener {
            val res = "sum = ${arraySum(myArray!!)}, mean = ${arrayMean(myArray)}, division = ${arrayDivision(myArray)}"
            setResult(RESULT_OK, Intent().apply {
                putExtra(OUTPUT_CONST, res)
            }
            )
            finish()
        }
    }

    class Contract : ActivityResultContract<IntArray, String>() {
        override fun createIntent(context: Context, input: IntArray) : Intent {
            val intent = Intent(context, SecondActivity::class.java)
            println(" ---------------------------1- ${input[1]}")
            intent.putExtra(INPUT_CONST, input)
            println(" ---------------------------2- ${input.size}")
            var const = intent.getIntArrayExtra(INPUT_CONST)
            println("----------------3--$const")
            return intent
        }

        override fun parseResult(resultCode: Int, intent: Intent?): String? {
            if(intent == null) return null
            var myResult = intent.getStringExtra(OUTPUT_CONST)
            return myResult
        }
    }

    fun arraySum(array: IntArray): Int{
        return array.sum()
    }

    fun arrayMean(array: IntArray): Double{
        return array.average()
    }

    fun arrayDivision(array: IntArray): Double{
        var array1 : MutableList<Int> = mutableListOf()
        var array2 : MutableList<Int> = mutableListOf()

        for (i in 0..(array.size/2 - 1)){
            array1.add(array[i])
        }
        for (i in (array.size/2)..(array.size - 1)){
            array2.add(array[i])
        }

        array1.toIntArray()
        array2.toIntArray()
        var array1sum = array1.sum().toDouble()
        var array2sum = array2.sum().toDouble()
        var a = array1sum/array2sum
        println(a)
        println("------------------------$array1sum")
        println("------------------------$array2sum")

        return a
    }

}