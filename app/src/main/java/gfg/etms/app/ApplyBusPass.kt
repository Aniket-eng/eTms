package gfg.etms.app

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import gfg.etms.app.R
import gfg.etms.app.databinding.ApplyBusPassBinding
import gfg.etms.app.databinding.HomeBinding



class ApplyBusPass : Fragment() {
    private var _binding: ApplyBusPassBinding? = null
    private val binding get() = _binding!!
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        }


    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        _binding = ApplyBusPassBinding.inflate(inflater, container, false)
        return binding.root
        // Inflate the layout for this fragment
        //return inflater.inflate(R.layout.apply_bus_pass, container, false)
    }

    override fun onStart() {
        super.onStart()

        binding.btnBoth.setOnClickListener {
            val transaction = requireActivity().supportFragmentManager.beginTransaction()
            transaction.replace(R.id.mainContainer, Passdetails())
            transaction.commit()
        }


    }


}