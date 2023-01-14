package com.example.myapplication

import android.os.AsyncTask
import android.os.Bundle
import android.util.Log
import androidx.appcompat.app.AppCompatActivity
import com.example.myapplication.models.Post
import org.jsoup.Jsoup
import org.jsoup.select.Elements
import java.io.IOException


class chernovik : AppCompatActivity() {


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_chernovik)
        
    }

    internal class getWebInfo()
        : AsyncTask<Void, Void, MutableList<Post>>(){
        @Deprecated("Deprecated in Java")
        override fun doInBackground(vararg params: Void?): MutableList<Post> {
            val newsList = mutableListOf<Post>()

            try {

                val url = "https://vk.com/itlkpfu"
                val doc = Jsoup.connect(url).get()

                val textElements: Elements = doc
                    .select("div.wall_post_text")

                val imageUrlElements: Elements = doc.select("img.MediaGrid__imageElement")

                for(i in 0 until textElements.size){
                    val text:String = textElements.eq(i).text()
                    newsList[i].text = text
                }


                for(i in 0 until imageUrlElements.size){
                    val imageUrl:String = imageUrlElements.eq(i).attr("src")
                    newsList[i].imgUrl!!.add(imageUrl)
                    Log.d("newsListImages", imageUrl)
                }

            }catch (e: IOException){
                println(e.message)
            }
            return newsList

        }

    }



}