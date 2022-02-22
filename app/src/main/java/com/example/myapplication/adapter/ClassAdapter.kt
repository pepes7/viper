package com.example.myapplication.adapter

import android.content.Context
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageButton
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.example.myapplication.R

class ClassAdapter (private val context: Context, private val listServicos: ArrayList<String>, private val codeClass: ArrayList<String> ) : RecyclerView.Adapter<ClassAdapter.MyViewHolder>(){

    override fun onCreateViewHolder(viewGroup: ViewGroup, i: Int): MyViewHolder {
        val view = LayoutInflater.from(viewGroup.context).inflate(R.layout.list_class,viewGroup,false)
        return MyViewHolder(view)

    }

    override fun getItemCount(): Int {
        return listServicos.size
    }

    override fun onBindViewHolder(myViewHolder: MyViewHolder, i: Int) {
        val servico = listServicos.get(i)
        myViewHolder.title.text = servico

        //metodo de click
        myViewHolder.delete.setOnClickListener {
            val newPosition: Int = myViewHolder.adapterPosition
            listServicos.removeAt(newPosition)
            codeClass.removeAt(newPosition)
            notifyItemRemoved(newPosition)
            notifyItemRangeChanged(newPosition, listServicos.size)
        }
    }

    inner  class MyViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
        var title : TextView
        var delete : ImageButton

        init {
            title = itemView.findViewById(R.id.txt_class)
            delete = itemView.findViewById(R.id.btn_delete_class)
        }
    }


}