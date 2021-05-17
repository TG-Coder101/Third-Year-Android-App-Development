package uk.ac.abertay.cmp309.mainmenu_project.viewmodel

import android.app.Application
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.viewModelScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import uk.ac.abertay.cmp309.mainmenu_project.data.StudentDatabase
import uk.ac.abertay.cmp309.mainmenu_project.fragments.model.Student
import uk.ac.abertay.cmp309.mainmenu_project.repository.UserRepository

class UserViewModelDelete(application: Application) : AndroidViewModel(application) {

    private val repository: UserRepository

    init {  //executed when user view model is called
        val userDao = StudentDatabase.getDatabase(application).userDao
        repository = UserRepository(userDao)
    }

    //delete one student
    fun deleteStudent(student: Student) {
        viewModelScope.launch(Dispatchers.IO) {
            repository.deleteStudent(student)
        }
    }

    //delete all students
    fun deleteAllStudents() {
        viewModelScope.launch(Dispatchers.IO) {
            repository.deleteAllStudents()
        }
    }



}