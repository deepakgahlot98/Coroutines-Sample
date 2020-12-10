package com.example.coroutinessample

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import com.bumptech.glide.Glide
import kotlinx.android.synthetic.main.activity_main.*
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers.IO
import kotlinx.coroutines.Dispatchers.Main
import kotlinx.coroutines.delay
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext

class MainActivity : AppCompatActivity() {

    private val DUMMY_DATA = "Setting image 1"
    private val DUMMY_DATA2 = "Setting image 2"

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        button.setOnClickListener {
            CoroutineScope(IO).launch {
                fakeDummyCall()
            }
        }
    }

    private suspend fun setImageOnMainThread(imageUrl: String) {
        withContext(Main) {
            Glide.with(applicationContext).load(imageUrl).into(imageview)
        }
    }

    private suspend fun setTextOnMainThread(input: String) {
        withContext(Main) {
            textview.text = input
        }
    }

    private suspend fun fakeDummyCall() {
        val result1 = getResultDummyCall()
        println("debug: $result1")
        setTextOnMainThread(result1)
        delay(1000)
        setImageOnMainThread("https://homepages.cae.wisc.edu/~ece533/images/mountain.png")

        val result2 = getResultDummyCall1()
        println("debug: $result2")
        setTextOnMainThread(result2)
        delay(2000)
        setImageOnMainThread("https://homepages.cae.wisc.edu/~ece533/images/airplane.png")
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