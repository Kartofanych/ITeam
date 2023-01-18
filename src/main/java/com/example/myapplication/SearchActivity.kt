package com.example.myapplication

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.text.Editable
import android.text.TextWatcher
import android.view.View
import android.widget.EditText
import android.widget.TextView
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.example.myapplication.adapters.RecyclerSearchClubListAdapter
import com.example.myapplication.adapters.RecyclerSearchTeachersListAdapter
import com.example.myapplication.models.Club
import com.example.myapplication.models.Teacher

class SearchActivity : AppCompatActivity() {

    private lateinit var searchField: EditText
    private lateinit var recyclerSearchTeacher:RecyclerView
    private lateinit var recyclerSearchClub:RecyclerView

    private lateinit var teachersText:TextView
    private lateinit var clubsText:TextView

    private val teacherList = ArrayList<Teacher>()
    private val clubList = ArrayList<Club>()


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_search)

        init()


        searchField.addTextChangedListener(object : TextWatcher{
            override fun beforeTextChanged(s: CharSequence?, start: Int, count: Int, after: Int) {

            }

            override fun onTextChanged(s: CharSequence?, start: Int, before: Int, count: Int) {

            }

            override fun afterTextChanged(s: Editable?) {
                search(s.toString())
            }

        })

    }

    private fun search(s:String) {
        val searchTeacherList = ArrayList<Teacher>()
        val searchClubList = ArrayList<Club>()
        if(s.isNotEmpty()) {
            for (teacher in teacherList) {
                if (teacher.searchString.contains(s, true)) {
                    searchTeacherList.add(teacher)
                }
            }

            for (club in clubList) {
                if (club.searchString.contains(s, true)) {
                    searchClubList.add(club)
                }
            }

            teachersText.visibility = View.VISIBLE
            clubsText.visibility = View.VISIBLE

        }else{
            teachersText.visibility = View.GONE
            clubsText.visibility = View.GONE
        }

        if(searchTeacherList.size == 0){
            teachersText.visibility = View.GONE
        }


        if(searchClubList.size == 0){
            clubsText.visibility = View.GONE
        }


        val teachersAdapter = RecyclerSearchTeachersListAdapter(searchTeacherList, this)
        recyclerSearchTeacher.adapter = teachersAdapter


        val clubAdapter = RecyclerSearchClubListAdapter(searchClubList, this)
        recyclerSearchClub.adapter = clubAdapter


    }

    private fun init(){
        searchField = findViewById(R.id.search_field)
        searchField.requestFocus()
        recyclerSearchTeacher = findViewById(R.id.search_teachers_recycler)
        recyclerSearchClub = findViewById(R.id.search_clubs_recycler)
        teachersText = findViewById(R.id.text_personal)
        clubsText = findViewById(R.id.text_clubs)
        teachersText.visibility = View.GONE
        clubsText.visibility = View.GONE
        recyclerSearchTeacher.layoutManager = LinearLayoutManager(this)
        recyclerSearchClub.layoutManager = LinearLayoutManager(this)



        val dates = arrayOf(true, true, false, false, true, true)
        val teacher = Teacher(
            "Светлана Николаевна", "Учитель русского языка", "gr", 89963351058,
            "Вы можете связаться со мной с 9 до 18 часов.", dates, 302, "русский язык", "https://cdn.profi.ru/s3/pfiles/PanischevaSN/b79e3d2cb5172af1eb6e1218d95d1cb8.jpg-a34-135.jpg"
        )
        val teacher1 = Teacher(
            "Марат Гумерович", "Учитель информатики", "gr", 89963351058,
            "Вы можете связаться со мной с 9 до 18 часов.", dates, 113, "информатика", "https://sun9-84.userapi.com/impf/c837232/v837232247/45877/3PTpu_ucOJ4.jpg?size=1620x2160&quality=96&sign=20deccc6574313c23252cde761820fd7&type=album"
        )

        teacherList.add(teacher)
        teacherList.add(teacher1)


        val club = Club("Волейбол", teacher, "спорт", 105)
        val club1 = Club("Олимпиадное программирование", teacher1, "олимпиады", 325)

        clubList.add(club)
        clubList.add(club1)


    }
}