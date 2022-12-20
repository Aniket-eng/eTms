package com.example.etms1

import android.app.Activity
import android.app.Application
import android.content.Intent
import android.os.Bundle
import android.text.Editable
import android.text.TextUtils
import android.text.TextWatcher
import android.util.Log
import android.util.Log.d
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.View.MeasureSpec
import android.view.ViewGroup
import android.view.WindowManager
import android.widget.Toast
import androidx.activity.result.contract.ActivityResultContracts
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.ViewCompat
import androidx.core.view.WindowInsetsCompat
import androidx.lifecycle.ViewModelProvider
import com.example.etms1.Adapter.PassAdapter
import com.example.etms1.Database.PassDatabase
import com.example.etms1.Models.Pass
import com.example.etms1.Models.PassViewModel
import kotlinx.android.synthetic.main.bus_pass.*
import java.time.LocalDate
import java.time.format.DateTimeFormatter
import java.time.temporal.TemporalAdjuster
import java.time.temporal.TemporalAdjusters
import java.time.temporal.TemporalAdjusters.lastDayOfMonth
import java.util.logging.Logger

private lateinit var pass: Pass
private lateinit var textWatcher: TextWatcher
private lateinit var viewModel: PassViewModel
private lateinit var database: PassDatabase
private lateinit var adapter: PassAdapter

class BusPass : Fragment() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        (activity as AppCompatActivity?)?.supportActionBar?.setDisplayHomeAsUpEnabled(true)
        viewModel = ViewModelProvider(this,
            ViewModelProvider.AndroidViewModelFactory.getInstance(Application())).get(PassViewModel::class.java)
        database = PassDatabase.getDatabase(requireContext())

        textWatcher = object : TextWatcher {
            override fun beforeTextChanged(s: CharSequence?, start: Int, count: Int, after: Int) {

            }

            override fun onTextChanged(s: CharSequence?, start: Int, before: Int, count: Int) {
                if(data_from.text.isEmpty()  &&  data_name.text.isEmpty()  &&  data_empId.text.isEmpty() &&  data_busStop.text.isEmpty() && data_route.text.isEmpty() ) {
                    Toast.makeText(activity,"Please Enter all Details",Toast.LENGTH_SHORT).show()
                }
                else{
                    data_submit.visibility = View.VISIBLE
                }
            }

            override fun afterTextChanged(s: Editable?) {

            }

        }
        val view  = activity?.window?.decorView ?: return
        ViewCompat.setOnApplyWindowInsetsListener(view) { v, insets ->
            val showingKeyboard = insets.isVisible(WindowInsetsCompat.Type.ime())
            if(showingKeyboard){
                tcs_logo.visibility = View.GONE
            }
            insets
        }


    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.bus_pass, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)


    }
    override fun onStart() {
        super.onStart()
        val drop = arrayOf<String>("18:00", "19:00")
        val pick = arrayOf<String>("6:45","7:45")
        pick_Time.minValue = 0
        pick_Time.maxValue = (pick.size - 1)
        pick_Time.displayedValues = pick
        drop_Time.minValue = 0
        drop_Time.maxValue = (drop.size - 1)
        drop_Time.displayedValues = drop
        data_from.addTextChangedListener(textWatcher)
        data_name.addTextChangedListener(textWatcher)
        data_empId.addTextChangedListener(textWatcher)
        data_busStop.addTextChangedListener(textWatcher)
        data_route.addTextChangedListener(textWatcher)
       val date = LocalDate.now()
        val month = LocalDate.now().month
        val year = LocalDate.now().year
        val firstday = LocalDate.of(year,month,1)
        val lastday = LocalDate.parse(firstday.toString()).with(TemporalAdjusters.lastDayOfMonth())

        val first_date = DateTimeFormatter.ofPattern("dd, MMM yyyy").format(firstday)
        val last_date = DateTimeFormatter.ofPattern("dd, MMM yyyy").format(lastday)
        data_startDate.text = first_date
        data_endDate.text = last_date
        var Pickup: String? = ""
        var Drop: String? = ""
        pick_Time.setOnValueChangedListener { picker, oldVal, newVal -> Pickup ="$newVal" }
        drop_Time.setOnValueChangedListener { picker, oldVal, newVal -> Drop ="$newVal"  }

      /*  val getContent = registerForActivityResult(ActivityResultContracts.StartActivityForResult()){ result ->

            if(result.resultCode == Activity.RESULT_OK){

                val pass = result.data?.getSerializableExtra("pass") as? Pass
                if(pass != null){
                    viewModel.insertPass(pass)
                }
            }
        }

       */


        data_submit.setOnClickListener {
            val emp_id = data_empId.text.toString()
            val from = data_from.text.toString()
            val to = data_to.text.toString()

            val bus_stop = data_busStop.text.toString()
            val route = data_route.text.toString()
            val start_date = data_startDate.text.toString()
            val end_date = data_endDate.text.toString()

            pass = Pass(
                null,emp_id,from,to,bus_stop,route,start_date,end_date
            )
            context?.let {
                viewModel.insertPass(pass)
                Toast.makeText(activity, "Details Added Sucessfully", Toast.LENGTH_SHORT).show()
            }
            /*if(pass!=null){
                Toast.makeText(activity, "Details Added Sucessfully", Toast.LENGTH_SHORT).show()


            }
            else{
                Toast.makeText(activity,"Please Enter all Details",Toast.LENGTH_SHORT).show()
            }

             */


        }

    }


}