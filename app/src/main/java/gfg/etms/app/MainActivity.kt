package gfg.etms.app

import android.content.Intent
import android.os.Bundle
import android.util.Log
import android.view.MenuItem
import android.view.View
import androidx.appcompat.app.ActionBarDrawerToggle
import androidx.appcompat.app.AppCompatActivity
import androidx.appcompat.app.AppCompatDelegate
import androidx.core.view.GravityCompat
import androidx.fragment.app.Fragment
import androidx.lifecycle.ViewModelProvider
import gfg.etms.app.Models.Pass
import gfg.etms.app.databinding.ActivityMainBinding
import gfg.etms.app.repository.MainViewModel
import androidx.appcompat.widget.Toolbar;
import gfg.etms.app.Database.PassDatabase
import gfg.etms.app.Models.PassViewModel
import gfg.etms.app.R
import com.google.gson.Gson


class MainActivity : AppCompatActivity(), BuspassRequest.OnDataPass {

    private lateinit var toolbar: Toolbar
    private lateinit var binding: ActivityMainBinding
    private lateinit var toggle: ActionBarDrawerToggle
    private lateinit var mainViewModel: MainViewModel
    private lateinit var viewModel: PassViewModel
    private lateinit var database: PassDatabase
    private lateinit var selectedPass: Pass
    private lateinit var fragment: Fragment

    //var text: String = """{"Drop":"","Pickup":"","bus_stop":"","emp_id":"","end_date":"","from":"DEFAULT_PASS","id":14,"name":"","route":"","start_date":"","to":"TCS Olympus"}"""


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        /*if (AppCompatDelegate.getDefaultNightMode() == AppCompatDelegate.MODE_NIGHT_YES) {
            setTheme(R.style.Theme_dark) //when dark mode is enabled, we use the dark theme
        } else {
            setTheme(R.style.Theme_App)  //default app theme
        }

         */
        AppCompatDelegate.setDefaultNightMode(AppCompatDelegate.MODE_NIGHT_NO);
        setContentView(R.layout.activity_main)
        binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)
       // initRecyclerView(this)
        mainViewModel = ViewModelProvider(this)[MainViewModel::class.java]
        checkUserPresent()
        database = PassDatabase.getDatabase(this)
        viewModel = ViewModelProvider(this)[PassViewModel::class.java]
        fragment = ViewPass()



        //viewModel = ViewModelProvider(this,
          //  ViewModelProvider.AndroidViewModelFactory.getInstance(application = Application())).get(PassViewModel::class.java)
        //adapter = PassAdapter(applicationContext,this)



        //val toolbar = findViewById<View>(R.id.toolbar) as Toolbar




        binding.apply {
            toggle = ActionBarDrawerToggle(this@MainActivity, drawerlayout ,
                R.string.OpenDrawer,
                R.string.CloseDrawer
            )
            toolbar = findViewById<Toolbar>(R.id.toolbar)
            setSupportActionBar(toolbar)
            //supportActionBar?.setHomeButtonEnabled(true)
            //supportActionBar?.setDisplayHomeAsUpEnabled(true)
            //supportActionBar?.setHomeAsUpIndicator(R.drawable.ic_actionmenu)

            drawerlayout.addDrawerListener(toggle)
            toggle.syncState()



            navigationView.setNavigationItemSelectedListener {
                when (it.itemId) {
                    R.id.optHome -> {
                        drawerlayout.closeDrawer(GravityCompat.START)
                       //startActivity(intent)
                       // val intent = Intent(applicationContext,MainActivity::class.java)
                      //  startActivity(intent)
                        setSupportActionBar(toolbar)
                        supportActionBar?.setTitle(R.string.MainTitle)
                        val bundle  = Bundle()
                        val text = viewModel.getData().value.toString()
                        Log.d("sds","Home is called $text String is got from Viewmodel")
                        if (text != null) {
                            if (text.length > 4) {
                                fragment.arguments = bundle.apply {
                                    putString("finally", text)
                                }
                                replacefragment(fragment)
                            }
                            else {
                                defaultPass()
                            }
                        }


                    }
                    R.id.optApply -> {
                        //nullPass.visibility = View.GONE
                        setSupportActionBar(toolbar)
                        supportActionBar?.setTitle(R.string.applybus)
                        replacefragment(ApplyBusPass())
                    }
                    R.id.optRequest -> {
                        //nullPass.visibility = View.GONE
                        setSupportActionBar(toolbar)
                        supportActionBar?.setTitle(R.string.MainTitle)
                        replacefragment(BuspassRequest())
                    }
                    R.id.optBack -> {
                           // nullPass.visibility = View.GONE
                            val intent = Intent(this@MainActivity, EntryPoint::class.java)
                            startActivity(intent)


                    }
                    R.id.optlogout -> {

                        mainViewModel.clearData()
                        val intent = Intent(this@MainActivity, EntryPoint::class.java)
                        startActivity(intent)
                    }

                }
                drawerlayout.closeDrawer(GravityCompat.START)
                true


            }

        }

       viewModel.recentpass.observe(this) {
            Log.d("data", "********************* TESTING ***********************")
            it?.let {
                binding.homeContainer.mainContainer.nullPass.visibility = View.GONE
                setSupportActionBar(toolbar)
                supportActionBar?.setTitle(R.string.MainTitle)
                val textpass = Gson().toJson(it)
                val bundle  = Bundle()
                fragment.arguments = bundle.apply {
                    putString("finally",textpass)
                }
                if (textpass != null){
                    replacefragment(fragment)
                    selectedPass = Gson().fromJson(textpass, Pass::class.java)

                    selectedPass?.let {
                        binding.homeContainer.mainContainer.lStartPointText.setText(it.from)
                        binding.homeContainer.mainContainer.lLocationText.setText(it.to)
                        binding.homeContainer.mainContainer.tvPickTime.setText(it.Pickup)
                        binding.homeContainer.mainContainer.tvDropTime.setText(it.Drop)
                        binding.homeContainer.mainContainer.employeeNameText.setText(it.name)
                        binding.homeContainer.mainContainer.employeeIdText.setText(it.emp_id)
                        binding.homeContainer.mainContainer.busStopNameText.setText(it.bus_stop)
                        binding.homeContainer.mainContainer.startDateTextCon.setText(it.start_date)
                        binding.homeContainer.mainContainer.endDateTextCon.setText(it.end_date)
                        binding.homeContainer.mainContainer.busRouteNameText.setText("Route : " + it.route)
                    }
                }
                else{
                    binding.homeContainer.mainContainer.nullPass.visibility = View.VISIBLE
                }




            }
        }



        viewModel.getData()?.observe(this) {
            Log.d("data", "WWdata  to hai!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!")
            it?.let {
                //nullPass.visibility = View.GONE
                text = it
                selectedPass = Gson().fromJson(text, Pass::class.java)

                selectedPass?.let {
                    binding.homeContainer.mainContainer.lStartPointText.setText(it.from)
                    binding.homeContainer.mainContainer.lLocationText.setText(it.to)
                    binding.homeContainer.mainContainer.tvPickTime.setText(it.Pickup)
                    binding.homeContainer.mainContainer.tvDropTime.setText(it.Drop)
                    binding.homeContainer.mainContainer.employeeNameText.setText(it.name)
                    binding.homeContainer.mainContainer.employeeIdText.setText(it.emp_id)
                    binding.homeContainer.mainContainer.busStopNameText.setText(it.bus_stop)
                    binding.homeContainer.mainContainer.startDateTextCon.setText(it.start_date)
                    binding.homeContainer.mainContainer.endDateTextCon.setText(it.end_date)
                    binding.homeContainer.mainContainer.busRouteNameText.setText("Route : " + it.route)
                }
            }

        }



    }



        /*appBarConfig = AppBarConfiguration(
            setOf(R.id.drawerlayout, R.id.toolbar,R.string.OpenDrawer,R.string.CloseDrawer)
        )

        private fun initRecyclerView(view: MainActivity) {



            val layoutManager = LinearLayoutManager(this)
            recycler1.layoutManager = layoutManager
          //  recyclerView.setHasFixedSize(true)
            //adapter = activity?.let{PassAdapter(it,this)}!!
            adapter = PassAdapter(this)
            recycler1.adapter = adapter


        }

         */

    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        if (toggle.onOptionsItemSelected(item)){
            true
        }
        return super.onOptionsItemSelected(item)
    }

    private fun checkUserPresent(){


        mainViewModel.read.observe(this){

            binding.navigationView.menu.findItem(R.id.username_text)?.title = it.toString()
        }
    }

    override fun onBackPressed() {
       if(binding.drawerlayout.isDrawerOpen(GravityCompat.START)){

           binding.drawerlayout.closeDrawer(GravityCompat.START)
       }
        else{
            super.onBackPressed()
       }
    }
    private fun defaultPass() {
        viewModel.recentpass.observe(this) {
            Log.d("data", "********************* TESTING ***********************")
            it?.let {
                binding.homeContainer.mainContainer.nullPass.visibility = View.GONE
                setSupportActionBar(toolbar)
                supportActionBar?.setTitle(R.string.MainTitle)
                val textpass = Gson().toJson(it)
                val bundle  = Bundle()
                fragment.arguments = bundle.apply {
                    putString("finally",textpass)
                }
                if (textpass != null){
                    replacefragment(fragment)
                    selectedPass = Gson().fromJson(textpass, Pass::class.java)

                    selectedPass?.let {
                        binding.homeContainer.mainContainer.lStartPointText.setText(it.from)
                        binding.homeContainer.mainContainer.lLocationText.setText(it.to)
                        binding.homeContainer.mainContainer.tvPickTime.setText(it.Pickup)
                        binding.homeContainer.mainContainer.tvDropTime.setText(it.Drop)
                        binding.homeContainer.mainContainer.employeeNameText.setText(it.name)
                        binding.homeContainer.mainContainer.employeeIdText.setText(it.emp_id)
                        binding.homeContainer.mainContainer.busStopNameText.setText(it.bus_stop)
                        binding.homeContainer.mainContainer.startDateTextCon.setText(it.start_date)
                        binding.homeContainer.mainContainer.endDateTextCon.setText(it.end_date)
                        binding.homeContainer.mainContainer.busRouteNameText.setText("Route : " + it.route)
                    }
                }
                else{
                    binding.homeContainer.mainContainer.nullPass.visibility = View.VISIBLE
                }




            }
        }
    }

    private fun replacefragment(fragment: Fragment) {

        val fragmentManager = supportFragmentManager
        val fragmentTransaction = fragmentManager.beginTransaction()
        fragmentTransaction.replace(R.id.mainContainer,fragment)
        fragmentTransaction.commit()
    }

    override fun onDataPass(data: Pass) {
        selectedPass = data
        if(selectedPass!=null) {
            Log.d("data", "data  to milgaya ab kya karu!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!")
        }
    }

    override fun onResume() {
        super.onResume()
        //val position = adapter.pos
       // val userDao = database.getPassDao()
        //val users: Pass = userDao.loadUser(position)
       //selectedPass = viewModel.loadUsr(position)

    }






}


