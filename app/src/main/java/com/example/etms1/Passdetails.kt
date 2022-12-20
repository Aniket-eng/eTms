package com.example.etms1

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.appcompat.app.AppCompatActivity
import androidx.fragment.app.FragmentManager
import kotlinx.android.synthetic.main.passdetails.*
import kotlinx.android.synthetic.main.toolbar_main.*


class Passdetails : Fragment() {
    // TODO: Rename and change types of parameters

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {

        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.passdetails, container, false)
    }

    override fun onStart() {
        super.onStart()
        search_bar.setOnClickListener {

            val transaction = requireActivity().supportFragmentManager.beginTransaction()
            transaction.replace(R.id.container,BusPass())
            transaction.commit()

        }

    }


}