package gfg.etms.app

import android.app.Application
import android.app.DatePickerDialog
import android.os.Bundle
import android.text.Editable
import android.text.TextWatcher
import android.util.Log
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
import gfg.etms.app.databinding.BusPassBinding
import gfg.etms.app.databinding.HomeBinding
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


    private var _binding: BusPassBinding? = null
    private val binding get() = _binding!!
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
                if(binding.dataFrom.text.isEmpty()  &&  binding.dataName.text.isEmpty()  &&  binding.dataEmpId.text.isEmpty() &&  binding.dataBusStop.text.isEmpty() && binding.dataRoute.text.isEmpty() ) {
                    Toast.makeText(activity,"Please Enter all Details",Toast.LENGTH_SHORT).show()
                }
                else{
                    binding.dataSubmit.visibility = View.VISIBLE
                }
            }

            override fun afterTextChanged(s: Editable?) {

            }

        }
        val view  = activity?.window?.decorView ?: return
        ViewCompat.setOnApplyWindowInsetsListener(view) { v, insets ->
            val showingKeyboard = insets.isVisible(WindowInsetsCompat.Type.ime())
            if(showingKeyboard){
                binding.tcsLogo.visibility = View.GONE
            }
            insets
        }


    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        _binding = BusPassBinding.inflate(inflater, container, false)
        return binding.root
        // Inflate the layout for this fragment
        //return inflater.inflate(R.layout.bus_pass, container, false)
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


        binding.apply {
            dataFrom.addTextChangedListener(textWatcher)
            dataName.addTextChangedListener(textWatcher)
            dataEmpId.addTextChangedListener(textWatcher)
            dataBusStop.addTextChangedListener(textWatcher)
            dataRoute.addTextChangedListener(textWatcher)
        }
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

        binding.dataStartDate.setOnClickListener {

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
                binding.dataStartDate.text = formatedDate

            },getDate.get(Calendar.YEAR), getDate.get(Calendar.MONTH),getDate.get(Calendar.DAY_OF_MONTH))
            datepicker.show()

        }

        binding.dataEndDate.setOnClickListener {

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
                binding.dataEndDate.text = formatedDate

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

        binding.apply {

            dataSubmit.setOnClickListener {
                val emp_id = dataEmpId.text.toString()
                val from = dataFrom.text.toString()
                val to = dataTo.text.toString()
                val name = dataName.text.toString()
                val bus_stop = dataBusStop.text.toString()
                val route = dataRoute.text.toString()
                val start_date = dataStartDate.text.toString()
                val end_date = dataEndDate.text.toString()
                viewModel.resetAutoKey()
                pass = Pass(
                    null,
                    name,
                    emp_id,
                    from,
                    to,
                    PickUp,
                    Drop,
                    bus_stop,
                    route,
                    start_date,
                    end_date
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

    override fun onResume() {
        super.onResume()
        var pickup : String = ""
        val generalMap = mapOf("06:45" to "17:45" , "07:45" to "18:45", "07:40" to "18:45")
        val drop = listOf("17:45","18:45")
        val pick = listOf("06:45","07:45","07:40")

        fun setDrop(pick : String) {

            val autoComplete2: AutoCompleteTextView = binding.dropTime
            if (generalMap.contains(pick)) {
                binding.dataSubmit.visibility = View.VISIBLE
                autoComplete2.setText(generalMap[PickUp])
                Drop = generalMap[PickUp].toString()
            } else {
                binding.dataSubmit.visibility = View.GONE
                Toast.makeText(activity, "Please Enter Correct Pickup Time", Toast.LENGTH_SHORT)
                    .show()

            }
        }


        val autoComplete1 : AutoCompleteTextView = binding.pickupTime
        val adepter = ArrayAdapter(requireContext(),R.layout.list_items1,pick.sorted())
        autoComplete1.setAdapter(adepter)
        autoComplete1.onItemClickListener = AdapterView.OnItemClickListener {
                adapterView, view, i, l ->

            PickUp = adapterView.getItemAtPosition(i) as String
            pickup = PickUp
            setDrop(pickup)
            Log.d("data", "$pickup")

        }




        /*
        val adepter1 = ArrayAdapter(requireContext(),R.layout.list_items1,drop.sorted())

        autoComplete2.setAdapter(adepter1)
        autoComplete2.onItemClickListener = AdapterView.OnItemClickListener {
                adapterView, view, i, l ->

            Drop = adapterView.getItemAtPosition(i) as String
            //Toast.makeText(requireContext(),"Item: $Drop",Toast.LENGTH_SHORT).show()

        }

         */


    }




}