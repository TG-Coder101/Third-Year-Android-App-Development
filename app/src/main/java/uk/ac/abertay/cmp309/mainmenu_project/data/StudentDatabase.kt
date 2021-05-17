package uk.ac.abertay.cmp309.mainmenu_project.data

import android.content.Context
import androidx.room.Database
import androidx.room.Room
import androidx.room.RoomDatabase
import uk.ac.abertay.cmp309.mainmenu_project.fragments.model.Student

@Database(entities = [Student::class], version = 1, exportSchema = false)
abstract class StudentDatabase: RoomDatabase() {

    abstract val userDao: UserDao

    companion object {
        @Volatile                                                                                   //writes made visible to other threads
        private var INSTANCE: StudentDatabase? = null                                               //database will only have one instance of its class

        fun getDatabase(context: Context): StudentDatabase {
            val tempInstance = INSTANCE                                                             //check if instance is not 0, if instance already exists return existing instance
            if(tempInstance != null) {                                                              //if no instance, create new instance
                return tempInstance
            }
            synchronized(this) {                                                               //protected from concurrent execution by threads
                val instance = Room.databaseBuilder(
                    context.applicationContext,
                    StudentDatabase::class.java,
                    "user_database"

                ).build()
                INSTANCE = instance
                return instance


            }
        }
    }
}

//Reference https://www.youtube.com/watch?v=lwAvI3WDXBY 7:02