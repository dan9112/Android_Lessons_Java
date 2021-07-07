package com.example.t2_viewmodel

import android.annotation.SuppressLint
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.databinding.DataBindingUtil
import androidx.fragment.app.Fragment
import androidx.lifecycle.ViewModelProvider
import com.example.t2_viewmodel.databinding.FragmentBaseBinding
import kotlinx.coroutines.*

class BaseFragment : Fragment() {

    private lateinit var viewModel: BaseViewModel

    private lateinit var binding: FragmentBaseBinding

    @SuppressLint("SetTextI18n")
    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        runBlocking {
            binding = DataBindingUtil.inflate(inflater, R.layout.fragment_base, container, false)
            viewModel = ViewModelProvider(this@BaseFragment).get(BaseViewModel::class.java)

            binding.logWindow.text = viewModel.log

            viewModel.counter.observe(viewLifecycleOwner, { currentNumber ->
                binding.display.text = String.format("%.2f", currentNumber.toFloat() / 100)
            })

            binding.start.setOnClickListener {
                viewModel.startCount()
            }
            binding.start.setOnLongClickListener {
                viewModel.started = false
                viewModel.counter.value = 0
                return@setOnLongClickListener true
            }
            binding.log.setOnClickListener {
                viewModel.log = binding.display.text.toString() + "\n" + viewModel.log
                binding.logWindow.text = viewModel.log
            }
            binding.log.setOnLongClickListener {
                viewModel.log = ""
                binding.logWindow.text = ""
                return@setOnLongClickListener true
            }
        }
        return binding.root
    }
}