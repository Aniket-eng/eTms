package com.example.etms1

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.Toast
import androidx.core.view.ViewCompat
import androidx.core.view.WindowInsetsCompat
import androidx.core.view.WindowInsetsControllerCompat
import androidx.fragment.app.Fragment
import androidx.lifecycle.ViewModelProvider
import com.example.etms1.repository.MainViewModel

private lateinit var mainViewModel: MainViewModel
var text: String? = ""


class EntryPoint : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        hideSystemBars()
        setContentView(R.layout.entry_point)

        mainViewModel = ViewModelProvider(this).get(MainViewModel::class.java)


        if(checkUserPresent().isNullOrEmpty()){
            replacefragment(Ultimatix())
        }
        else{
            Toast.makeText(this@EntryPoint, checkUserPresent(), Toast.LENGTH_SHORT).show()
            replacefragment(Home())
        }

    }

    private fun replacefragment(fragment: Fragment) {

        val fragmentManager = supportFragmentManager
        val fragmentTransaction = fragmentManager.beginTransaction()

        fragmentTransaction.replace(R.id.entrycontainer,fragment)
        fragmentTransaction.commit()
    }

    private fun checkUserPresent(): String? {


        mainViewModel.read.observe(this){
            text = it.toString()

        }
        return text

    }

    private fun hideSystemBars() {
        val windowInsetsController =
            ViewCompat.getWindowInsetsController(window.decorView) ?: return
        // Configure the behavior of the hidden system bars
        windowInsetsController.systemBarsBehavior =
            WindowInsetsControllerCompat.BEHAVIOR_SHOW_TRANSIENT_BARS_BY_SWIPE
        // Hide both the status bar and the navigation bar
        windowInsetsController.hide(WindowInsetsCompat.Type.systemBars())
    }

}