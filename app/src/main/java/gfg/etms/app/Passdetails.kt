package gfg.etms.app

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import gfg.etms.app.R
import kotlinx.android.synthetic.main.passdetails.*


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
            transaction.replace(R.id.mainContainer, BusPass())
            transaction.commit()

        }

    }


}