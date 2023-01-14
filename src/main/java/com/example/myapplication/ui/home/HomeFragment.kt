package com.example.myapplication.ui.home

import android.os.Bundle
import android.os.Handler
import android.os.Looper
import android.view.*
import android.view.GestureDetector.SimpleOnGestureListener
import android.view.ScaleGestureDetector.SimpleOnScaleGestureListener
import android.view.animation.ScaleAnimation
import android.widget.Adapter
import android.widget.RelativeLayout
import android.widget.ScrollView
import androidx.fragment.app.Fragment
import androidx.lifecycle.ViewModelProvider
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import androidx.viewpager2.widget.CompositePageTransformer
import androidx.viewpager2.widget.MarginPageTransformer
import androidx.viewpager2.widget.ViewPager2
import com.example.myapplication.R
import com.example.myapplication.adapters.EventSliderAdapter
import com.example.myapplication.adapters.RecyclerEventListAdapter
import com.example.myapplication.databinding.FragmentHomeBinding
import com.example.myapplication.models.Event
import java.lang.Math.abs


class HomeFragment : Fragment(){

    private var _binding: FragmentHomeBinding? = null

    // This property is only valid between onCreateView and
    // onDestroyView.
    private val binding get() = _binding!!



    private lateinit var recyclerView: RecyclerView
    private lateinit var eventList: ArrayList<Event>
    private lateinit var adapter: RecyclerEventListAdapter



    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View {
        val homeViewModel =
            ViewModelProvider(this)[HomeViewModel::class.java]

        _binding = FragmentHomeBinding.inflate(inflater, container, false)
        val root: View = binding.root
        recyclerView = root.findViewById(R.id.event_recycler)

        init()


        return root
    }


    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }

    private fun init(){
        eventList = ArrayList()
        eventList.add(Event("https://media.istockphoto.com/id/1181250359/photo/business-people.jpg?s=612x612&w=0&k=20&c=1DFEPJdcvlhFdQYp-hzj2CYXXRn-b6qYoPgyOptZsck=",
            "Встреча старост", "Завтра в 15:00 в библиотеке лицея состоится лекция директора института фундаментальной медицины и биологии, проректора по биомедицинскому направлению КФУ Андрея Павловича Киясова «Казанская медицинская школа и будущее медицины» \uD83E\uDDD1\uD83C\uDFFB\u200D\uD83D\uDD2C\n" +
                    "Говорят, лектор очень увлекательно читает! Так что лекция обещает быть очень интересной. Приходите! \uD83D\uDE09"))
        eventList.add(Event("https://static.vecteezy.com/system/resources/previews/003/560/630/original/karaoke-battle-neon-signs-style-text-free-vector.jpg", "English Karaoke Battle", "Hello IT-lyceum! \uD83D\uDE4B\uD83C\uDFFB\u200D♀️\n" +
                "\n" +
                "We are thrilled to announce New Year English Karaoke battle again! \uD83C\uDFA4\n" +
                "\n" +
                " Will you dare to accept this challenge?) \n" +
                "\n" +
                "Sign up (https://docs.google.com/spreadsheets/d/1fqcRoDApOx_oysI-9rM-YXTk43Nuyt2Dlg_fRYs51qM/edit?usp=sharing) and come, take your best friends and your favourite English song!\n" +
                "\n" +
                "Thursday, December, 15, 5 p.m."))
        eventList.add(Event("https://avatars.dzeninfra.ru/get-zen_doc/1895332/pub_5da31195c7e50c00af78e370_5da312620a451800b11ebcbd/scale_1200", "Библиовечер","Друзья! Сегодня в 19:00 в библиотеке состоится библиовечер \uD83C\uDF84\uD83D\uDCDA\n" +
                "\n" +
                "Вести вечер и проводить конкурсы будут лицеисты.\n" +
                "\n" +
                "Приглашаем 7, 8 классы и всех желающих!"))

        adapter = RecyclerEventListAdapter(eventList, requireContext())


        recyclerView.layoutManager = LinearLayoutManager(context)
        recyclerView.adapter = adapter

    }

}