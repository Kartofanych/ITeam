package com.example.myapplication.ui.notifications

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.lifecycle.ViewModelProvider
import com.example.myapplication.databinding.FragmentNotificationsBinding


class NotificationsFragment : Fragment() {

    private var _binding: FragmentNotificationsBinding? = null
    private val binding get() = _binding!!






    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View {
        val notificationsViewModel =
            ViewModelProvider(this).get(NotificationsViewModel::class.java)

        _binding = FragmentNotificationsBinding.inflate(inflater, container, false)
        val root: View = binding.root


       // init()




        notificationsViewModel.text.observe(viewLifecycleOwner) {
            //textView.text = it
        }
        return root
    }



    private fun init() {

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