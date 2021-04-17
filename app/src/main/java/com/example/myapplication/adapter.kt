package com.example.myapplication

import android.annotation.SuppressLint
import android.graphics.drawable.Drawable
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import androidx.core.content.ContextCompat
import androidx.recyclerview.widget.RecyclerView
import kotlinx.android.synthetic.main.item.view.*

class adapter(
    var context: MainActivity,
    private var arrayList: List<String>,
    private var logo: List<Drawable>,
    private var listener: ClickListener
): RecyclerView.Adapter<MyViewHolder>() {
    private var selectedPosition = -1
    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): MyViewHolder {
        val view =
            LayoutInflater.from(parent.context).inflate(R.layout.item, parent, false)
        return MyViewHolder(view, context)
    }

    override fun getItemCount(): Int {
        println("size = ${arrayList.size}")
        return arrayList.size
    }

    fun updateAdapterArrayList(arrayList: List<String>) {
        this.arrayList = arrayList
    }

    fun updateSelectedUnselected() {
        selectedItemPos = -1
        lastItemSelectedPos = -1
        notifyItemChanged(selectedItemPos, lastItemSelectedPos)
    }

    @SuppressLint("ResourceAsColor")
    override fun onBindViewHolder(holder: MyViewHolder, position: Int) {
        itemClickListener = listener
        val name = arrayList[position]
        val logoicon = logo[position]

        holder.itemView.setOnClickListener {
            selectedItemPos = holder.adapterPosition
            if (lastItemSelectedPos == -1)
                lastItemSelectedPos = selectedItemPos
            else {
                notifyItemChanged(lastItemSelectedPos)
                lastItemSelectedPos = selectedItemPos
            }
            notifyItemChanged(selectedItemPos)
            listener.onClick(position, name)
        }

        if (position == selectedItemPos) {
            holder.selectedBg()
        } else
            holder.defaultBg()
        holder.bind(name, logoicon)

    }
}
var itemClickListener: ClickListener? = null
var selectedItemPos = -1
var lastItemSelectedPos = -1


class MyViewHolder(itemView: View, Context: MainActivity): RecyclerView.ViewHolder(itemView) {

    val appname : TextView =itemView.tvApps
    val applogo : ImageView = itemView.ivApps
    fun bind(name: String, icon: Drawable){
        appname.text = name
        applogo.setImageDrawable(icon)
    }

    fun defaultBg() {
        itemView.setBackgroundResource(R.color.tp)
    }

    fun selectedBg() {
        itemView.setBackgroundResource(R.color.tp2)
    }
}

interface ClickListener {
    fun onClick(position: Int, month: String)
}
