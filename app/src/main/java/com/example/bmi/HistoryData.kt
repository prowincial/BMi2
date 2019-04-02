package com.example.bmi

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView



class HistoryData : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_history_data)
        checkData()
    }

    private fun checkData(){
        val names:ArrayList<String> =ArrayList()
        val wynik:ArrayList<String> = intent.getStringArrayListExtra("wynik")
        val colors:ArrayList<Int> = intent.getIntegerArrayListExtra("color")
        val num10:Int

        if (wynik.size < 10){
            num10 =0
        }
        else
            num10 =wynik.size-10

        val wynik10=wynik.subList(num10,wynik.size)
        val color10=colors.subList(num10,wynik.size)

        for (i in 1..wynik.size){
            names.add("$i")

        }

        val recyclerView: RecyclerView = findViewById(R.id.recyclerView)
        recyclerView.layoutManager = LinearLayoutManager(this)
        recyclerView.adapter = DataAdapter(names,wynik10,color10)

    }
}
