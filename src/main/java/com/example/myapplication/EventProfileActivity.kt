package com.example.myapplication

import android.content.Intent
import android.graphics.drawable.Drawable
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.View
import android.widget.ImageView
import android.widget.RelativeLayout
import android.widget.TextView
import androidx.core.content.res.ResourcesCompat
import com.bumptech.glide.Glide
import com.example.myapplication.models.Event
import com.google.gson.Gson
import com.smb.glowbutton.GlowButton

class EventProfileActivity : AppCompatActivity() {

    lateinit var title:TextView
    lateinit var date:TextView
    lateinit var info:TextView
    lateinit var image:ImageView
    lateinit var back:ImageView
    lateinit var options:Bundle

    lateinit var comeButton: GlowButton
    var clicked:Int = 0
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_event_profile)

        options = intent.extras!!


        init()






    }

    private fun init(){
        title = findViewById(R.id.event_title)
        info = findViewById(R.id.event_info)
        date = findViewById(R.id.event_date)
        image = findViewById(R.id.event_image)
        back = findViewById(R.id.back)
        comeButton = findViewById(R.id.come_button)

        val gson = Gson()
        val event = gson.fromJson(options.getString("event"), Event::class.java)

        title.text = event.title
        date.text = event.date.toString()
        info.text = event.info
        Glide.with(this)
            .load(event.imageUrl)
            .into(image)

        back.setOnClickListener(View.OnClickListener {
            onBackPressed()
        })

        comeButton.disableWithAnimation()
        comeButton.setOnClickListener(View.OnClickListener {
            clicked+=1
            if(clicked % 2 == 0) {
                comeButton.disableWithAnimation()
                //comeButton. = not_pressed
            }else {
                comeButton.enableWithAnimation()
                //comeButton.background = pressed
            }
        })

    }

    override fun onBackPressed() {
        val rel:RelativeLayout = findViewById(R.id.animate_rel)
        rel.transitionName = "hey"
        super.onBackPressed()

    }
}