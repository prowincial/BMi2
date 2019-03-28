package com.example.bmi

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import kotlinx.android.synthetic.main.activity_bmi_activity.*


class BMI_activity : AppCompatActivity() {

    companion object {
        const val TOTAL_COUNT ="total_count"
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_bmi_activity)
        showBMiIndex()
    }
    private fun showBMiIndex(){
        val count = intent.getDoubleExtra(TOTAL_COUNT,0.0)
        textView4.text = count.toString()
    }
}
