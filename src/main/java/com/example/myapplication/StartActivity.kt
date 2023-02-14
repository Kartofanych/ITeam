package com.example.myapplication

import android.content.Context
import android.content.Intent
import android.content.SharedPreferences
import android.os.Bundle
import android.view.MotionEvent
import android.view.View
import android.widget.RelativeLayout
import android.widget.TextView
import androidx.appcompat.app.AppCompatActivity
import androidx.core.content.res.ResourcesCompat
import com.google.firebase.auth.FirebaseAuth
import com.makeramen.roundedimageview.RoundedImageView

class StartActivity : AppCompatActivity() {


    val APP_PREFERENCES = "mysettings"
    val APP_PREFERENCES_GROUPS = "zero"



    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_start)

        var student = true

        val auth = FirebaseAuth.getInstance()
        if(auth.currentUser!=null){
            startActivity(Intent(this, NavigationActivity::class.java))
        }

        //val mSettings = getSharedPreferences(APP_PREFERENCES, Context.MODE_PRIVATE)
        val guest: RelativeLayout = findViewById(R.id.card_student)
        val guestText: TextView = findViewById(R.id.student_text)
        val admin: RelativeLayout = findViewById(R.id.card_admin)
        val adminText:TextView = findViewById(R.id.admin_text)
        val contin:TextView = findViewById(R.id.contin)

        guest.setOnClickListener(View.OnClickListener {
            guest.background = ResourcesCompat.getDrawable(resources, R.drawable.element_image_solid_corners_touched, theme)
            admin.background = ResourcesCompat.getDrawable(resources, R.drawable.element_edit_field, theme)
            guestText.setTextColor(ResourcesCompat.getColor(resources, R.color.blue_it, theme))
            adminText.setTextColor(ResourcesCompat.getColor(resources, R.color.black, theme))
            student = true
        })

        admin.setOnClickListener(View.OnClickListener {
            admin.background = ResourcesCompat.getDrawable(resources, R.drawable.element_image_solid_corners_touched, theme)
            guest.background = ResourcesCompat.getDrawable(resources, R.drawable.element_edit_field, theme)
            adminText.setTextColor(ResourcesCompat.getColor(resources, R.color.blue_it, theme))
            guestText.setTextColor(ResourcesCompat.getColor(resources, R.color.black, theme))
            student = false
        })

        contin.setOnClickListener {
            if(student) {
                startActivity(Intent(this, LoginUserActivity::class.java))

                /*val editor = mSettings.edit()
                editor.clear()
                editor.apply()
                if (mSettings.contains(APP_PREFERENCES_GROUPS)) {
                    startActivity(Intent(this, NavigationActivity::class.java))
                } else {
                    startActivity(Intent(this, ChoosingGroupsActivity::class.java))
                }*/
            }else{
                startActivity(Intent(this, LoginAdminActivity::class.java))
            }
        }

    }

}