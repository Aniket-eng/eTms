package com.example.etms1

import android.content.Intent
import android.os.Bundle
import android.view.MenuItem
import androidx.appcompat.app.ActionBarDrawerToggle
import androidx.appcompat.app.AppCompatActivity
import androidx.appcompat.app.AppCompatDelegate
import androidx.core.view.GravityCompat
import androidx.fragment.app.Fragment
import androidx.lifecycle.ViewModelProvider
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.example.etms1.Adapter.PassAdapter
import com.example.etms1.databinding.ActivityMainBinding
import com.example.etms1.repository.MainViewModel
import kotlinx.android.synthetic.main.activity_main.*
import kotlinx.android.synthetic.main.toolbar_main.*
import kotlinx.android.synthetic.main.nav_header_layout.*


class MainActivity : AppCompatActivity() {

    private lateinit var binding : ActivityMainBinding
    private lateinit var toggle: ActionBarDrawerToggle
    private lateinit var mainViewModel: MainViewModel
    private lateinit var recyclerView: RecyclerView
    private lateinit var adapter: PassAdapter

    var text: String? = ""

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        if (AppCompatDelegate.getDefaultNightMode() == AppCompatDelegate.MODE_NIGHT_YES) {
            setTheme(R.style.Theme_dark) //when dark mode is enabled, we use the dark theme
        } else {
            setTheme(R.style.Theme_App)  //default app theme
        }

        setContentView(R.layout.activity_main)

        initRecyclerView(this)
        mainViewModel = ViewModelProvider(this).get(MainViewModel::class.java)
        checkUserPresent()

        //val toolbar = findViewById<View>(R.id.toolbar) as Toolbar



        binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)
        binding.apply {
            toggle = ActionBarDrawerToggle(this@MainActivity, drawerlayout , R.string.OpenDrawer, R.string.CloseDrawer)
            setSupportActionBar(toolbar)
            supportActionBar?.setHomeButtonEnabled(true)
            supportActionBar?.setDisplayHomeAsUpEnabled(true)
            supportActionBar?.setHomeAsUpIndicator(R.drawable.ic_actionmenu)

            drawerlayout.addDrawerListener(toggle)
            toggle.syncState()



            navigationView.setNavigationItemSelectedListener {
                when (it.itemId) {
                    R.id.optHome -> {
                        drawerlayout.closeDrawer(GravityCompat.START)
                        val intent = Intent(applicationContext,MainActivity::class.java)
                        startActivity(intent)

                    }
                    R.id.optApply -> {
                        setSupportActionBar(toolbar)
                        supportActionBar?.setTitle(R.string.applybus)
                        replacefragment(ApplyBusPass())
                    }
                    R.id.optRequest -> {
                        setSupportActionBar(toolbar)
                        supportActionBar?.setTitle(R.string.request)
                        replacefragment(BuspassRequest())
                    }
                    R.id.optBack -> {
                            val intent = Intent(this@MainActivity,EntryPoint::class.java)
                            startActivity(intent)


                    }
                    R.id.optlogout -> {

                        mainViewModel.clearData()
                        val intent = Intent(this@MainActivity,EntryPoint::class.java)
                        startActivity(intent)
                    }

                }
                drawerlayout.closeDrawer(GravityCompat.START)
                true


            }

        }
    }



        /*appBarConfig = AppBarConfiguration(
            setOf(R.id.drawerlayout, R.id.toolbar,R.string.OpenDrawer,R.string.CloseDrawer)
        )
        */
        private fun initRecyclerView(view: MainActivity) {

            val layoutManager = LinearLayoutManager(this)
            recyclerView = view!!.findViewById(R.id.recycler)
            recyclerView.layoutManager = layoutManager
            recyclerView.setHasFixedSize(true)
            //adapter = activity?.let{PassAdapter(it,this)}!!
            adapter = PassAdapter(this,this)
            recyclerView.adapter = adapter


        }

    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        if (toggle.onOptionsItemSelected(item)){
            true
        }
        return super.onOptionsItemSelected(item)
    }

    private fun checkUserPresent(){


        mainViewModel.read.observe(this){

            username_text.text = it.toString()
        }
    }

    override fun onBackPressed() {
       if(drawerlayout.isDrawerOpen(GravityCompat.START)){

           drawerlayout.closeDrawer(GravityCompat.START)
       }
        else{
            super.onBackPressed()
       }
    }


    private fun replacefragment(fragment: Fragment) {

        val fragmentManager = supportFragmentManager
        val fragmentTransaction = fragmentManager.beginTransaction()

        fragmentTransaction.replace(R.id.container,fragment)
        fragmentTransaction.commit()
    }






    }


