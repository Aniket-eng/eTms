package com.example.etms1

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.cardview.widget.CardView
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.example.etms1.Adapter.PassAdapter
import com.example.etms1.Database.PassDatabase
import com.example.etms1.Models.Pass
import com.example.etms1.Models.PassViewModel

private lateinit var recyclerView: RecyclerView
private lateinit var database: PassDatabase
private lateinit var viewModel: PassViewModel
private lateinit var adapter: PassAdapter
private lateinit var selectedPass : Pass

class BuspassRequest : Fragment() , PassAdapter.PassItemClickListener{

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {

        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.buspass_request, container, false)
    }

    private fun initRecyclerView(view: View?) {

        val layoutManager = LinearLayoutManager(context)
        recyclerView = requireView().findViewById(R.id.recycler)
        recyclerView.layoutManager = layoutManager
        recyclerView.setHasFixedSize(true)
        //adapter = activity?.let{PassAdapter(it,this)}!!
        adapter = PassAdapter(requireContext(),this)
        recyclerView.adapter = adapter


    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        initRecyclerView(view)
        /*viewModel = ViewModelProvider(this,
            ViewModelProvider.AndroidViewModelFactory.getInstance(Application()))[PassViewModel::class.java]
        database = PassDatabase.getDatabase(requireContext())

         */
        val count = adapter.itemCount

    }

    override fun onItemClicked(pass: Pass) {

    }

    override fun onLongItemClicked(pass: Pass, cardView: CardView) {

    }


}