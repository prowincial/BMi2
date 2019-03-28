package com.example.bmi.logic

class BmiForInPo (var mass:Int,var height:Int):Bmi {
    override fun countBmi():Double{
        val bmi: Double= 703.0*mass/height/height
        return bmi

    }

}