package com.example.countdowntimer

import android.os.BadParcelableException
import android.os.CountDownTimer
import android.util.Log
import android.widget.ProgressBar
import android.widget.Toast
import androidx.core.view.isVisible
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import java.util.concurrent.TimeUnit

class CountDownTimerViewModel : ViewModel() {
    // TODO: Implement the ViewModel
     var timeCountInMilliSeconds = 60000L
    enum class TimerStatus{
        STARTED, STOPPED
    }
    private var timerStatus = TimerStatus.STOPPED
    private lateinit var countDownTimer: CountDownTimer


    private val _startOrStopIcon   =MutableLiveData<Int>()
    val startOrStopIcon : LiveData<Int>
    get() = _startOrStopIcon

    private val _timerText = MutableLiveData<String>()
    val timerText : LiveData<String>
    get() = _timerText

    private val _progressBarValues = MutableLiveData<Int>()
    val progressBarValues : LiveData<Int>
    get()= _progressBarValues

    private val _maxProgressBarValues = MutableLiveData<Int>()
    val maxProgressBarValues : LiveData<Int>
        get()= _maxProgressBarValues

    
    private  val  _isResetImageVisible = MutableLiveData<Boolean>()
    val isResetImageVisible : LiveData<Boolean>
    get() =  _isResetImageVisible
    
    private  val _isEditTextView  = MutableLiveData<Boolean>()
    val  isEditTextView : LiveData<Boolean>
    get() = _isEditTextView




    init {
        _isEditTextView.value = true
        _timerText.value = "00:00:00"
        _maxProgressBarValues.value = 100
        _progressBarValues.value = 100
    }
    fun reset() {
        stopCountDownTimer();
        startCountDownTimer();

    }
    //method to start and Stop countDownTimer
     fun startStop(timerValue : Long) {
        if(timerStatus ==TimerStatus.STOPPED){
            timeCountInMilliSeconds = timerValue

            // call to initialize the progress bar values
            set_progressBarValues();
           // showing the reset icon
            _isResetImageVisible.value= true;
            // changing play icon to stop icon
            _startOrStopIcon.value = -1
            // making edit text not editable
            _isEditTextView.value = false
            // changing the timer status to started
            timerStatus = TimerStatus.STARTED;
            // call to start the count down timer
            startCountDownTimer();

        }else{
            // hiding the reset icon
             _isResetImageVisible.value = false;
            // changing stop icon to start icon
           _startOrStopIcon.value = 1
            // making edit text editable
            _isEditTextView.value = true
            // changing the timer status to stopped
            timerStatus = TimerStatus.STOPPED;
            stopCountDownTimer();
        }


    }




    private fun startCountDownTimer() {
        countDownTimer = object : CountDownTimer(timeCountInMilliSeconds,1000){
            override fun onTick(millisUntilFinished: Long) {
                Log.i("TimeinMIlli",millisUntilFinished.toString())

                _timerText.value = hmsTimeFormatter(millisUntilFinished).toString()

                _progressBarValues.value = ((millisUntilFinished / 1000).toInt())

            }

            override fun onFinish() {

                _timerText.value = hmsTimeFormatter(timeCountInMilliSeconds).toString();
                // call to initialize the progress bar values
                set_progressBarValues();
                // hiding the reset icon
                 _isResetImageVisible.value = false;
                // changing stop icon to start icon
                _startOrStopIcon.value = 1
                // making edit text editable
               _isEditTextView.value = true
                // changing the timer status to stopped
                timerStatus = TimerStatus.STOPPED;
            }

        }
        countDownTimer.start()

    }
    private fun stopCountDownTimer() { countDownTimer.cancel()
    }
    private fun hmsTimeFormatter(milliSeconds: Long): Any {
        val hms = String.format("%02d:%02d:%02d",
                TimeUnit.MILLISECONDS.toHours(milliSeconds),
                TimeUnit.MILLISECONDS.toMinutes(milliSeconds) - TimeUnit.HOURS.toMinutes(TimeUnit.MILLISECONDS.toHours(milliSeconds)),
                TimeUnit.MILLISECONDS.toSeconds(milliSeconds) - TimeUnit.MINUTES.toSeconds(TimeUnit.MILLISECONDS.toMinutes(milliSeconds)))
        Log.i("TimermilleSecons",hms)

        return hms

    }
    private fun set_progressBarValues() {
        _maxProgressBarValues.value = (timeCountInMilliSeconds / 1000).toInt();
        _progressBarValues.value = (timeCountInMilliSeconds / 1000).toInt()
    }


}