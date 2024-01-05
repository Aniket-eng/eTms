package gfg.etms.app

import android.app.Application
import android.app.DatePickerDialog
import android.os.Bundle
import android.text.Editable
import android.text.TextWatcher
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.AdapterView
import android.widget.ArrayAdapter
import android.widget.AutoCompleteTextView
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.ViewCompat
import androidx.core.view.WindowInsetsCompat
import androidx.lifecycle.ViewModelProvider
import gfg.etms.app.Adapter.PassAdapter
import gfg.etms.app.Database.PassDatabase
import gfg.etms.app.Models.Pass
import gfg.etms.app.Models.PassViewModel
import gfg.etms.app.R
import kotlinx.android.synthetic.main.bus_pass.*
import java.text.SimpleDateFormat
import java.time.LocalDate
import java.time.format.DateTimeFormatter
import java.time.temporal.TemporalAdjusters
import java.util.*

private lateinit var pass: Pass
private lateinit var textWatcher: TextWatcher
private lateinit var viewModel: PassViewModel
private lateinit var database: PassDatabase
private lateinit var adapter: PassAdapter
private lateinit var PickUp:String
private lateinit var Drop:String


class BusPass : Fragment() {
    var formatDate = SimpleDateFormat("d MMM, yyyy")
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

        //pick_Time.minValue = 0
        //pick_Time.maxValue = (pick.size - 1)
        //pick_Time.wrapSelectorWheel = true
        //pick_Time.displayedValues = pick

        //drop_Time.minValue = 0
        //drop_Time.maxValue = (drop.size - 1)
        //drop_Time.wrapSelectorWheel = true
        //drop_Time.displayedValues = drop


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

        val last_date = DateTimeFormatter.ofPattern("dd, MMM yyyy").format(lastday)


        //pick_Time.setOnValueChangedListener { picker, oldVal, newVal -> Pickup = pick[newVal] }
        //drop_Time.setOnValueChangedListener { picker, oldVal, newVal -> Drop = drop[newVal] }

        fun Int.ordinalAbbrev() =
            if (this % 100 / 10 == 1) "th"
            else when (this % 10) { 1 -> "st" 2 -> "nd" 3 -> "rd" else -> "th" }

        data_startDate.setOnClickListener {

            val getDate : Calendar = Calendar.getInstance()
            val datepicker = DatePickerDialog(requireContext(),android.R.style.Theme_Holo_Dialog_MinWidth,DatePickerDialog.OnDateSetListener { view, year, month, dayOfMonth ->

                val selectDate : Calendar = Calendar.getInstance()
                selectDate.set(Calendar.YEAR,year)
                selectDate.set(Calendar.MONTH,month)
                selectDate.set(Calendar.DAY_OF_MONTH,dayOfMonth)
                val date : String = formatDate.format(selectDate.time)
                val dateArray = date.split(" ").toTypedArray()
                val formatedDate = String.format(
                    "${dateArray[0]}${
                        dateArray[0].toInt().ordinalAbbrev()
                    } ${dateArray[1]} ${dateArray[2]}"
                )
                data_startDate.text = formatedDate

            },getDate.get(Calendar.YEAR), getDate.get(Calendar.MONTH),getDate.get(Calendar.DAY_OF_MONTH))
            datepicker.show()

        }

        data_endDate.setOnClickListener {

            val getDate : Calendar = Calendar.getInstance()
            val datepicker = DatePickerDialog(requireContext(),android.R.style.Theme_Holo_Dialog_MinWidth,DatePickerDialog.OnDateSetListener { view, year, month, dayOfMonth ->

                val selectDate : Calendar = Calendar.getInstance()
                selectDate.set(Calendar.YEAR,year)
                selectDate.set(Calendar.MONTH,month)
                selectDate.set(Calendar.DAY_OF_MONTH,dayOfMonth)
                val date : String = formatDate.format(selectDate.time)
                val dateArray = date.split(" ").toTypedArray()
                val formatedDate = String.format(
                    "${dateArray[0]}${
                        dateArray[0].toInt().ordinalAbbrev()
                    } ${dateArray[1]} ${dateArray[2]}"
                )
                data_endDate.text = formatedDate

            },getDate.get(Calendar.YEAR), getDate.get(Calendar.MONTH),getDate.get(Calendar.DAY_OF_MONTH))
            datepicker.show()

        }
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
            val name = data_name.text.toString()
            val bus_stop = data_busStop.text.toString()
            val route = data_route.text.toString()
            val start_date = data_startDate.text.toString()
            val end_date = data_endDate.text.toString()
            viewModel.resetAutoKey()
            pass = Pass(
                null,name,emp_id,from,to,PickUp,Drop,bus_stop,route,start_date,end_date
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

    override fun onResume() {
        super.onResume()

        val drop = listOf("18:00","19:00","17:45","18:45","17:40")
        val pick = listOf("06:45","07:45","07:00","06:00","07:30","06:30","07:40")

        val autoComplete1 : AutoCompleteTextView = pickup_Time
        val adepter = ArrayAdapter(requireContext(),R.layout.list_items1,pick)
        autoComplete1.setAdapter(adepter)
        autoComplete1.onItemClickListener = AdapterView.OnItemClickListener {
                adapterView, view, i, l ->

            PickUp = adapterView.getItemAtPosition(i) as String
            //Toast.makeText(requireContext(),"Item: $PickUp",Toast.LENGTH_SHORT).show()

        }

        val autoComplete2 : AutoCompleteTextView = drop_Time
        val adepter1 = ArrayAdapter(requireContext(),R.layout.list_items1,drop)

        autoComplete2.setAdapter(adepter1)
        autoComplete2.onItemClickListener = AdapterView.OnItemClickListener {
                adapterView, view, i, l ->

            Drop = adapterView.getItemAtPosition(i) as String
            //Toast.makeText(requireContext(),"Item: $Drop",Toast.LENGTH_SHORT).show()

        }


    }




}