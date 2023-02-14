package com.example.myapplication.ui.notifications

import android.content.Context
import android.os.Build
import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.annotation.RequiresApi
import androidx.fragment.app.Fragment
import androidx.lifecycle.ViewModelProvider
import androidx.recyclerview.widget.RecyclerView
import androidx.viewpager2.widget.CompositePageTransformer
import androidx.viewpager2.widget.MarginPageTransformer
import androidx.viewpager2.widget.ViewPager2
import com.example.myapplication.R
import com.example.myapplication.adapters.NewsSliderAdapter
import com.example.myapplication.databinding.FragmentNotificationsBinding
import com.example.myapplication.models.News
import com.google.gson.Gson
import com.google.gson.reflect.TypeToken
import java.lang.reflect.Type


class NotificationsFragment : Fragment() {

    private var _binding: FragmentNotificationsBinding? = null
    private val binding get() = _binding!!




    private lateinit var  viewPager2: ViewPager2
    private lateinit var newsList:ArrayList<News>
    private lateinit var adapter: NewsSliderAdapter
    private lateinit var groupsList:List<String>
    private lateinit var gson: Gson
    private lateinit var listOfMyClassObject: Type


    val APP_PREFERENCES = "mysettings"
    val APP_PREFERENCES_GROUPS = "zero"

    @RequiresApi(Build.VERSION_CODES.O)
    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View {
        val notificationsViewModel =
            ViewModelProvider(this).get(NotificationsViewModel::class.java)

        _binding = FragmentNotificationsBinding.inflate(inflater, container, false)
        val root: View = binding.root

        val mSettings = context?.getSharedPreferences(APP_PREFERENCES, Context.MODE_PRIVATE)

        gson = Gson()

        val string = mSettings!!.getString(APP_PREFERENCES_GROUPS, "zero")
        Log.d("1", string.toString())

        listOfMyClassObject = object : TypeToken<ArrayList<String>>() {}.type
        if(!string.equals("zero")) {
            groupsList = gson.fromJson(string, listOfMyClassObject)
        }else{
            groupsList = ArrayList()
        }

        init(root)
        setUpTransformer()



        notificationsViewModel.getNews().observe(viewLifecycleOwner) {
            updateUI(it)
        }
        return root
    }

    private fun updateUI(news: ArrayList<News>) {

        newsList = ArrayList()


        for(element in news){
            //val tags: ArrayList<String> = gson.fromJson(element.tags, listOfMyClassObject)
            for(group in groupsList){
                if(element.tags.contains(group)){
                    newsList.add(element)
                    break
                }
            }
        }

        val sortedDatesDescending:List<News> =
                newsList.sortedWith(compareBy<News> { it.date.month }.
            thenBy { it.date.day }.thenBy { it.date.hours }.thenBy { it.date.minutes }).reversed()

        adapter = NewsSliderAdapter(ArrayList<News>(sortedDatesDescending), viewPager2)

        viewPager2.adapter = adapter
    }


    private fun setUpTransformer(){
        val transformer = CompositePageTransformer()
        transformer.addTransformer(MarginPageTransformer(40))
        transformer.addTransformer { page, position ->
            val r = 1 - kotlin.math.abs(position)
            page.scaleY = 0.85f + r * 0.14f
        }

        viewPager2.setPageTransformer(transformer)
    }

    private fun init(root: View) {
        viewPager2 = root.findViewById(R.id.pager)
        newsList = ArrayList()


        adapter = NewsSliderAdapter(newsList, viewPager2)

        viewPager2.adapter = adapter
        viewPager2.offscreenPageLimit = 3
        viewPager2.clipToPadding = false
        viewPager2.clipChildren = false
        viewPager2.getChildAt(0).overScrollMode = RecyclerView.OVER_SCROLL_NEVER

    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }
}