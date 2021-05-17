package uk.ac.abertay.cmp309.mainmenu_project.fragments

import android.app.AlertDialog
import android.content.ContentValues
import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.databinding.DataBindingUtil
import androidx.fragment.app.Fragment
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider
import androidx.recyclerview.widget.LinearLayoutManager
import uk.ac.abertay.cmp309.mainmenu_project.R
import uk.ac.abertay.cmp309.mainmenu_project.databinding.FragmentClassroomBinding
import uk.ac.abertay.cmp309.mainmenu_project.fragments.update.UpdateFragment
import uk.ac.abertay.cmp309.mainmenu_project.viewmodel.UserViewModel
import uk.ac.abertay.cmp309.mainmenu_project.viewmodel.UserViewModelDelete


class ClassroomFragment : Fragment() {

    private lateinit var mUserViewModel: UserViewModel
    private lateinit var mUserViewModelDelete: UserViewModelDelete
    private lateinit var binding: FragmentClassroomBinding

    override fun onCreateView(
            inflater: LayoutInflater, container: ViewGroup?,
            savedInstanceState: Bundle?
    ): View {
        // Inflate the layout for this fragment
        binding = DataBindingUtil.inflate(inflater, R.layout.fragment_classroom, container, false)  //data binding

        // Recycler View
        val adapter = ListAdapter{
            activity?.supportFragmentManager?.beginTransaction()?.replace(R.id.container_fragment, UpdateFragment(it))?.addToBackStack(null)?.commit()   //redirect to add fragment
            Toast.makeText(activity,"Update Database", Toast.LENGTH_SHORT).show()
        }

        //get the delete view model
        mUserViewModelDelete = ViewModelProvider(this).get(UserViewModelDelete::class.java)

        val recyclerView = binding.recyclerview
        recyclerView.adapter = adapter
        recyclerView.layoutManager = LinearLayoutManager(requireContext())

        // UserViewModel initialisation
        mUserViewModel = ViewModelProvider(this).get(UserViewModel::class.java)
        // list of students wrapped in live data object
        // sql query that gets data from database
        mUserViewModel.readAllData.observe(viewLifecycleOwner, Observer { student ->
            adapter.setData(student) // passing student inside setData function
            Log.d(ContentValues.TAG, "Intent started")

        })

        //opens the add fragment when clicked
        binding.AddStudentButton.setOnClickListener {
            activity?.supportFragmentManager?.beginTransaction()?.replace(R.id.container_fragment, AddFragment())?.addToBackStack(null)?.commit()   //redirect to add fragment
        }

        //call the delete all students function when the button is clicked
        binding.deleteall.setOnClickListener {
            deleteAllStudents()
        }

        return binding.root
    }


    private fun deleteAllStudents() {
        val builder = AlertDialog.Builder(requireContext())         //creates alert dialogue popup
        builder.setPositiveButton("Yes"){ _, _ ->
            mUserViewModelDelete.deleteAllStudents()
            Toast.makeText(
                    requireContext(),
                    "Successful",
                    Toast.LENGTH_SHORT).show()
        }
        builder.setNegativeButton("No"){_, _ -> }
        builder.setTitle("Delete")
        builder.setMessage("Are you sure you want to delete ")
        builder.create().show()
    }



}