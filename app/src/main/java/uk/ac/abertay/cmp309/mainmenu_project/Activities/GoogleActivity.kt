package uk.ac.abertay.cmp309.mainmenu_project.Activities

import android.content.ContentValues.TAG
import android.content.Intent
import android.os.Bundle
import android.provider.CalendarContract
import android.util.Log
import android.widget.Button
import android.widget.EditText
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import androidx.databinding.DataBindingUtil
import uk.ac.abertay.cmp309.mainmenu_project.R
import uk.ac.abertay.cmp309.mainmenu_project.databinding.ActivityGoogleBinding


class GoogleActivity : AppCompatActivity() {

    //lateinit variables
    lateinit var title: EditText
    lateinit var location: EditText
    lateinit var description: EditText
    lateinit var addEvent: Button
    lateinit var binding: ActivityGoogleBinding //set up data binding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = DataBindingUtil.setContentView(this, R.layout.activity_google)
        //create data binding variables
        title = binding.etTitle
        location = binding.etLocation
        description = binding.etDescription
        addEvent = binding.buttonAdd
        //Create explicit intent
        addEvent.setOnClickListener {
            if (title.text.toString().isNotEmpty() && location.text.toString().isNotEmpty()
                && description.text.toString().isNotEmpty()
            ) {
                val intent = Intent(Intent.ACTION_INSERT)
                intent.data = CalendarContract.Events.CONTENT_URI  //set the intent data as the content uri
                //pass in the values we want to send
                intent.putExtra(CalendarContract.Events.TITLE, title.text.toString())
                intent.putExtra(CalendarContract.Events.EVENT_LOCATION, location.text.toString())
                intent.putExtra(CalendarContract.Events.DESCRIPTION, description.text.toString())
                intent.putExtra(CalendarContract.Events.ALL_DAY, true)  //make event available all day
                if (intent.resolveActivity(packageManager) != null) {
                    startActivity(intent)
                    Log.d(TAG, "Intent started")
                } else {
                    Toast.makeText(this, "No app that can support this action",
                        Toast.LENGTH_SHORT).show()
                    Log.d(TAG, "Intent Not Started")
                }
            } else {
                Toast.makeText(this, "Please fill all fields", Toast.LENGTH_SHORT).show()   //if the edit text fields are empty
            }
        }
    }
}

//https://www.youtube.com/watch?v=NK_-phxyIAM
//https://developer.android.com/guide/components/intents-common#Calendar