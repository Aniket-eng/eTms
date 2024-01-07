package gfg.etms.app




import android.app.Activity
import android.content.Context
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.view.inputmethod.InputMethodManager
import android.widget.Button
import android.widget.EditText
import androidx.appcompat.app.AppCompatActivity
import androidx.appcompat.app.AppCompatDelegate
import androidx.core.view.ViewCompat
import androidx.core.view.WindowInsetsCompat
import androidx.core.view.WindowInsetsControllerCompat
import androidx.fragment.app.Fragment
import androidx.lifecycle.ViewModelProvider
import gfg.etms.app.databinding.UltimatixBinding
import gfg.etms.app.repository.MainViewModel



class Ultimatix : Fragment() {
    lateinit var buttonOk: Button
    lateinit var username: EditText
    private lateinit var mainViewModel: MainViewModel
    private var _binding: UltimatixBinding? = null
    private val binding get() = _binding!!

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        (activity as AppCompatActivity).supportActionBar?.hide()
        //hideSystemBars()
        AppCompatDelegate.setDefaultNightMode(AppCompatDelegate.MODE_NIGHT_NO);
        mainViewModel = ViewModelProvider(this)[MainViewModel::class.java]

        /** mainViewModel.read.observe(this, { myName ->
        name_txt.text = myName
        })
         */


    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        _binding = UltimatixBinding.inflate(inflater, container, false)
        return binding.root
        /* Inflate the layout for this fragment
        try {
            var v = inflater.inflate(R.layout.ultimatix, container, false)
            return v
            // ... rest of body of onCreateView() ...
        } catch (e: Exception) {
            Log.e(TAG, "onCreateView", e)
            throw e
        }

         */

    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        //hideSystemBars()
    }
    override fun onStart() {
        super.onStart()

        binding.btnProceed.setOnClickListener {
            val myName= binding.userName.text.toString()
            mainViewModel.savaData(myName)
            hideKeyboard()
            val transaction = requireActivity().supportFragmentManager.beginTransaction()
            transaction.replace(R.id.Containerlog, Home())
            transaction.disallowAddToBackStack()
            transaction.commit()


        }
    }

    override fun onResume() {
        super.onResume()
        (activity as AppCompatActivity).actionBar?.hide()
    }

    fun Fragment.hideKeyboard() {
        view?.let { activity?.hideKeyboard(it) }
    }

    private fun Context.hideKeyboard(view: View) {
        val inputMethodManager = getSystemService(Activity.INPUT_METHOD_SERVICE) as InputMethodManager
        inputMethodManager.hideSoftInputFromWindow(view.windowToken, 0)
    }

    private fun hideSystemBars() {
        val windowInsetsController =
            ViewCompat.getWindowInsetsController(requireActivity().window.decorView) ?: return
        // Configure the behavior of the hidden system bars
        windowInsetsController.systemBarsBehavior =
            WindowInsetsControllerCompat.BEHAVIOR_SHOW_TRANSIENT_BARS_BY_SWIPE
        // Hide both the status bar and the navigation bar
        windowInsetsController.hide(WindowInsetsCompat.Type.systemBars())
    }


}