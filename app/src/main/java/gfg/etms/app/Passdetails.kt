package gfg.etms.app

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import gfg.etms.app.R
import gfg.etms.app.databinding.PassdetailsBinding
import gfg.etms.app.databinding.UltimatixBinding


class Passdetails : Fragment() {
    // TODO: Rename and change types of parameters
    private var _binding: PassdetailsBinding? = null
    private val binding get() = _binding!!
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        _binding = PassdetailsBinding.inflate(inflater, container, false)
        return binding.root
        // Inflate the layout for this fragment
        //return inflater.inflate(R.layout.passdetails, container, false)
    }

    override fun onStart() {
        super.onStart()
        binding.searchBar.setOnClickListener {

            val transaction = requireActivity().supportFragmentManager.beginTransaction()
            transaction.replace(R.id.mainContainer, BusPass())
            transaction.commit()

        }

    }


}