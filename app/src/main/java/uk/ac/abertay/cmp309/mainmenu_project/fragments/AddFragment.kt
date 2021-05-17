package uk.ac.abertay.cmp309.mainmenu_project.fragments

import android.content.ContentValues
import android.os.Bundle
import android.text.Editable
import android.text.TextUtils
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.fragment.app.Fragment
import androidx.lifecycle.ViewModelProvider
import kotlinx.android.synthetic.main.fragment_add.*
import kotlinx.android.synthetic.main.fragment_add.view.*
import uk.ac.abertay.cmp309.mainmenu_project.R
import uk.ac.abertay.cmp309.mainmenu_project.fragments.model.Student
import uk.ac.abertay.cmp309.mainmenu_project.viewmodel.UserViewModel


class AddFragment : Fragment() {

    private lateinit var mUserViewModel: UserViewModel

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?,
                              savedInstanceState: Bundle?): View? {
        // Inflate the layout for this fragment
        val view = inflater.inflate(R.layout.fragment_add, container, false)

        //import the ViewModel that adds students to database
        mUserViewModel = ViewModelProvider(this).get(UserViewModel::class.java)

        //button listener
        view.add_btn.setOnClickListener{
            insertDataToDatabase()      //add to database
            Log.d(ContentValues.TAG, "Insert to Database")
        }
        return view
    }

    private fun insertDataToDatabase() {

        //set the variables
        val firstName = addFirstName_et.text.toString()
        val lastName = addLastName_et.text.toString()
        val yearGroup = addYearGroup_et.text.toString()
        val age = addAge_et.text

        if(inputCheck(firstName, lastName, yearGroup, age)) {
            //create User Object
            val user = Student(0, firstName, lastName, yearGroup, Integer.parseInt(age.toString()))
            //add to database
            mUserViewModel.addUser(user)
            Toast.makeText(requireContext(), "Success", Toast.LENGTH_SHORT).show()
            Log.d(ContentValues.TAG, "Success")
            //Navigate Back
            activity?.supportFragmentManager?.beginTransaction()?.replace(R.id.container_fragment, ClassroomFragment())?.addToBackStack(null)?.commit()   //redirect to add fragment
        }else{
            Toast.makeText(requireContext(), "Missing Fields", Toast.LENGTH_SHORT).show()
            Log.d(ContentValues.TAG, "No Fields")
        }

    }


    private fun inputCheck(firstName: String, lastName: String, yearGroup: String, age: Editable): Boolean {
        return !(TextUtils.isEmpty(firstName) && TextUtils.isEmpty(lastName) && TextUtils.isEmpty(yearGroup) && age.isEmpty()) //return false if fields are empty and return true is correct
    }

}