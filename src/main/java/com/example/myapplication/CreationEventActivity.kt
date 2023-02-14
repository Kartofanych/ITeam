package com.example.myapplication

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
import androidx.core.content.ContextCompat
import com.bumptech.glide.Glide
import com.crazylegend.core.modifiers.TitleTextModifier
import com.crazylegend.imagepicker.images.ImageModel
import com.crazylegend.imagepicker.pickers.SingleImagePicker
import com.example.myapplication.models.DateClass
import com.example.myapplication.models.Event
import com.github.florent37.singledateandtimepicker.SingleDateAndTimePicker
import com.google.android.gms.tasks.OnSuccessListener
import com.google.firebase.database.DatabaseReference
import com.google.firebase.database.ktx.database
import com.google.firebase.ktx.Firebase
import com.google.firebase.storage.FirebaseStorage


class CreationEventActivity : AppCompatActivity() {

    private lateinit var eventImage:ImageView
    private var loaded:Boolean = false
    private lateinit var imageModel:ImageModel
    private lateinit var create: TextView
    private lateinit var eventName: EditText
    private lateinit var eventInfo: EditText
    private lateinit var eventDate: SingleDateAndTimePicker

    private val storage:FirebaseStorage = FirebaseStorage.getInstance()
    private var storageRef = storage.reference

    private val db = Firebase.database("https://iteam-e45a8-default-rtdb.europe-west1.firebasedatabase.app")
    private val eventRef:DatabaseReference = db.getReference("events")

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_creation_event)

        create = findViewById(R.id.create_event)
        val eventAddImage:ImageView = findViewById(R.id.event_add_image)
        eventImage = findViewById(R.id.event_image)
        eventName = findViewById(R.id.event_name)
        eventInfo = findViewById(R.id.event_info)
        eventDate = findViewById(R.id.event_date)

        eventAddImage.setOnClickListener(View.OnClickListener {
            createPicker()
        })

        create.setOnClickListener(View.OnClickListener {
            if(eventName.text.isNotEmpty() && eventInfo.text.isNotEmpty() && loaded) {
                loadImage()
                Log.d("1", "hey")
            }
        })





    }

    private fun loadImage() {

        val path = "events/"+eventName.text.toString()


        storageRef.child(path).putFile(imageModel.contentUri).addOnSuccessListener { taskSnapshot ->

            storageRef.child(path).downloadUrl.addOnSuccessListener(OnSuccessListener<Uri> { uri ->
                val url = uri.toString()
                loadInfo(url)
            })
        }

    }

    private fun loadInfo(url: String) {
        val dateClass = DateClass(eventDate.date.date, eventDate.date.month, eventDate.date.hours, eventDate.date.minutes, eventDate.date.year)
        val event = Event(url, eventName.text.toString(), eventInfo.text.toString(), dateClass)
        eventRef.push().setValue(event).addOnSuccessListener {
            Toast.makeText(this, "Событие создано!", Toast.LENGTH_SHORT).show()
            onBackPressed()
        }
    }

    private fun createPicker() {
        SingleImagePicker.showPicker(this,
            {
                loadingIndicatorTint = ContextCompat.getColor(this@CreationEventActivity, R.color.black)
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
            }, this::loadImage
        )
    }

    private fun loadImage(imageModel: ImageModel) {
        loaded = true
        Glide.with(this)
            .load(imageModel.contentUri)
            .into(eventImage)
        Log.d("1", imageModel.contentUri.toString())
        this.imageModel = imageModel
    }
}