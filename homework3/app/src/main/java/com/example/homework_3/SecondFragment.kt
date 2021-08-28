package com.example.homework_3

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Button
import android.widget.Toast

// TODO: Rename parameter arguments, choose names that match
// the fragment initialization parameters, e.g. ARG_ITEM_NUMBER
private const val ARG_PARAM1 = "param1"
private const val ARG_PARAM2 = "param2"

class SecondFragment() : Fragment() {
    // TODO: Rename and change types of parameters

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        val newArray = requireArguments().getIntArray("key")
            println("-----------------$newArray")
            val res = "sum = ${arraySum(newArray!!)}, mean = ${arrayMean(newArray)}, division = ${arrayDivision(newArray)}"
            Toast.makeText(context, res,Toast.LENGTH_LONG).show()

    }

//    override fun onCreate(savedInstanceState: Bundle?) {
//        super.onCreate(savedInstanceState)
//    }

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_second, container, false)
    }

    companion object {

        @JvmStatic
        fun newInstance(arr: IntArray): SecondFragment{
            val args = Bundle().apply {
                putIntArray("key", arr)
            }
            val secondFragment = SecondFragment()
            secondFragment.arguments = args
            return secondFragment
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

        return a
    }
}