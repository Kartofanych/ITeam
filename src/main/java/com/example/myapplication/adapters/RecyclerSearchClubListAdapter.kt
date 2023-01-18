package com.example.myapplication.adapters

import android.app.Activity
import android.content.res.ColorStateList
import android.os.Build
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import androidx.annotation.RequiresApi
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.example.myapplication.R
import com.example.myapplication.models.Club
import com.example.myapplication.models.Teacher
import kotlin.reflect.typeOf


class RecyclerSearchClubListAdapter(var searchList: ArrayList<Club>, var activity: Activity) :
    RecyclerView.Adapter<RecyclerSearchClubListAdapter.ViewHolder>(){

    inner class ViewHolder(itemView: View):RecyclerView.ViewHolder(itemView){
        val clubName:TextView = itemView.findViewById(R.id.club_name)
        val clubType:TextView = itemView.findViewById(R.id.club_type)
        val typeColor:View = itemView.findViewById(R.id.type_color)
        val imageView: ImageView = itemView.findViewById(R.id.teacher_image)
        val teacherImageBackground: ImageView = itemView.findViewById(R.id.teacher_image_background)
        val title: TextView = itemView.findViewById(R.id.teacher_title)
        val name: TextView = itemView.findViewById(R.id.teacher_name)
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        val v = LayoutInflater.from(parent.context).inflate(R.layout.element_search_clubs_for_recycler, parent, false)
        return ViewHolder(v)
    }

    @RequiresApi(Build.VERSION_CODES.M)
    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        val club:Club = searchList[position]
        holder.clubName.text = club.name
        holder.clubType.text = club.type

        val color = setColor(club.type)
        holder.typeColor.backgroundTintList = ColorStateList.valueOf(color)
        holder.clubType.setTextColor(color)
        holder.teacherImageBackground.setBackgroundColor(color)


        val teacher:Teacher = club.curator
        Glide.with(activity)
            .load(teacher.imageUrl)
            .into(holder.imageView)
        holder.title.text = teacher.title
        holder.name.text = teacher.name
    }

    @RequiresApi(Build.VERSION_CODES.M)
    private fun setColor(clubType:String): Int {
        when(clubType){
            "спорт"->return activity.getColor(R.color.blue_sport)
            "олимпиады"->return activity.getColor(R.color.orange_olympiads)
        }
        return 5

    }

    override fun getItemCount(): Int {
        return searchList.size
    }

}