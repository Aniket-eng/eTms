package gfg.etms.app

import android.app.Application
import android.content.Intent
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.appcompat.app.AppCompatActivity
import androidx.lifecycle.ViewModelProvider
import gfg.etms.app.Database.PassDatabase
import gfg.etms.app.Models.PassViewModel
import gfg.etms.app.R
import gfg.etms.app.databinding.HomeBinding
import gfg.etms.app.databinding.PassdetailsBinding
import gfg.etms.app.databinding.UltimatixBinding

private lateinit var database: PassDatabase
private lateinit var viewModel: PassViewModel
private lateinit var fragment: Fragment
class Home : Fragment() {

    private var _binding: HomeBinding? = null
    private val binding get() = _binding!!
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        (activity as AppCompatActivity).supportActionBar?.hide()
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        _binding = HomeBinding.inflate(inflater, container, false)
        return binding.root
        //return inflater.inflate(R.layout.home, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        //viewModel = ViewModelProvider(requireActivity(),
         //   ViewModelProvider.AndroidViewModelFactory.getInstance(Application()))[PassViewModel::class.java]
        //database = PassDatabase.getDatabase(requireContext())
    }

    override fun onStart() {
        super.onStart()

        binding.btnBusService.setOnClickListener {
            activity.let {
                val intent = Intent(it, MainActivity::class.java)
                startActivity(intent)
            }

        }

    }

    override fun onResume() {
        super.onResume()
        (activity as AppCompatActivity).supportActionBar?.hide()
    }


}