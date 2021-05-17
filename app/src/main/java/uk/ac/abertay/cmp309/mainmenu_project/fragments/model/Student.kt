package uk.ac.abertay.cmp309.mainmenu_project.fragments.model

import android.os.Parcelable
import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.PrimaryKey
import kotlinx.android.parcel.Parcelize

@Parcelize
//specify table name and columns
@Entity(tableName = "user_table")
data class Student(
        @PrimaryKey(autoGenerate = true)    //room library will automatically generate numbers for id column
        var id: Int,

        //students first name
        @ColumnInfo(name = "student_first_name")
        var firstName: String,

        //students last name
        @ColumnInfo(name = "student_last_name")
        var lastName: String,

        //students age
        @ColumnInfo(name = "student_age")
        var age: String,

        //students
        @ColumnInfo(name = "student_year_group")
        var yearGroup: Int
): Parcelable //pass student object
