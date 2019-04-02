package com.example.bmi

import android.content.Context
import android.content.Intent
import android.graphics.Color
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.Menu
import android.view.MenuItem
import android.view.View
import android.widget.Toast
import androidx.core.graphics.toColorInt
import com.example.bmi.logic.BmiForInPo
import com.example.bmi.logic.BmiForKgCm
import com.google.gson.Gson
import com.google.gson.reflect.TypeToken
import kotlinx.android.synthetic.main.activity_main.*


class MainActivity : AppCompatActivity() {
    var flag:Boolean = true
    var bmiList:ArrayList<String> = ArrayList()
    var colorList:ArrayList<Int> = ArrayList()




    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        loadData()


        }

    fun countMe(view: View){

        if (inputMass.text.isNotEmpty() && inputWeight.text.isNotEmpty()) {
            var mass = inputMass.text.toString().toInt()
            var rost = inputWeight.text.toString().toInt()
            var result:Double

            if (flag){
                var bmi = BmiForKgCm(mass, rost)
                result = bmi.countBmi()
            }
            else{
                var bmi = BmiForInPo(mass,rost)
                result = bmi.countBmi()
            }
            textCOlor(result)
            checkData()
            bmiList.add(String.format("%.2f",result))
            colorList.add(resultBMI.currentTextColor)
            resultBMI.text="$result"

        }
        else toastMe()
        saveData()
    }

    private fun checkData(){
        if (inputMass.text.toString().toInt() < 0){
            inputMass?.error = "Height>0"
        }
        if (inputWeight.text.toString().toInt() < 0){
            inputWeight?.error = "Weight >0"
        }
    }
    private fun toastMe(){
        val myToast = Toast.makeText(this,"Enter data", Toast.LENGTH_SHORT)
        myToast.show()
    }

    private fun textCOlor(result:Double)=when(result){
        in 0.0..18.49->{
            typeOfWeight.text = "Underweight"
            resultBMI.text = String.format("%.2f",result)
            resultBMI.setTextColor(Color.parseColor("#3366CC"))
            typeOfWeight.setTextColor("#3366CC".toColorInt())
        }
        in 18.5..24.9->{
            typeOfWeight.text = "Normal"
            resultBMI.text = String.format("%.2f",result)
            resultBMI.setTextColor(Color.parseColor("#00A693"))
            typeOfWeight.setTextColor("#00A693".toColorInt())
        }
        in 25.0..29.9->{
            typeOfWeight.text = "Overweight"
            resultBMI.text = String.format("%.2f",result)
            resultBMI.setTextColor(Color.parseColor("#F68121"))
            typeOfWeight.setTextColor("#F68121".toColorInt())
        }
        in 30.0..34.9->{
            typeOfWeight.text = "Obese"
            resultBMI.text = String.format("%.2f",result)
            resultBMI.setTextColor(Color.parseColor("#E74536"))
            typeOfWeight.setTextColor("#E74536".toColorInt())
        }

        else->{
            typeOfWeight.text = "Extremely Obese"
            resultBMI.text = String.format("%.2f",result)
            resultBMI.setTextColor(Color.parseColor("#BE3B8B"))
            typeOfWeight.setTextColor("#BE3B8B".toColorInt())
        }
    }
    override fun onCreateOptionsMenu(menu: Menu): Boolean {
        val inflater = menuInflater
        inflater.inflate(R.menu.menu, menu)
        return true
    }

    override fun onOptionsItemSelected(item: MenuItem) = when (item.itemId) {
        R.id.action_about_me-> {
            val intent = Intent(this,About_me::class.java)
            startActivity(intent)
            true

        }
        R.id.action_zmiana -> {
            if (flag)
                changeView()
            else
                changeView1()
            true
        }
        R.id.action_history -> {
            val intent = Intent(this, HistoryData::class.java)
            intent.putExtra("wynik",bmiList)
            intent.putExtra("color",colorList)

            startActivity(intent)

            true

        }

        else -> {
            super.onOptionsItemSelected(item)
        }
    }
    fun giveBMI(view: View){
        if (resultBMI.text.isNotEmpty()) {
            val intent = Intent(this, BMI_activity::class.java)
            val count: Double = resultBMI.text.toString().toDouble()

            intent.putExtra(BMI_activity.TOTAL_COUNT, count)
            startActivity(intent)
        }
        else
            toastMe()
    }
    fun changeView( )
    {       textView.text = "Weight[inches]"
            textView2.text = "Height[pounds]"
            inputMass.text = null
            inputWeight.text = null
            resultBMI.text = null
            typeOfWeight.text = null
            flag = false
        }
    fun changeView1()
        {
            textView.text = "Weight [kg]"
            textView2.text = "Height[m]"
            inputMass.text = null
            inputWeight.text = null
            resultBMI.text = null
            typeOfWeight.text = null
            flag = true
        }

    override fun onRestoreInstanceState(savedInstanceState: Bundle?) {
        super.onRestoreInstanceState(savedInstanceState)
        val wynikText = savedInstanceState?.getCharSequence("savedWynik")
        val wynikColor = savedInstanceState?.getInt("savedColor")
        val wynikType = savedInstanceState?.getCharSequence("savedWynikType")
        val wynikColorType = savedInstanceState?.getInt("savedWynikColorType")
        val wynikTextWeight = savedInstanceState?.getCharSequence("savedTextWeight")
        val wynikTextHeight = savedInstanceState?.getCharSequence("savedTextHeight")

        resultBMI.setText(wynikText)
        if(wynikColor!=null)
            resultBMI.setTextColor(wynikColor)
        typeOfWeight.setText(wynikType)
        if (wynikColorType!=null)
            typeOfWeight.setTextColor(wynikColorType)
        textView.setText(wynikTextWeight)
        textView2.setText(wynikTextHeight)



    }

    override fun onSaveInstanceState(outState: Bundle?) {
        super.onSaveInstanceState(outState)
        val wynikText = resultBMI.text
        val wynikColor=resultBMI.currentTextColor
        val wynikType = typeOfWeight.text
        val wynikColorType = typeOfWeight.currentTextColor
        val textWieght = textView.text
        val textHieght = textView2.text

        outState?.putCharSequence("savedWynik", wynikText)
        outState?.putInt("savedColor",wynikColor)
        outState?.putCharSequence("savedWynikType",wynikType)
        outState?.putInt("savedWynikColorType",wynikColorType)
        outState?.putCharSequence("savedTextWeight",textWieght)
        outState?.putCharSequence("savedTextHeight",textHieght)




    }
    fun saveData(){
        val sp= getSharedPreferences("savedData", Context.MODE_PRIVATE)
        val ed=sp!!.edit()
        val gson= Gson()
        val bmiString:String=gson.toJson(bmiList)
        val colorString:String=gson.toJson(colorList)
        ed.putString("sBmi",bmiString)
        ed.putString("sColor",colorString)
        ed.apply()
    }
    fun loadData(){
        val sp= getSharedPreferences("savedData", Context.MODE_PRIVATE)
        val gson= Gson()
        val bmiString:String=sp.getString("sBmi",gson.toJson(bmiList))
        val colorString:String=sp.getString("sColor",gson.toJson(colorList))
        val type = object : TypeToken<ArrayList<String>>(){}.type
        val type1 = object :TypeToken<ArrayList<Int>>(){}.type
        bmiList=gson.fromJson(bmiString,type)
        colorList=gson.fromJson(colorString,type1)



    }





}
