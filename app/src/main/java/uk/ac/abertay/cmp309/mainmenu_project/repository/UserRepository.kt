package uk.ac.abertay.cmp309.mainmenu_project.repository

import androidx.lifecycle.LiveData
import uk.ac.abertay.cmp309.mainmenu_project.data.UserDao
import uk.ac.abertay.cmp309.mainmenu_project.fragments.model.Student

class UserRepository(private val userDao: UserDao) {

    val readAllData: LiveData<List<Student>> = userDao.readAllData()

    suspend fun addUser(student: Student) {
        userDao.addUser(student)
    }

    suspend fun updateUser(student: Student) {
        userDao.update(student)
    }

    suspend fun get(id: Long) : Student? {
       return userDao.get(id)
    }

    suspend fun deleteStudent(student: Student){
        userDao.deleteStudent(student)
    }

    suspend fun deleteAllStudents(){
        userDao.deleteAllStudents()
    }


}