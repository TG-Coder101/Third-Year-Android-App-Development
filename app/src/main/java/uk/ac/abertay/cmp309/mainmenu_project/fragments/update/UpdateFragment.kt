package uk.ac.abertay.cmp309.mainmenu_project.fragments.update

import android.app.AlertDialog
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.databinding.DataBindingUtil
import androidx.fragment.app.Fragment
import androidx.lifecycle.ViewModelProvider
import uk.ac.abertay.cmp309.mainmenu_project.R
import uk.ac.abertay.cmp309.mainmenu_project.databinding.FragmentUpdateBinding
import uk.ac.abertay.cmp309.mainmenu_project.fragments.ClassroomFragment
import uk.ac.abertay.cmp309.mainmenu_project.fragments.model.Student
import uk.ac.abertay.cmp309.mainmenu_project.viewmodel.UserViewModelDelete
import uk.ac.abertay.cmp309.mainmenu_project.viewmodel.UserViewModelUpdate


class UpdateFragment(val student: Student) : Fragment() {   //pass in student in the class

    private lateinit var mUserViewModelUpdate: UserViewModelUpdate
    private lateinit var mUserViewModelDelete: UserViewModelDelete
    lateinit var binding: FragmentUpdateBinding

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?,
                              savedInstanceState: Bundle?): View {
        // Inflate the layout for this fragment
        binding = DataBindingUtil.inflate(inflater, R.layout.fragment_update, container, false)  //data binding


        mUserViewModelUpdate = ViewModelProvider(this).get(UserViewModelUpdate::class.java)
        mUserViewModelDelete = ViewModelProvider(this).get(UserViewModelDelete::class.java)

        //when update fragment opens, the fields are filled automatically with the variables used in the add fragment.
        binding.updateFirstNameEt.setText(student.firstName)
        binding.updateLastNameEt.setText(student.lastName)
        binding.updateYearGroupEt.setText(student.yearGroup.toString())
        binding.updateAgeEt.setText(student.age)

        //creates a button listener that updates the database
        binding.updateBtn.setOnClickListener {
            mUserViewModelUpdate.update(binding.updateFirstNameEt.text.toString(),
                    binding.updateLastNameEt.text.toString(),
                    binding.updateYearGroupEt.text.toString().toInt(),
                    binding.updateAgeEt.text.toString().toInt(), student )
            Toast.makeText(activity,"Success", Toast.LENGTH_SHORT).show()
            activity?.supportFragmentManager?.beginTransaction()?.replace(R.id.container_fragment, ClassroomFragment())?.addToBackStack(null)?.commit()   //redirect to add fragment
        }

        //send user back to classroom fragment when delete function is called
        binding.DeleteStudent.setOnClickListener {
            deleteStudent()
            activity?.supportFragmentManager?.beginTransaction()?.replace(R.id.container_fragment, ClassroomFragment())?.addToBackStack(null)?.commit()   //redirect to add fragment
        }

        //Add Menu
        setHasOptionsMenu(true)

        return binding.root
    }


    private fun deleteStudent() {
        val builder = AlertDialog.Builder(requireContext())         //creates an alert when called
        builder.setPositiveButton("Yes"){ _, _ ->              //if yes then delete the student and return a toast
            mUserViewModelDelete.deleteStudent(student)
            Toast.makeText(
                    requireContext(),
                    "Successfully removed: ${(student.firstName)}",
                    Toast.LENGTH_SHORT).show()
        }
        builder.setNegativeButton("No"){_, _ -> }
        builder.setTitle("Delete ${(student.firstName)}?")          //title of alert
        builder.setMessage("Are you sure you want to delete ${(student.firstName)}?")
        builder.create().show()
    }




}