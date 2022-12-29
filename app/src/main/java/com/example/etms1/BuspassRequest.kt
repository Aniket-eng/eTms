package com.example.etms1

import android.app.Application
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.MenuItem
import android.view.View
import android.view.ViewGroup
import android.widget.AdapterView.OnItemClickListener
import android.widget.PopupMenu
import android.widget.Toast
import androidx.cardview.widget.CardView
import androidx.lifecycle.ViewModelProvider
import androidx.lifecycle.get
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.example.etms1.Adapter.PassAdapter
import com.example.etms1.Database.PassDatabase
import com.example.etms1.Models.Pass
import com.example.etms1.Models.PassViewModel
import com.example.etms1.databinding.BuspassRequestBinding
import kotlinx.android.synthetic.main.buspass_request.*
import kotlinx.android.synthetic.main.list_item1.*

private lateinit var recyclerView: RecyclerView
private lateinit var database: PassDatabase
private lateinit var viewModel: PassViewModel
private lateinit var adapter: PassAdapter
private lateinit var layoutManager: LinearLayoutManager
private lateinit var selectedPass : Pass

class BuspassRequest : Fragment() , PassAdapter.PassItemClickListener, PopupMenu.OnMenuItemClickListener{

    lateinit var binding : BuspassRequestBinding
    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
         binding = BuspassRequestBinding.inflate(inflater, container, false)



        // Inflate the layout for this fragment



        return binding.root
    }

    /*private fun initRecyclerView(view: View?) {

        val layoutManager = LinearLayoutManager(context)
        recyclerView = requireView().findViewById(R.id.recycler)
        recyclerView.layoutManager = layoutManager
        recyclerView.setHasFixedSize(true)
        adapter = activity?.let{PassAdapter(allpass)}!!
        recyclerView.adapter = adapter


    }

     */

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        viewModel = ViewModelProvider(this,
            ViewModelProvider.AndroidViewModelFactory.getInstance(Application()))[PassViewModel::class.java]
        database = PassDatabase.getDatabase(requireContext())

        adapter = PassAdapter(requireActivity(),this)
        // rest of my stuff
        binding.recycler.setHasFixedSize(true)
        binding.recycler.layoutManager = LinearLayoutManager(requireContext())
        binding.recycler.adapter = adapter
        adapter.notifyDataSetChanged()


        viewModel = ViewModelProvider(this,
        ViewModelProvider.AndroidViewModelFactory.getInstance(application = Application())).get(PassViewModel::class.java)

        viewModel.allpass.observe(viewLifecycleOwner,{list ->
            list?.let {
                adapter.updateList(it as ArrayList<Pass>)
            }
        })



    }

    override fun onItemClicked(pass: Pass) {
        val bundle = Bundle()
        val transaction = requireActivity().supportFragmentManager.beginTransaction()
        transaction.replace(R.id.container,ViewPass())
        transaction.commit()
    }

    override fun onLongItemClicked(pass: Pass, cardView: CardView) {
        selectedPass = pass
        popUpDisplay(cardView)

    }

    override fun onBtnClicked(pass: Pass) {
        list_delete.setOnClickListener {
            viewModel.deletePass(selectedPass)
        }
    }

    private fun popUpDisplay(cardView: CardView) {

        val popup = PopupMenu(requireContext(),cardView)
        popup.setOnMenuItemClickListener(this@BuspassRequest)
        popup.inflate(R.menu.pop_up_menu)
        popup.show()

    }

    override fun onMenuItemClick(item: MenuItem?): Boolean {

        if(item?.itemId == R.id.delete_pass ){
            viewModel.deletePass(selectedPass)
            return true
        }
        return false
    }




}