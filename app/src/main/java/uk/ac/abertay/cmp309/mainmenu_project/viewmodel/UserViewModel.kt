package uk.ac.abertay.cmp309.mainmenu_project.viewmodel

import android.app.Application
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.LiveData
import androidx.lifecycle.viewModelScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import uk.ac.abertay.cmp309.mainmenu_project.data.StudentDatabase
import uk.ac.abertay.cmp309.mainmenu_project.fragments.model.Student
import uk.ac.abertay.cmp309.mainmenu_project.repository.UserRepository

class UserViewModel(application: Application): AndroidViewModel(application){

    val readAllData: LiveData<List<Student>>
    private val repository: UserRepository

    init {  //executed when user view model is called
        val userDao = StudentDatabase.getDatabase(application).userDao
        repository = UserRepository(userDao)
        readAllData = repository.readAllData
    }

    fun addUser(student: Student) { //bad practice to launch database in main threads
        viewModelScope.launch(Dispatchers.IO ) {  //Dispatches.IO runs code in background threads. Coroutine
            repository.addUser(student)
        }
    }


}