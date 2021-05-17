package uk.ac.abertay.cmp309.mainmenu_project.fragments

import android.app.AlertDialog
import android.content.Intent
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.CalendarView
import android.widget.Toast
import androidx.fragment.app.Fragment
import uk.ac.abertay.cmp309.mainmenu_project.Activities.CalendarActivity
import uk.ac.abertay.cmp309.mainmenu_project.R

class TimetableFragment : Fragment() {

    lateinit var calendarView: CalendarView
    var selectedDate: String? = null

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {

        val resultInflater = inflater.inflate(R.layout.fragment_timetable, container, false)
        // Inflate the layout for this fragment
        calendarView = resultInflater.findViewById(R.id.calendarView)!!
        //set calendar listener
        calendarView.setOnDateChangeListener { _, year: Int, month: Int, dateMonth: Int ->
            selectedDate = dateMonth.toString() + "/" + (month + 1) + "/" + year    //convert to variable
            Toast.makeText(
                    requireActivity(),
                    "The selected date is $selectedDate",
                    Toast.LENGTH_SHORT
            ).show()
            val alert = AlertDialog.Builder(requireContext())
            alert.setPositiveButton("Yes") { _, _ ->
                val modifiedMonth = month + 1

                //convert to strings
                val intent = Intent(requireContext(), CalendarActivity::class.java).apply {
                    putExtra("Year", year.toString())
                    putExtra("Month", modifiedMonth.toString())
                    putExtra("Day", dateMonth.toString())
                }
                startActivity(intent)   //start the intent
            }
            //alert dialogue options
            alert.setNegativeButton("No") { _, _ -> }
            alert.setTitle("Send")
            alert.setMessage("Are you sure you want to send ")
            alert.create().show()
        }
        return resultInflater
    }
}
//https://www.youtube.com/watch?v=IdTRhFkC1Po Programmer World
//https://programmerworld.co/android/how-to-create-a-custom-calendar-app-to-store-reminders-and-events-using-sqlite-database-in-android-studio/











/*
Potential Future Work.

    fun InsertDatabase(view: View?, selectedDate: String?) {
        Log.d(TAG, "my Message")
        when (view?.id) {

            R.id.buttonSave -> {
                val contentValues = ContentValues().apply {
                    put("Date", selectedDate)
                    put("Event", editText.text.toString())

                }
                sqlitedatabase.insert("Timetable", null, contentValues)
                Toast.makeText(
                        requireActivity(),
                        "Clicked",
                        Toast.LENGTH_SHORT
                ).show()
                Log.d(TAG, "my Message")
            }
        }
    }


    fun ReadDatabase(view: View?, selectedDate: String) {
        dbHandler.readableDatabase
        val query = "Select Event from Timetable where Date=$selectedDate"
        try {
            cursor = sqlitedatabase.rawQuery(query, null)
            cursor.moveToFirst()
            editText.setText(cursor.getString(0))
        } catch (e: Exception) {
            e.printStackTrace()
            editText.setText("")
        }
        Log.d(TAG, "victory")

    }

       try {
            dbHandler = SQLite(requireContext())
            sqlitedatabase = dbHandler.writableDatabase
            sqlitedatabase.execSQL("CREATE TABLE Timetable(Date TEXT, Event TEXT)")
        } catch (e: Exception) {
            e.printStackTrace()
        }



}
*/
