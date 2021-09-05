package com.example.multithread

import android.widget.TextView
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel

class MainViewModel: ViewModel() {
    var click : Int = 0
    var status : Int = 0
    var myPosition : Int = -1
    var myList : MutableList<Int> = mutableListOf(2)

    private var _countLiveData = MutableLiveData<Int>()
    val countLiveData: LiveData<Int>
        get() = _countLiveData


    fun isPrime(num: Int): Boolean {
        for (i in 3..(Math.sqrt(num.toDouble()).toInt())) {
            if (num % i == 0) {
                return false
            }
        }
        return true
    }

    fun countNum() {
        var thread: Thread? = null
        thread = Thread {
            var i = 3
            while (status != 2) {
                if (isPrime(i)) {
                    myList.add(i)
                }
                i = i + 2
            }
            stopThread(thread)
        }
        thread?.start()
    }
    
    fun stopThread(th: Thread?){
        var th = null
    }

    fun changeNum(position: Int, sec: Int = 1) {
        var thread: Thread? = null
        val interval = (sec * 1000).toLong()
        var num = position
        thread = Thread {
            while (status == 0) {
                num++
                _countLiveData.postValue(myList.get(num))
                Thread.sleep(interval)
            }
            myPosition = num
            stopThread(thread)
        }
        thread?.start()
    }

    fun startCount(sec: Int = 1){
        status = 0
        if (status == 0 && click == 0){
            countNum()
            myPosition = -1
        }
        changeNum(myPosition,sec)
    }

    fun pauseCount(){
        status = 1
        click = 1
    }

    fun stopCount(){
        status = 2
        click = 0
        myList = mutableListOf(2)
    }

}