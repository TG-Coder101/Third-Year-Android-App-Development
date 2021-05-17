package uk.ac.abertay.cmp309.mainmenu_project.Activities

import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import kotlinx.android.synthetic.main.activity_calendar.*
import uk.ac.abertay.cmp309.mainmenu_project.R

class CalendarActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_calendar)

        val year = intent.getStringExtra("Year")

        val month = intent.getStringExtra("Month")

        val day = intent.getStringExtra("Day")

        val message = "$day/$month/$year"

        dateTextView.apply {
            text = message
        }

    }
}