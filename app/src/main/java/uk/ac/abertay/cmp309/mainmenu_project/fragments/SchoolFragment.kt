package uk.ac.abertay.cmp309.mainmenu_project.fragments

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import uk.ac.abertay.cmp309.mainmenu_project.R


class SchoolFragment : Fragment() {


    override fun onCreateView( inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_school, container, false)
    }


}