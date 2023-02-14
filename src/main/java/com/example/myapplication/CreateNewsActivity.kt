package com.example.myapplication

import android.animation.ArgbEvaluator
import android.animation.ValueAnimator
import android.graphics.Color
import android.net.Uri
import android.os.Bundle
import android.util.Log
import android.view.View
import android.widget.EditText
import android.widget.ImageView
import android.widget.TextView
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import androidx.appcompat.content.res.AppCompatResources
import androidx.core.content.ContextCompat
import androidx.core.content.res.ResourcesCompat
import com.bumptech.glide.Glide
import com.crazylegend.core.modifiers.TitleTextModifier
import com.crazylegend.imagepicker.images.ImageModel
import com.crazylegend.imagepicker.pickers.SingleImagePicker
import com.example.myapplication.models.DateClass
import com.example.myapplication.models.News
import com.google.android.gms.tasks.OnSuccessListener
import com.google.firebase.database.DatabaseReference
import com.google.firebase.database.ktx.database
import com.google.firebase.ktx.Firebase
import com.google.firebase.storage.FirebaseStorage
import com.google.gson.Gson
import java.util.*
import kotlin.collections.ArrayList


class CreateNewsActivity : AppCompatActivity() {

    private lateinit var newsImage: ImageView
    private var loaded:Boolean = false
    private lateinit var imageModel: ImageModel
    private lateinit var create: TextView
    private lateinit var newsInfo: EditText
    private lateinit var newsName: EditText

    var tagList:ArrayList<TextView> = ArrayList()
    var activeTagsList:ArrayList<Boolean> = ArrayList()


    private val storage: FirebaseStorage = FirebaseStorage.getInstance()
    private var storageRef = storage.reference

    private val db = Firebase.database("https://iteam-e45a8-default-rtdb.europe-west1.firebasedatabase.app")
    private val newsRef: DatabaseReference = db.getReference("news")

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_create_news)

        create = findViewById(R.id.create_news)
        val eventAddImage:ImageView = findViewById(R.id.event_add_image)
        newsImage = findViewById(R.id.news_image)
        newsInfo = findViewById(R.id.news_info)
        newsName = findViewById(R.id.news_name)

        tagList.add(findViewById(R.id.lyceum_life))
        tagList.add(findViewById(R.id.sport))
        tagList.add(findViewById(R.id.olympiads))
        tagList.add(findViewById(R.id.languages))
        tagList.add(findViewById(R.id.another))

        for(i:Int in 0 until tagList.size){
            tagList[i].setOnClickListener(View.OnClickListener {
                when(tagList[i].tag){
                    "0"-> {
                        tagList[i].tag = "1"
                        tagList[i].animate().alpha(0f).setDuration(150).withEndAction {
                            tagList[i].backgroundTintList =
                                AppCompatResources.getColorStateList(
                                    this,
                                    R.color.purple
                                )
                            tagList[i].setTextColor(resources.getColor(R.color.white))
                            tagList[i].animate().alpha(1f).setDuration(150).start()
                        }.start()

                    }
                    "1"->{
                        tagList[i].tag = "0"
                        tagList[i].animate().alpha(0f).setDuration(150).withEndAction {
                            tagList[i].background = ContextCompat.getDrawable(this, R.drawable.element_filled_outlined_button)
                            tagList[i].backgroundTintList = null
                            tagList[i].setTextColor(resources.getColor(R.color.black))
                            tagList[i].animate().alpha(1f).setDuration(150).start()
                        }.start()

                    }
                }

            })
        }


        eventAddImage.setOnClickListener(View.OnClickListener {
            createPicker()
        })

        create.setOnClickListener(View.OnClickListener {
            var k = 0
            for(element in tagList){
                if(element.tag.equals("1")){
                    k+=1
                }
            }
            if(newsInfo.text.isNotEmpty() && k > 0) {
                loadNews()
                Log.d("1", "hey")
            }
        })



    }



    private fun loadNews() {

        val path = "news/" + newsInfo.text.toString()


        storageRef.child(path).putFile(imageModel.contentUri).addOnSuccessListener { taskSnapshot ->

            storageRef.child(path).downloadUrl.addOnSuccessListener(OnSuccessListener<Uri> { uri ->
                val url = uri.toString()
                loadInfo(url)
            })
        }
    }

    private fun loadInfo(url: String) {
        val calendar = Calendar.getInstance()


        val dateClass = DateClass(calendar.get(Calendar.DAY_OF_MONTH), calendar.get(Calendar.MONTH), calendar.get(Calendar.HOUR_OF_DAY), calendar.get(Calendar.MINUTE), calendar.get(Calendar.YEAR))
        val list:ArrayList<String> = ArrayList()
        for(element in tagList){
            if(element.tag.equals("1")){
                list.add(element.text.toString())
            }
        }

        val gson = Gson()
        val news = News(url, gson.toJson(list), newsInfo.text.toString(),newsName.text.toString(), dateClass)

        newsRef.push().setValue(news).addOnSuccessListener {
            Toast.makeText(this, "Новость создана!", Toast.LENGTH_SHORT).show()
            onBackPressed()
        }
    }

    private fun createPicker() {
        SingleImagePicker.showPicker(this,
            {
                loadingIndicatorTint = ContextCompat.getColor(this@CreateNewsActivity, R.color.black)
                titleTextModifier.apply {
                    textAlignment = TextView.TEXT_ALIGNMENT_VIEW_START
                    textStyle = TitleTextModifier.TextStyle.NORMAL
                    margin = 22 // use dp or sp this is only for demonstration purposes
                    textColor = Color.BLACK
                    textPadding = 5 // use dp or sp this is only for demonstration purposes
                    textSize = 20f  // use sp this is only for demonstration purposes
                    textString = "Выберите изображение"
                }
                noContentTextModifier.apply {
                    textAlignment = TextView.TEXT_ALIGNMENT_CENTER
                    textStyle = TitleTextModifier.TextStyle.BOLD_ITALIC
                    margin = 22 // use dp or sp this is only for demonstration purposes
                    textColor = Color.RED
                    textPadding = 5 // use dp or sp this is only for demonstration purposes
                    textSize = 20f  // use sp this is only for demonstration purposes
                }
                viewHolderPlaceholderModifier.apply {
                    resID = R.drawable.icon_arrow_up
                }
            }, ::loadImage)
    }

    private fun loadImage(imageModel: ImageModel) {
        loaded = true
        Glide.with(this)
            .load(imageModel.contentUri)
            .into(newsImage)
        Log.d("1", imageModel.contentUri.toString())
        this.imageModel = imageModel
    }


}