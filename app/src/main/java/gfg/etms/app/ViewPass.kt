package gfg.etms.app

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.appcompat.app.AppCompatActivity
import androidx.fragment.app.Fragment
import gfg.etms.app.Models.Pass
import gfg.etms.app.databinding.ViewPassBinding
import com.google.gson.Gson
import kotlinx.android.synthetic.main.toolbar_main.*

private lateinit var viewPass: Pass

class ViewPass : Fragment() {

private lateinit var binding: ViewPassBinding


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        (activity as AppCompatActivity).setSupportActionBar(toolbar)
        (activity as AppCompatActivity).supportActionBar?.title = "asdadasdasd"

    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        binding = ViewPassBinding.inflate(inflater, container, false)
        val view = binding.root
        return view
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        setInitialData()
    }



    private fun setInitialData() {
        val jsonPass = arguments?.getString("finally")

        if(jsonPass != null){
            viewPass = Gson().fromJson(jsonPass, Pass::class.java)
             viewPass?.let {
                 binding.llStartPointText.setText(it.from)
                 binding.llLocationText.setText(it.to)
                 binding.tvPickTime.setText(it.Pickup)
                 binding.tvDropTime.setText(it.Drop)
                 binding.employeeNameText.setText(it.name)
                 binding.employeeIdText.setText(it.emp_id)
                 binding.busStopNameText.setText(it.bus_stop)
                 binding.startDateTextCon.setText(it.start_date)
                 binding.endDateTextCon.setText(it.end_date)
                 binding.busRouteNameText.setText("Route : "+it.route)
            }
        }
        else{
            binding.llStartPointText.text = "Data Nahi Ayaa......"
        }
    }



}