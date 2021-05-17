package uk.ac.abertay.cmp309.mainmenu_project.data

import androidx.lifecycle.LiveData
import androidx.room.*
import uk.ac.abertay.cmp309.mainmenu_project.fragments.model.Student


//our data access object which will contain all methods to access database

@Dao
interface UserDao {

    //all using suspend because they are using kotlin coroutines

    @Insert(onConflict = OnConflictStrategy.IGNORE)
    suspend fun addUser(student: Student)

    @Query( "SELECT * From user_table ORDER BY id ASC") //select all from the user table and order by ascending order
    fun readAllData(): LiveData<List<Student>>  //returns list of students wrapped in live data

    @Query( "SELECT * From user_table Where id = :id") //finds student based on id
    fun get(id: Long) : Student?


    @Update(onConflict = OnConflictStrategy.IGNORE)
    suspend fun update(student: Student) //suspend because of coroutines

    @Delete
    suspend fun deleteStudent(student: Student)

    @Query("Delete From user_table")
    suspend fun deleteAllStudents()

}
//Reference https://www.youtube.com/watch?v=lwAvI3WDXBY
