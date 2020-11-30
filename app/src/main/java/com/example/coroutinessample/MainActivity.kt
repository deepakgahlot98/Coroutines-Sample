package com.example.coroutinessample

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import kotlinx.android.synthetic.main.activity_main.*
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers.IO
import kotlinx.coroutines.Dispatchers.Main
import kotlinx.coroutines.delay
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext

class MainActivity : AppCompatActivity() {

    private val DUMMY_DATA = "Result is being printed"
    private val DUMMY_DATA2 = "Second Result is being printed"

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        button.setOnClickListener {
            CoroutineScope(IO).launch {
                fakeDummyCall()
            }
        }
    }

    private suspend fun setTextOnMainThread(input: String) {
        withContext(Main) {
            val newText = textview.text.toString() + "\n$input"
            textview.text = newText
        }
    }

    private suspend fun fakeDummyCall() {
        val result1 = getResultDummyCall()
        println("debug: $result1")
        setTextOnMainThread(result1)

        val result2 = getResultDummyCall1()
        println("debug: $result2")
        setTextOnMainThread(result2)
    }

    //delay will only delay this single coroutine not the entire thread
    private suspend fun getResultDummyCall(): String {
        delay(1000)
        return DUMMY_DATA
    }

    //Just another dummy method to make another background thask
    private suspend fun getResultDummyCall1(): String {
        delay(2000)
        return DUMMY_DATA2
    }
}