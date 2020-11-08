package com.example.simplestopwatch

import android.graphics.Color
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.TypedValue
import android.widget.TextView
import kotlinx.android.synthetic.main.activity_main.*
import java.text.SimpleDateFormat
import java.time.LocalDateTime
import java.time.format.DateTimeFormatter
import java.util.*
import kotlin.concurrent.timer

class MainActivity : AppCompatActivity() {
    private var time = 0
    private var timerTask: Timer? = null
    private var isRunning = false
    private var lap = 1

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        start_btn.setOnClickListener {
            isRunning = !isRunning

            if(isRunning){
                start()
            }else{
                pause()
            }
        }

        record_btn.setOnClickListener {
            recoreLapTime()
        }

        reset_btn.setOnClickListener {
            reset()
        }
    }

    private fun start(){
        start_btn.setText("일시정지")
        start_btn.setBackgroundColor(Color.parseColor("#ef9e9f"));

        timerTask = timer(period = 10){
            time++
            val sec = time / 100
            val milli = time % 100
            runOnUiThread{
                secTextView.text = "$sec"
                milliTextView.text = "$milli"
            }
        }
    }

    private fun pause(){
        start_btn.setText("다시시작")
        start_btn.setBackgroundColor(Color.parseColor("#d9e1e8"));
        timerTask?.cancel()
    }

    private fun recoreLapTime(){
        val currentDateTime = Calendar.getInstance().time
        var dateFormat = SimpleDateFormat("yy.MM.dd HH:mm:ss", Locale.KOREA).format(currentDateTime)
        val lapTime = this.time
        val textView = TextView(this)
        textView.setTextColor(Color.parseColor("#d9e1e8"));
        textView.setTextSize(TypedValue.COMPLEX_UNIT_DIP, 18F)
        textView.text = "$lap 번째 : ${lapTime / 100}.${lapTime % 100}\t\t\t\t\t\t\t\t\t\t\t\t${dateFormat}"

        lapLayout.addView(textView, 0)
        lap++
    }

    private fun reset(){
        timerTask?.cancel()

        time = 0
        isRunning = false
        start_btn.setText("시작")
        secTextView.text = "0"
        milliTextView.text = "00"

        lapLayout.removeAllViews()
        lap = 1
    }
}