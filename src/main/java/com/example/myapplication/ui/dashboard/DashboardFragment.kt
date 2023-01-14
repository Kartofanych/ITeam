package com.example.myapplication.ui.dashboard

import android.app.ActionBar.LayoutParams
import android.content.Intent
import android.content.res.Resources
import android.net.Uri
import android.os.Bundle
import android.util.TypedValue
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.LinearLayout
import android.widget.RelativeLayout
import android.widget.TextView
import androidx.fragment.app.Fragment
import androidx.lifecycle.ViewModelProvider
import com.example.myapplication.R
import com.example.myapplication.databinding.FragmentDashboardBinding
import com.example.myapplication.models.Teacher
import com.google.android.material.bottomsheet.BottomSheetBehavior
import java.util.*


class DashboardFragment : Fragment() {

    private var _binding: FragmentDashboardBinding? = null

    lateinit var connectTeacher : TextView
    lateinit var bottomSheet : RelativeLayout

    // This property is only valid between onCreateView and
    // onDestroyView.
    private val binding get() = _binding!!

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View {
        val dashboardViewModel =
            ViewModelProvider(this).get(DashboardViewModel::class.java)
        _binding = FragmentDashboardBinding.inflate(inflater, container, false)
        val root: View = binding.root
        connectTeacher = root.findViewById(R.id.connect_teacher)
        bottomSheet = root.findViewById(R.id.bottom_sheet)
        setUpBottomSheet(root)

        dashboardViewModel.text.observe(viewLifecycleOwner) {
           // textView.text = it
        }
        return root
    }

    private fun setUpBottomSheet(root: View) {
        val backgroundShadow:RelativeLayout = root.findViewById(R.id.background_shadow)
        val bottomSheetBehavior = BottomSheetBehavior.from(bottomSheet)
        val closeTeacherCard:ImageView = bottomSheet.findViewById(R.id.back)
        bottomSheetBehavior.peekHeight = 0
        bottomSheetBehavior.isHideable = true
        bottomSheetBehavior.state = BottomSheetBehavior.STATE_HIDDEN
        bottomSheetBehavior.addBottomSheetCallback(object : BottomSheetBehavior.BottomSheetCallback() {

            override fun onStateChanged(bottomSheet: View, newState: Int) {
                // Do something for new state.
            }

            override fun onSlide(bottomSheet: View, slideOffset: Float) {
                backgroundShadow.alpha = 0.7f * slideOffset
            }
        })
        connectTeacher.setOnClickListener(View.OnClickListener{
            bottomSheetBehavior.state = BottomSheetBehavior.STATE_EXPANDED
        })

        closeTeacherCard.setOnClickListener(View.OnClickListener {
            bottomSheetBehavior.state = BottomSheetBehavior.STATE_HIDDEN
        })


        val dates = arrayOf(true, true, false, false, true, true)

        val teacher = Teacher("Светлана Николаевна", "Учитель русского языка", "gr", 89963351058,
            "Вы можете связаться со мной с 9 до 18 часов.", dates, 302)

        setUpTeacherSheet(teacher, bottomSheet)

    }

    private fun setUpTeacherSheet(teacher: Teacher, bottomSheet: RelativeLayout) {
        val messageMe: ImageView = bottomSheet.findViewById(R.id.message_me_throw)
        val name: TextView = bottomSheet.findViewById(R.id.person_name)
        val position: TextView = bottomSheet.findViewById(R.id.person_position)
        val info: TextView = bottomSheet.findViewById(R.id.person_information)
        val dateContainer:LinearLayout = bottomSheet.findViewById(R.id.date_container)

        messageMe.setOnClickListener(View.OnClickListener {
            if(teacher.telegramID!="") {
                val uri = Uri.Builder().scheme("http").authority("telegram.me").appendEncodedPath(
                    teacher.number.toString()
                ).build()
                startActivity(Intent(Intent.ACTION_VIEW, uri).setPackage("org.telegram.messenger"))
            }else{
                val uri = Uri.parse("smsto:+7${teacher.number}")
                val i = Intent(Intent.ACTION_SENDTO, uri)
                i.setPackage("com.whatsapp")
                startActivity(Intent.createChooser(i, ""))
            }
        })

        name.text = teacher.name
        position.text = teacher.title
        info.text = teacher.info


        val calendar:Calendar = Calendar.getInstance()

        var currentDay: Int = calendar.get(Calendar.DAY_OF_WEEK)
        var currentDate: Int = calendar.get(Calendar.DAY_OF_YEAR)
        val startDate: Int = currentDate-currentDay+1
        if(currentDay == 7){
            currentDay = 1
            currentDate += 1
        }

        for(i in 0..5){

            val params = LayoutParams(Int.MAX_VALUE, Int.MAX_VALUE)
            params.height = DpSize(95f)
            params.width = DpSize(45f)
            val work: Boolean = teacher.workDays[i]
            val v: View = if(work) {
                layoutInflater.inflate(R.layout.element_week_day_work, null)
            }else{
                layoutInflater.inflate(R.layout.element_week_day_chill, null)
            }
            val weekDay:TextView = v.findViewById(R.id.week_day)
            val weekDate:TextView = v.findViewById(R.id.week_date)


            val date:Calendar = getDate(startDate+i+1)

            when (i) {
                0 -> weekDay.text = "ПН"
                1 -> {
                    weekDay.text = "ВТ"
                    params.leftMargin = DpSize(10f)
                    params.rightMargin = DpSize(10f)
                }
                2 -> {
                    weekDay.text = "СР"
                    params.rightMargin = DpSize(10f)
                }
                3 -> weekDay.text = "ЧТ"
                4 -> {
                    weekDay.text = "ПТ"
                    params.leftMargin = DpSize(10f)
                    params.rightMargin = DpSize(10f)
                }
                5 -> weekDay.text = "СБ"
            }

            if(date.get(Calendar.DAY_OF_WEEK) == currentDay){
                val todayArrow:ImageView = v.findViewById(R.id.arrow_today)
                todayArrow.visibility = View.VISIBLE
            }

            weekDate.text = date.get(Calendar.DAY_OF_WEEK).toString()


            v.layoutParams = params
            dateContainer.addView(v)

        }


    }

    private fun DpSize(i: Float): Int {
        val r: Resources = resources
        val px = TypedValue.applyDimension(
            TypedValue.COMPLEX_UNIT_DIP,
            i,
            r.displayMetrics
        )
        return px.toInt()

    }

    private fun getDate(dayOfYear:Int): Calendar {
        val cal:Calendar = Calendar.getInstance()
        cal.set(Calendar.DAY_OF_YEAR, dayOfYear)
        return cal
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }
}