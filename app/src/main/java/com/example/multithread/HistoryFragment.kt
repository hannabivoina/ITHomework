package com.example.multithread

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.view.inputmethod.InputBinding
import android.widget.ArrayAdapter
import android.widget.ListView
import android.widget.SimpleAdapter
import androidx.lifecycle.ViewModelProvider
import com.example.multithread.databinding.FragmentHistoryBinding

class HistoryFragment : Fragment() {
    lateinit var mViewModel : MainViewModel
    private lateinit var binding: FragmentHistoryBinding

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        mViewModel = ViewModelProvider(requireActivity()).get(MainViewModel::class.java)
        binding = FragmentHistoryBinding.inflate(inflater,container,false)
        historyList()
        return binding.root
    }
    fun historyList(){
//        val history = mViewModel.historyList
//        val data = history?.map {
//            mapOf(
//                KEY_ID to it
//            )
//        }
//        val adapter = ArrayAdapter(requireContext(),android.R.layout.simple_list_item_1,data)
//        view?.findViewById<ListView>(R.id.HistoryList)?.adapter = adapter
    }

    companion object {
        @JvmStatic
        val KEY_ID = "id"
    }
}