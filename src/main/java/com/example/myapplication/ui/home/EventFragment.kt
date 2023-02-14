package com.example.myapplication.ui.home

import android.os.Bundle
import android.util.Log
import android.view.*
import androidx.fragment.app.Fragment
import androidx.lifecycle.ViewModelProvider
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.example.myapplication.R
import com.example.myapplication.adapters.RecyclerEventListAdapter
import com.example.myapplication.databinding.FragmentEventBinding
import com.example.myapplication.models.Event


class EventFragment : Fragment(){

    private var _binding: FragmentEventBinding? = null

    // This property is only valid between onCreateView and
    // onDestroyView.
    private val binding get() = _binding!!



    private lateinit var recyclerView: RecyclerView
    private lateinit var eventList: ArrayList<Event>
    private lateinit var adapter: RecyclerEventListAdapter



    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View {
        val eventViewModel =
            ViewModelProvider(this)[EventViewModel::class.java]

        _binding = FragmentEventBinding.inflate(inflater, container, false)
        val root: View = binding.root
        recyclerView = root.findViewById(R.id.event_recycler)

        eventViewModel.getEvents().observe(viewLifecycleOwner){
            updateUI(it)
        }


        return root
    }

    private fun updateUI(events: ArrayList<Event>) {
        eventList = ArrayList()

        for(event in events){
            eventList.add(event)
            Log.d("1", event.imageUrl)
        }

        val sortedDatesDescending:List<Event> =
            eventList.sortedWith(compareBy<Event> { it.date.month }.
            thenBy { it.date.day }.thenBy { it.date.hours }.thenBy { it.date.minutes })

        adapter = RecyclerEventListAdapter(ArrayList<Event>(sortedDatesDescending), requireContext())


        recyclerView.layoutManager = LinearLayoutManager(context)
        recyclerView.adapter = adapter

    }


    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }


}