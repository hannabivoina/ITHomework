package com.example.multithread

import android.widget.TextView
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel

class MainViewModel : ViewModel() {
    private var click: Int = 0
    private var status: Int = 0
    private var myPosition: Int = 0
    private var myList: MutableList<Int> = mutableListOf(2)
    private var res: Int = 0
    private var sum: Int = 0

    var _historyLiveData = ArrayList<String>()
    val historyLiveData = MutableLiveData<ArrayList<String>>()

    private var _countLiveData = MutableLiveData<Int>()
    val countLiveData: LiveData<Int>
        get() = _countLiveData

    fun getRes(): Int {
        return res
    }

    fun getSum(): Int {
        return sum
    }

    fun isPrime(num: Int): Boolean {
        for (i in 3..(Math.sqrt(num.toDouble()).toInt())) {
            if (num % i == 0) {
                return false
            }
        }
        return true
    }

    fun countNum() {
        var thread = Thread {
            var i = 3
            while (status != 2) {
                if (isPrime(i)) {
                    myList.add(i)
                }
                i = i + 2
            }
        }
        thread.start()
    }

    fun changeNum(position: Int, sec: Int = 1) {
        val interval = (sec * 1000).toLong()
        var num = position
        var thread = Thread {
            while (status == 0) {
                num++
                res = myList.get(num)
                _countLiveData.postValue(myList.get(num))
                Thread.sleep(interval)
            }
            myPosition = num
        }
        thread.start()
    }

    fun startCount(sec: Int = 1) {
        status = 0
        if (status == 0 && click == 0) {
            countNum()
            myPosition = -1
        }
        changeNum(myPosition, sec)
    }

    fun pauseCount() {
        status = 1
        click = 1
    }

    fun stopCount() {
        status = 2
        click = 0
        myList = mutableListOf(2)
        sum = sumCount(res)
        var hist = "$res, сумма $sum"
        _historyLiveData.add(hist)
        historyLiveData.value = _historyLiveData
    }

    fun sumCount(num: Int): Int {
        var newSum = 0
        for (i in num downTo 0) {
            newSum = newSum + i
        }
        return newSum
    }
}