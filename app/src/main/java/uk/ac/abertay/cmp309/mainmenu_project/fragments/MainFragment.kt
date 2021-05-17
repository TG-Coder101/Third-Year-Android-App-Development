package uk.ac.abertay.cmp309.mainmenu_project.fragments

import android.content.Intent
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.databinding.DataBindingUtil
import androidx.fragment.app.Fragment
import uk.ac.abertay.cmp309.mainmenu_project.Activities.GoogleActivity
import uk.ac.abertay.cmp309.mainmenu_project.R
import uk.ac.abertay.cmp309.mainmenu_project.databinding.FragmentMainBinding

class MainFragment : Fragment() {

    lateinit var binding: FragmentMainBinding //set up data binding

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        // Inflate the layout for this fragment
        binding = DataBindingUtil.inflate(inflater, R.layout.fragment_main, container, false)  //data binding
        binding.goCalendar.setOnClickListener {
            Toast.makeText(requireContext(), "Google Calendar", Toast.LENGTH_SHORT).show()
            val intent = Intent(activity, GoogleActivity::class.java)
            startActivity(intent)   //start the intent
        }
        return binding.root
    }
}