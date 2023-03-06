package com.example.spark

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView

class MyAdapter(private val message : ArrayList<ReceivedUser>) : RecyclerView.Adapter<MyAdapter.MyViewHolder>() {


    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): MyViewHolder {
       val itemView = LayoutInflater.from(parent.context).inflate(R.layout.item,parent,false)
        return MyViewHolder(itemView)
    }

    override fun getItemCount(): Int {
        return message.size
    }

    override fun onBindViewHolder(holder: MyViewHolder, position: Int) {
        holder.sandesh.text = message[position].message
        holder.bhejneWale.text = message[position].name
    }
    class MyViewHolder(itemView : View) : RecyclerView.ViewHolder(itemView){

        val sandesh = itemView.findViewById<TextView>(R.id.sandesh)!!
        val bhejneWale = itemView.findViewById<TextView>(R.id.nameofsender)

    }
}