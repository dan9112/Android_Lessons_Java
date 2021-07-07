package com.example.t2_viewmodel

import android.util.Log
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.GlobalScope
import kotlinx.coroutines.delay
import kotlinx.coroutines.launch

class BaseViewModel : ViewModel() {

    var counter = MutableLiveData<Int>()

    var started = false

    var log = ""

    init {
        counter.value = 0
        Log.i("BaseViewModel", "BaseViewModel created!")
    }

    fun startCount() {
        started = !started
        GlobalScope.launch(Dispatchers.Main) {
            while (started) {
                delay(10)
                counter.value = counter.value!! + 1
            }
        }
    }
}