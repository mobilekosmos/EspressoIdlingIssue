package com.issues.espressoidling

import android.os.Bundle
import android.view.View
import com.google.android.material.snackbar.Snackbar
import androidx.appcompat.app.AppCompatActivity
import androidx.test.espresso.IdlingResource
import androidx.test.espresso.idling.CountingIdlingResource

import kotlinx.android.synthetic.main.activity_main2.*

class MainActivity2 : AppCompatActivity() {

    var espressoTestIdlingResource = CountingIdlingResource("test")

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main2)
    }

    public fun sayHello(view : View) {
        textView.visibility = View.VISIBLE
        button.visibility = View.GONE
//        espressoTestIdlingResource.increment();
        Thread.sleep(5000)
//        espressoTestIdlingResource.decrement();
        textView.visibility = View.GONE
        button.visibility = View.VISIBLE
    }

    fun getIdlingResource(): IdlingResource {
        return espressoTestIdlingResource
    }
}