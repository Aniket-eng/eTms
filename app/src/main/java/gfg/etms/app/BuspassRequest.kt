package gfg.etms.app

import android.app.Application
import android.content.Context
import android.os.Bundle
import android.util.Log
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.MenuItem
import android.view.View
import android.view.ViewGroup
import android.widget.Button
import android.widget.PopupMenu
import android.widget.Toolbar
import androidx.appcompat.app.AppCompatActivity
import androidx.appcompat.widget.AppCompatButton
import androidx.cardview.widget.CardView
import androidx.lifecycle.LifecycleOwner
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider
import androidx.navigation.NavController
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import gfg.etms.app.Adapter.PassAdapter
import gfg.etms.app.Database.PassDatabase
import gfg.etms.app.Models.Pass
import gfg.etms.app.Models.PassViewModel
import gfg.etms.app.R
import gfg.etms.app.databinding.BuspassRequestBinding
import com.google.gson.Gson

private lateinit var recyclerView: RecyclerView
private lateinit var database: PassDatabase
private lateinit var viewModel: PassViewModel
private lateinit var adapter: PassAdapter
private lateinit var layoutManager: LinearLayoutManager
private lateinit var selectedPass : Pass
private lateinit var fragment: Fragment
private lateinit var toolbar: androidx.appcompat.widget.Toolbar
private lateinit var navController: NavController


class BuspassRequest : Fragment() , PassAdapter.PassItemClickListener, PopupMenu.OnMenuItemClickListener{

    lateinit var binding : BuspassRequestBinding
    lateinit var dataPasser: OnDataPass
    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
         binding = BuspassRequestBinding.inflate(inflater, container, false)

        fragment = ViewPass()

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
        viewModel = ViewModelProvider(requireActivity(),
            ViewModelProvider.AndroidViewModelFactory.getInstance(Application())).get(PassViewModel::class.java)
        database = PassDatabase.getDatabase(requireContext())
        adapter = PassAdapter(requireActivity(),this)
        // rest of my stuff
        binding.recycler.setHasFixedSize(true)
        binding.recycler.layoutManager = LinearLayoutManager(requireContext())
        binding.recycler.adapter = adapter
        adapter.notifyDataSetChanged()

        //viewModel = ViewModelProvider(this,
        //ViewModelProvider.AndroidViewModelFactory.getInstance(application = Application())).get(PassViewModel::class.java)
        val owner = activity as LifecycleOwner
        viewModel.allpass.observe(owner, Observer { list ->
            Log.d("data", "********************* TESTING ***********************")
            list?.let {
                adapter.updateList(it as ArrayList<Pass>)
            }
        })



    }
    interface OnDataPass {
        fun onDataPass(data: Pass)
    }

    override fun onItemClicked(pass: Pass) {

        val bundle = Bundle()
        val gson = Gson().toJson(pass)
        viewModel.setData(gson)
        if(gson!=null) {
            Log.d("data", "gson mai data  to hai!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!")
        }
        //toolbar = view?.findViewById<androidx.appcompat.widget.Toolbar>(R.id.toolbar)!!
        //(activity as AppCompatActivity?)?.setSupportActionBar(toolbar)
        (activity as AppCompatActivity?)?.supportActionBar?.setTitle(R.string.MainTitle)
        val transaction = requireActivity().supportFragmentManager.beginTransaction()
        fragment.arguments = bundle.apply {
            putString("finally",gson)

        }


       // val action : NavDirections = BuspassRequestDirections.actionBuspassRequestToViewPass22(bundle)
        //findNavController().navigate(R.id.action_buspassRequest_to_viewPass22,bundle)


        transaction.replace(R.id.mainContainer, fragment)
        transaction.commit()


    }

    override fun onLongItemClicked(pass: Pass, cardView: CardView) {
        selectedPass = pass
        popUpDisplay(cardView)

    }

    override fun onBtnClicked(pass: Pass) {
        var btn : AppCompatButton = view?.findViewById(R.id.list_delete) !!
        btn.setOnClickListener {
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

        if(item?.itemId == R.id.delete_pass){
            viewModel.deletePass(selectedPass)
            return true
        }
        return false
    }

    override fun onAttach(context: Context) {
        super.onAttach(context)
        dataPasser = context as OnDataPass
    }
    fun passData(data: Pass){
        Log.d("data", " Data to BHEJA BHAI NE !!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!")
        dataPasser.onDataPass(data)
    }




}