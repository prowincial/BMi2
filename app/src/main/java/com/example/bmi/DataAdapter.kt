package com.example.bmi

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView

class DataAdapter(val users:ArrayList<String>, val wyniks:MutableList<String>,val colors:MutableList<Int>):
    RecyclerView.Adapter<DataAdapter.ViewHolder>(){
    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        val view: View = LayoutInflater.from(parent.context).inflate(R.layout.data_raws,parent,false)
        return ViewHolder(view)

    }

    override fun getItemCount(): Int {
        return wyniks.size
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        holder.number.text=users[position]
        holder.wynik.text=wyniks[position]
        holder.wynik.setTextColor(colors[position])


    }

    inner class ViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
        val number: TextView =itemView.findViewById(R.id.number)
        val wynik:TextView=itemView.findViewById(R.id.wartosc)

    }

}