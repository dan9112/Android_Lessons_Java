package com.example.t2_viewmodel

import android.util.Log
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import kotlinx.coroutines.*

class BaseViewModel : ViewModel() {

    /** Счётчик (локальная переменная класса) */
    private val _counter = MutableLiveData<Int>()

    /** Счётчик (публичная переменная ReadOnly) */
    val counter: LiveData<Int>
        get() = _counter

    /** Счётчик отмеченных в логе строк */
    private var logCounter = 1

    /**
     * Флаг секундомера.
     *
     * Коды: 0 - пауза, 1 - работа, 2 - остановка
     */
    private var started: UShort = 2u

    /** Непосредственно секундомер */
    private lateinit var job: Job

    /** Лог (локальная переменная класса) */
    private val _log = MutableLiveData<String>()

    /** Лог (публичная переменная ReadOnly) */
    val log: LiveData<String>
        get() = _log

    init {
        _counter.value = 0
        _log.value = ""
        Log.i("BaseViewModel", "BaseViewModel created!")
    }

    /** Функция счёта секундомера */
    fun startCount() {
        when (started) {
            0u.toUShort(), 2u.toUShort() -> started = 1u
            1u.toUShort() -> started = 0u
        }

        job = GlobalScope.launch(Dispatchers.Main) {
            while (started == 1u.toUShort()) {
                delay(10)
                _counter.value = counter.value!! + 1
            }
            if (started == 2u.toUShort()) _counter.value = 0
        }
    }

    fun clearLog() {
        _log.value = ""
        logCounter = 1
    }

    fun resetCounter() {
        started = 2u
        if (job.isCompleted) {
            _counter.value = 0
        }
    }

    fun updateLog(currentLink: String) {
        _log.value = log.value + "\n$logCounter: $currentLink"
        logCounter++
    }
}