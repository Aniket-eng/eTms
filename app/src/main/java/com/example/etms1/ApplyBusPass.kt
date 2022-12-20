package com.example.etms1

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import kotlinx.android.synthetic.main.apply_bus_pass.*
import kotlinx.android.synthetic.main.toolbar_main.*


class ApplyBusPass : Fragment() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        }


    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.apply_bus_pass, container, false)
    }

    override fun onStart() {
        super.onStart()

        btn_both.setOnClickListener {
            val transaction = requireActivity().supportFragmentManager.beginTransaction()
            transaction.replace(R.id.container, Passdetails())
            transaction.commit()
        }


    }


}