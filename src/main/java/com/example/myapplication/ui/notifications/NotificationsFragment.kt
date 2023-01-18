package com.example.myapplication.ui.notifications

import android.os.Build
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.annotation.RequiresApi
import androidx.fragment.app.Fragment
import androidx.lifecycle.ViewModelProvider
import androidx.recyclerview.widget.RecyclerView
import androidx.viewpager2.widget.ViewPager2
import com.example.myapplication.R
import com.example.myapplication.adapters.ViewPagerFutureEventsAdapter
import com.example.myapplication.databinding.FragmentNotificationsBinding
import com.example.myapplication.models.DateClass
import com.example.myapplication.models.Event
import java.time.Month


class NotificationsFragment : Fragment() {

    private var _binding: FragmentNotificationsBinding? = null
    private val binding get() = _binding!!




    private lateinit var carousel:ViewPager2
    private lateinit var eventList: ArrayList<Event>


    @RequiresApi(Build.VERSION_CODES.O)
    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View {
        val notificationsViewModel =
            ViewModelProvider(this).get(NotificationsViewModel::class.java)

        _binding = FragmentNotificationsBinding.inflate(inflater, container, false)
        val root: View = binding.root


        init(root)




        notificationsViewModel.text.observe(viewLifecycleOwner) {
            //textView.text = it
        }
        return root
    }



    @RequiresApi(Build.VERSION_CODES.O)
    private fun init(root: View) {
        carousel = root.findViewById(R.id.carousel_future_events)
        carousel.apply {
            clipChildren = false  // No clipping the left and right items
            clipToPadding = false  // Show the viewpager in full width without clipping the padding
            offscreenPageLimit = 3  // Render the left and right items
            (getChildAt(0) as RecyclerView).overScrollMode =
                RecyclerView.OVER_SCROLL_NEVER // Remove the scroll effect
        }

        eventList = ArrayList<Event>()
        eventList.add(
            Event(
            "https://media.istockphoto.com/id/1181250359/photo/business-people.jpg?s=612x612&w=0&k=20&c=1DFEPJdcvlhFdQYp-hzj2CYXXRn-b6qYoPgyOptZsck=",
            "Встреча старост",
            "Завтра в 15:00 в библиотеке лицея состоится лекция директора института фундаментальной медицины и биологии, проректора по биомедицинскому направлению КФУ Андрея Павловича Киясова «Казанская медицинская школа и будущее медицины» \uD83E\uDDD1\uD83C\uDFFB\u200D\uD83D\uDD2C\n" +
                    "Говорят, лектор очень увлекательно читает! Так что лекция обещает быть очень интересной. Приходите! \uD83D\uDE09",
            DateClass(15, "Январь", "15:30")
        )
        )
        eventList.add(
            Event(
            "https://static.vecteezy.com/system/resources/previews/003/560/630/original/karaoke-battle-neon-signs-style-text-free-vector.jpg",
            "English Karaoke Battle",
            "Hello IT-lyceum! \uD83D\uDE4B\uD83C\uDFFB\u200D♀️\n" +
                    "\n" +
                    "We are thrilled to announce New Year English Karaoke battle again! \uD83C\uDFA4\n" +
                    "\n" +
                    " Will you dare to accept this challenge?) \n" +
                    "\n" +
                    "Sign up (https://docs.google.com/spreadsheets/d/1fqcRoDApOx_oysI-9rM-YXTk43Nuyt2Dlg_fRYs51qM/edit?usp=sharing) and come, take your best friends and your favourite English song!\n" +
                    "\n" +
                    "Thursday, December, 15, 5 p.m.",
            DateClass(15, "Январь", "15:30")
        )
        )
        eventList.add(
            Event(
            "https://avatars.dzeninfra.ru/get-zen_doc/1895332/pub_5da31195c7e50c00af78e370_5da312620a451800b11ebcbd/scale_1200",
            "Библиовечер",
            "Друзья! Сегодня в 19:00 в библиотеке состоится библиовечер \uD83C\uDF84\uD83D\uDCDA\n" +
                    "\n" +
                    "Вести вечер и проводить конкурсы будут лицеисты.\n" +
                    "\n" +
                    "Приглашаем 7, 8 классы и всех желающих!",
            DateClass(15, "Январь", "15:30")
        )
        )

        carousel.adapter = ViewPagerFutureEventsAdapter(eventList, requireContext())
    }














    /*
    private var loader: AsyncTask<Void, Void, MutableList<Post>>? = null

    loader = getWebInfo(context, mRecyclerView)
        loader!!.execute()

    internal class getWebInfo(var con: Context?, mRecyclerView: RecyclerView)
        : AsyncTask<Void, Void, MutableList<Post>>(){


        var recyclerView:RecyclerView = mRecyclerView


        override fun doInBackground(vararg params: Void?): MutableList<Post> {
            val posts = mutableListOf<Post>()

            try {
                //val webClient = WebClient()
                //val myPage: HtmlPage = webClient.getPage(url)


                val url = "https://vk.com/itlkpfu"

                val doc = Jsoup.connect(url).get()

                val textElements: Elements = doc
                    .select("div.wall_post_text")
                val imageUrlBlocks: Elements = doc.select("div.MediaGridContainerWeb--post")
                val dateElements: Elements = doc.select("span.rel_date")


                for(i in 0 until textElements.size){
                    val text:String = textElements.eq(i).text()
                    val date:String = dateElements.eq(i).text()
                    posts.add(Post(text, date, mutableListOf<String>()))
                }

                for(i in 0 until imageUrlBlocks.size){
                    val urlList: MutableList<String> = mutableListOf<String>()
                    var imageUrlElements: Elements = imageUrlBlocks.eq(i).select("div.MediaGrid__interactive")
                        for (j in 0 until imageUrlElements.size) {
                            val imageUrl: String =
                                imageUrlElements.html()
                            //attr("data-options")

                            urlList.add(imageUrl)
                            //Log.d(j.toString(), imageUrl)
                        }
                    posts[i].imgUrl = urlList
                }


                var imageUrlElements: Elements = doc.select("div.MediaGrid__thumb")
                Log.d("1", imageUrlElements.html())

            }catch (e: IOException){
                println(e.message)
            }


            return posts

        }
        override fun onPostExecute(result: MutableList<Post>){
            super.onPostExecute(result)
            val mViewPagerAdapter = con?.let { RecyclerPostListAdapter(result, it) }
            recyclerView.adapter = mViewPagerAdapter

        }

    }*/




    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }
}