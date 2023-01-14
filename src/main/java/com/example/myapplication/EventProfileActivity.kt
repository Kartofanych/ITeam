package com.example.myapplication

import android.graphics.drawable.Drawable
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.View
import android.widget.ImageView
import android.widget.TextView
import androidx.core.content.res.ResourcesCompat
import com.bumptech.glide.Glide
import com.smb.glowbutton.GlowButton

class EventProfileActivity : AppCompatActivity() {

    lateinit var title:TextView
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
        image = findViewById(R.id.event_image)
        back = findViewById(R.id.back)
        comeButton = findViewById(R.id.come_button)

        title.text = options.getString("event_name")
        info.text = options.getString("event_info")
        Glide.with(this)
            .load(options.getString("event_imageUrl"))
            .into(image)

        back.setOnClickListener(View.OnClickListener {

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
}