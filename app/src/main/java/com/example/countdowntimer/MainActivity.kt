package com.example.countdowntimer

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.os.CountDownTimer
import android.util.Log
import android.widget.*
import androidx.core.view.isVisible
import java.util.concurrent.TimeUnit


class MainActivity : AppCompatActivity() {

    //declaring variables
    private var timeCountInMilliSeconds = 60000L
    private enum class TimerStatus{
        STARTED, STOPPED
    }
    private var timerStatus = TimerStatus.STOPPED
    private lateinit var progressBar: ProgressBar
    private lateinit var editTextMinute : EditText
    private lateinit var textViewTime : TextView
    private lateinit var imageViewReset : ImageView
    private lateinit var imageViewStartStop :  ImageView
    private lateinit var countDownTimer: CountDownTimer



    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        //method to initialize the Views
        initViews()
        //method to initialize the Listeners
        imageViewReset.setOnClickListener{
            reset()
        }
        imageViewStartStop.setOnClickListener{
            startStop()
        }

    }



//initializing function

    private fun initViews() {
        progressBar = findViewById(R.id.progressBarCircle)
        editTextMinute = findViewById(R.id.editTextMinute)
        textViewTime = findViewById(R.id.textViewTime)
        imageViewReset = findViewById(R.id.imageViewReset)
        imageViewStartStop = findViewById(R.id.imageViewStartStop)


    }

    //method to reset countDownTimer
    private fun reset() {
        stopCountDownTimer();
        startCountDownTimer();

    }



    //method to start and Stop countDownTimer
    private fun startStop() {
       if(timerStatus == TimerStatus.STOPPED){
           setTimerValues();
           // call to initialize the progress bar values
           setProgressBarValues();
           // showing the reset icon
           imageViewReset.isVisible = true;
           // changing play icon to stop icon
           imageViewStartStop.setImageResource(R.drawable.icon_stop);
           // making edit text not editable
           editTextMinute.setEnabled(false);
           // changing the timer status to started
           timerStatus = TimerStatus.STARTED;
           // call to start the count down timer
           startCountDownTimer();

       }else{
           // hiding the reset icon
           imageViewReset.isVisible = false;
           // changing stop icon to start icon
           imageViewStartStop.setImageResource(R.drawable.icon_start);
           // making edit text editable
           editTextMinute.setEnabled(true);
           // changing the timer status to stopped
           timerStatus = TimerStatus.STOPPED;
           stopCountDownTimer();
       }


    }



    private fun setTimerValues() {
        var time = 0
        if (editTextMinute.text !=null) {
            // fetching value from edit text and type cast to integer
            time = (editTextMinute.text.toString().trim()).toInt()
        } else {
            // toast message to fill edit text
            Toast.makeText(this, R.string.message_minutes, Toast.LENGTH_SHORT).show();
        }
        // assigning values after converting to milliseconds
        timeCountInMilliSeconds = (time * 60 * 1000).toLong()
    }
    private fun startCountDownTimer() {
       countDownTimer = object : CountDownTimer(timeCountInMilliSeconds,1000){
           override fun onTick(millisUntilFinished: Long) {
               Log.i("TimeinMIlli",millisUntilFinished.toString())

               textViewTime.text = hmsTimeFormatter(millisUntilFinished).toString()

               progressBar.setProgress(((millisUntilFinished / 1000).toInt()))

           }

           override fun onFinish() {

               textViewTime.text = hmsTimeFormatter(timeCountInMilliSeconds).toString();
               // call to initialize the progress bar values
               setProgressBarValues();
               // hiding the reset icon
               imageViewReset.isVisible = false;
               // changing stop icon to start icon
               imageViewStartStop.setImageResource(R.drawable.icon_start);
               // making edit text editable
               editTextMinute.setEnabled(true);
               // changing the timer status to stopped
               timerStatus = TimerStatus.STOPPED;
           }

       }
        countDownTimer.start()

    }

    private fun setProgressBarValues() {
        progressBar.setMax((timeCountInMilliSeconds / 1000).toInt());
        progressBar.setProgress((timeCountInMilliSeconds / 1000).toInt())    }

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


}