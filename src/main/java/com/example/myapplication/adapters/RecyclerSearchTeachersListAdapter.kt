package com.example.myapplication.adapters

import android.app.Activity
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.example.myapplication.R
import com.example.myapplication.models.Teacher

class RecyclerSearchTeachersListAdapter(var searchList: ArrayList<Teacher>, var activity: Activity) :
    RecyclerView.Adapter<RecyclerSearchTeachersListAdapter.ViewHolder>(){

    inner class ViewHolder(itemView: View):RecyclerView.ViewHolder(itemView){
        val imageView: ImageView = itemView.findViewById(R.id.teacher_image)
        val title: TextView = itemView.findViewById(R.id.teacher_title)
        val name: TextView = itemView.findViewById(R.id.teacher_name)
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        val v = LayoutInflater.from(parent.context).inflate(R.layout.element_search_teachers_for_recycler    , parent, false)
        return ViewHolder(v)
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        val teacher = searchList[position]
        Glide.with(activity)
            .load(teacher.imageUrl)
            .into(holder.imageView)
        holder.title.text = teacher.title + ", " + teacher.room
        holder.name.text = teacher.name
    }

    override fun getItemCount(): Int {
        return searchList.size
    }

}