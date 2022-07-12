package com.example.androidstudioproject

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import kotlinx.android.synthetic.main.listlayout.view.*

class HelpViewAdapter(val DataList:ArrayList<Data>) : RecyclerView.Adapter<HelpViewAdapter.HelpViewHolder>() {
    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): HelpViewHolder {
        val cellForRow = LayoutInflater.from(parent.context).inflate(R.layout.listlayout, parent, false)
        return HelpViewHolder(cellForRow)
    }

    override fun getItemCount(): Int {
        return DataList.size
    }

    override fun onBindViewHolder(holder: HelpViewHolder, position: Int) {
        holder.profile.setImageResource(DataList[position].profile)
        holder.name.text = DataList[position].name
    }

    class HelpViewHolder(v: View) : RecyclerView.ViewHolder(v) {
        var profile = v.imageView
        var name = v.exampletextView
    }
}
