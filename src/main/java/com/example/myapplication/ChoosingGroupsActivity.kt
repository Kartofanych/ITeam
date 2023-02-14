package com.example.myapplication

import android.content.Context
import android.content.Intent
import android.os.Build
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.View
import android.widget.ImageView
import android.widget.RelativeLayout
import android.widget.TextView
import androidx.annotation.RequiresApi
import androidx.appcompat.content.res.AppCompatResources.getColorStateList
import androidx.cardview.widget.CardView
import com.google.gson.Gson

class ChoosingGroupsActivity : AppCompatActivity() {

    val APP_PREFERENCES = "mysettings"
    val APP_PREFERENCES_GROUPS = "zero"

    lateinit var sportsAnim:ImageView
    lateinit var sportsCard:CardView
    lateinit var sports:RelativeLayout


    lateinit var olympiadsAnim:ImageView
    lateinit var olympiadsCard:CardView
    lateinit var olympiads:RelativeLayout


    lateinit var lyceumAnim:ImageView
    lateinit var lyceumCard:CardView
    lateinit var lyceum:RelativeLayout


    lateinit var languageAnim:ImageView
    lateinit var languageCard:CardView
    lateinit var language:RelativeLayout

    private val animations:ArrayList<ClickAnimation> = ArrayList()


    @RequiresApi(Build.VERSION_CODES.M)
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_choosing_groups)

        val mSettings = getSharedPreferences(APP_PREFERENCES, Context.MODE_PRIVATE)


        val relLater:RelativeLayout = findViewById(R.id.rel_later)
        val ready:TextView = findViewById(R.id.ready)

        relLater.setOnClickListener(View.OnClickListener {
            startActivity(Intent(this, NavigationActivity::class.java))
        })
        ready.setOnClickListener(View.OnClickListener {


            val gson = Gson()
            val list:ArrayList<String> = ArrayList()
            for(i in 0 until animations.size){
                if(animations[i].active){
                    list.add(animations[i].text)
                }
            }
            val jsonString = gson.toJson(list)
            val editor = mSettings.edit()
            editor.putString(APP_PREFERENCES_GROUPS,jsonString)
            editor.apply()
            val intent = Intent(this, NavigationActivity::class.java)
            intent.putExtra("categories", jsonString)
            startActivity(intent)
        })



        init()




    }


    @RequiresApi(Build.VERSION_CODES.M)
    private fun init(){
        sports = findViewById(R.id.rel_sports)
        sportsCard = findViewById(R.id.card_sports)
        sportsAnim = findViewById(R.id.animation_sport)

        val animation1 = ClickAnimation(sports, sportsCard, sportsAnim, false, this, findViewById<TextView>(R.id.text_sports).text.toString())
        sports.setOnClickListener(View.OnClickListener {
            animation1.run()
        })

        olympiads = findViewById(R.id.rel_olympiads)
        olympiadsCard = findViewById(R.id.card_olympiads)
        olympiadsAnim = findViewById(R.id.animation_olympiads)

        val animation2 = ClickAnimation(olympiads, olympiadsCard, olympiadsAnim, false, this, findViewById<TextView>(R.id.text_olympiads).text.toString())
        olympiads.setOnClickListener(View.OnClickListener {
            animation2.run()
        })


        lyceum = findViewById(R.id.rel_lyceum)
        lyceumCard = findViewById(R.id.card_lyceum)
        lyceumAnim = findViewById(R.id.animation_lyceum_life)

        val animation3 = ClickAnimation(lyceum, lyceumCard, lyceumAnim, false, this, findViewById<TextView>(R.id.text_lyceum_life).text.toString())
        lyceum.setOnClickListener(View.OnClickListener {
            animation3.run()
        })

        language = findViewById(R.id.rel_languages)
        languageCard = findViewById(R.id.card_languages)
        languageAnim = findViewById(R.id.animation_languages)

        val animation4 = ClickAnimation(language, languageCard, languageAnim, false, this, findViewById<TextView>(R.id.text_languages).text.toString())
        language.setOnClickListener(View.OnClickListener {
            animation4.run()
        })
        animations.add(animation1)
        animations.add(animation2)
        animations.add(animation3)
        animations.add(animation4)



    }



    class ClickAnimation : Runnable{

        private var relativeLayout:RelativeLayout
        var text:String
        private var cardView: CardView
        private var animation:ImageView
        var active:Boolean = false
        private var activity: Context

        constructor(
            relativeLayout: RelativeLayout,
            cardView: CardView,
            animation: ImageView,
            active: Boolean,
            activity: Context,
            text: String
        ) {
            this.relativeLayout = relativeLayout
            this.cardView = cardView
            this.animation = animation
            this.active = active
            this.activity = activity
            this.text = text
        }


        @RequiresApi(Build.VERSION_CODES.M)
        override fun run() {

            relativeLayout.isClickable = false
            animation.animate().scaleX(1.4f).scaleY(1.4f).alpha(0f).setDuration(600)
                .withEndAction {
                    animation.scaleX = 1f
                    animation.scaleY = 1f
                    animation.alpha = 1f
                    relativeLayout.isClickable = true

                }.start()

            cardView.animate().alpha(0f).setDuration(300).withEndAction {
                if(!active) {
                    relativeLayout.setBackgroundResource(R.drawable.gradient_chosen)

                    cardView.animate().alpha(1f).setDuration(300).withEndAction {
                        animation.imageTintList = getColorStateList(activity, R.color.gradient_grey)
                        active = true
                    }.start()
                }else{
                    relativeLayout.setBackgroundResource(R.drawable.gradient_grey)
                    cardView.animate().alpha(1f).setDuration(300).withEndAction {
                        animation.imageTintList = getColorStateList(activity, R.color.blue_from)
                        active = false
                    }.start()
                }
            }.start()

        }
    }
}