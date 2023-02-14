package com.example.myapplication

import android.os.Bundle
import android.util.DisplayMetrics
import android.util.Log
import android.view.Gravity
import android.view.LayoutInflater
import android.view.View
import android.widget.GridLayout
import android.widget.ImageView
import android.widget.TextView
import androidx.appcompat.app.AppCompatActivity

class AdminActivity : AppCompatActivity() {

    lateinit var grid:GridLayout

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_admin_2)
        grid = findViewById(R.id.grid_changes)
        setUpGrid()
        //findViewById<TextView>(R.id.create_event).setOnClickListener(View.OnClickListener { startActivity(Intent(this, CreationEventActivity::class.java)) })

        //findViewById<TextView>(R.id.create_news).setOnClickListener(View.OnClickListener { startActivity(Intent(this, CreateNewsActivity::class.java)) })



    }
    class Change(val name: String, val id: Int)

    private fun setUpGrid() {
        val total = 4
        val names:ArrayList<Change> = ArrayList()
        names.add(Change("Создать новость", R.drawable.icon_hogwarts_news))
        names.add(Change("Создать событие", R.drawable.icon_hogwarts_3))
        names.add(Change("Изменить учителей", R.drawable.icon_hogwarts_teacher))
        names.add(Change("Изменить страницы кружков", R.drawable.icon_hogwarts_clubs))

        val column = 2
        val row = (total + 1) / column
        grid.columnCount = column
        grid.rowCount = row + 1
        var i = 0
        var c = 0
        var r = 0
        val infl:LayoutInflater = layoutInflater
        while (i < total) {
            if (c == column) {
                c = 0
                r++
            }
            val view: View = infl.inflate(R.layout.element_admin_control_page_element, null, false)
            val str : TextView = view.findViewById(R.id.text_change)
            val image : ImageView = view.findViewById(R.id.change_image)
            str.text = names[i].name
            image.setImageDrawable(resources.getDrawable(names[i].id))
            val param = GridLayout.LayoutParams()
            param.height = dpSize(190f)
            param.width = dpSize(150f)
            if (c == 0) {
                param.rightMargin = dpSize(15f)
            }
            if (r != 0) {
                param.topMargin = dpSize(dpSize(8f).toFloat())
            }
            param.setGravity(Gravity.CENTER)
            param.columnSpec = GridLayout.spec(c)
            param.rowSpec = GridLayout.spec(r)
            view.layoutParams = param
            grid.addView(view)
            i++
            c++
        }
    }

    fun dpSize(dp: Float): Int {
        val metrics: DisplayMetrics = resources.displayMetrics
        val fpixels = metrics.density * dp
        return (fpixels + 0.5f).toInt()
    }
}