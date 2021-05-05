package com.example.countdowntimer

import android.content.Context
import android.opengl.Visibility
import android.os.Build
import androidx.lifecycle.ViewModelProvider
import android.os.Bundle
import android.os.CountDownTimer
import android.util.Log
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.*
import androidx.annotation.RequiresApi
import androidx.core.view.isVisible
import androidx.databinding.DataBindingUtil
import androidx.lifecycle.Observer
import com.example.countdowntimer.databinding.CountDownTimerFragmentBinding
import java.util.concurrent.TimeUnit

class CountDownTimer : Fragment() {
    //declaring variables

    private lateinit var binding : CountDownTimerFragmentBinding

    private lateinit var viewModel: CountDownTimerViewModel

    @RequiresApi(Build.VERSION_CODES.O)
    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        binding = DataBindingUtil.inflate(inflater,R.layout.count_down_timer_fragment,container,false)

        viewModel = ViewModelProvider(this).get(CountDownTimerViewModel::class.java)

        binding.countdownTimerViewModel = viewModel

        binding.lifecycleOwner = this

        binding.imageViewStartStop.setOnClickListener{
            viewModel.startStop(setTimerValues())
        }

        //Set reset Image View Visibility
        viewModel.isResetImageVisible.observe(viewLifecycleOwner, Observer {
            if(it){
                binding.imageViewReset.isVisible = it
            }

        })

         //Set Start and Stop button Image Source
         viewModel.startOrStopIcon.observe(viewLifecycleOwner, Observer {
             when (it) {
                  1 ->  binding.imageViewStartStop.setImageResource(R.drawable.icon_start)
                 -1 ->  binding.imageViewStartStop.setImageResource(R.drawable.icon_stop)

             }
         })



        return binding.root

    }

  // function to set the timer value from an edit Text
    private fun setTimerValues() : Long {
        var time = 0
        var timeinMilli = 0L
        if (binding.editTextMinute.text !=null) {
            // fetching value from edit text and type cast to integer
            time = (binding.editTextMinute.text.toString().trim()).toInt()
        } else {
            // toast message to fill edit text
            Toast.makeText(requireContext(), R.string.message_minutes, Toast.LENGTH_SHORT).show();
        }
        // assigning values after converting to milliseconds
       timeinMilli = (time * 60 * 1000).toLong()
        return timeinMilli
    }
}



