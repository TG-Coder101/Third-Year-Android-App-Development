package uk.ac.abertay.cmp309.mainmenu_project

import android.content.Intent
import android.os.Bundle
import android.view.MenuItem
import android.widget.Toast
import androidx.appcompat.app.ActionBarDrawerToggle
import androidx.appcompat.app.AppCompatActivity
import androidx.appcompat.widget.Toolbar
import androidx.core.view.GravityCompat
import androidx.databinding.DataBindingUtil
import androidx.fragment.app.FragmentManager
import androidx.fragment.app.FragmentTransaction
import com.google.android.material.navigation.NavigationView
import uk.ac.abertay.cmp309.mainmenu_project.Activities.MapsActivity
import uk.ac.abertay.cmp309.mainmenu_project.databinding.ActivityMainBinding
import uk.ac.abertay.cmp309.mainmenu_project.fragments.ClassroomFragment
import uk.ac.abertay.cmp309.mainmenu_project.fragments.MainFragment
import uk.ac.abertay.cmp309.mainmenu_project.fragments.SettingsFragment
import uk.ac.abertay.cmp309.mainmenu_project.fragments.TimetableFragment

class MainActivity : AppCompatActivity(), NavigationView.OnNavigationItemSelectedListener {         //listen for click events on menu

    lateinit var fragmentManager: FragmentManager                                                   //fragment classes
    lateinit var binding: ActivityMainBinding
    lateinit var actionBarDrawerToggle: ActionBarDrawerToggle
    lateinit var toolbar: Toolbar                                                                   //toolbar variable for custom toolbar
    lateinit var fragmentTransaction: FragmentTransaction


    //OnCreate
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = DataBindingUtil.setContentView(this, R.layout.activity_main)
        //Assigning custom toolbar to new activity
        toolbar = findViewById(R.id.toolbar)                                                        //lookup toolbar id
        setSupportActionBar(toolbar)                                                                //save toolbar of main activity
        binding.naviView.setNavigationItemSelectedListener(this)

        actionBarDrawerToggle = ActionBarDrawerToggle(this, binding.drawerLayout, toolbar, R.string.open, R.string.close)        //implement action_bar_toggle. Ties together the functionality of DrawerLayout and ActionBar using activity
        binding.drawerLayout.addDrawerListener(actionBarDrawerToggle)                               //set the listener for action_bar_toggle
        actionBarDrawerToggle.isDrawerIndicatorEnabled = true                                       //enable navigation drawer 'hamburger'
        actionBarDrawerToggle.syncState()                                                           //sync the state of the indicator based on whether the drawer layout is open or closed

        // must load default fragment or user will see blank fragment
        fragmentManager = supportFragmentManager                                                    //initialise fragment manager first
        fragmentTransaction = fragmentManager.beginTransaction()                                    //initialise fragment transaction
        fragmentTransaction.add(R.id.container_fragment, MainFragment())                            //pass in the main fragment
        fragmentTransaction.commit()                                                                //must commit the fragment or it wont work
    }


    //Identify which menu is clicked
    override fun onNavigationItemSelected(item: MenuItem): Boolean {

        binding.drawerLayout.closeDrawer(GravityCompat.START)                                       //closes the drawer after fragment is opened

        when (item.itemId) {
            R.id.home -> {
                fragmentManager = supportFragmentManager                                            //initialise fragment manager first
                Toast.makeText(applicationContext, "Home", Toast.LENGTH_SHORT).show()
                fragmentTransaction = fragmentManager.beginTransaction()                            //initialise fragment transaction
                fragmentTransaction.replace(R.id.container_fragment, MainFragment())                //pass in the main fragment
                fragmentTransaction.commit()
            }
            R.id.work -> {
                fragmentManager = supportFragmentManager                                            //initialise fragment manager first
                Toast.makeText(applicationContext, "Classroom", Toast.LENGTH_SHORT).show()
                fragmentTransaction = fragmentManager.beginTransaction()                            //initialise fragment transaction
                fragmentTransaction.replace(R.id.container_fragment, ClassroomFragment())           //pass in the main fragment
                fragmentTransaction.commit()
            }
            R.id.timeline -> {
                fragmentManager = supportFragmentManager                                            //initialise fragment manager first
                Toast.makeText(applicationContext, "Timetable", Toast.LENGTH_SHORT).show()
                fragmentTransaction = fragmentManager.beginTransaction()                            //initialise fragment transaction
                fragmentTransaction.replace(R.id.container_fragment, TimetableFragment())           //pass in the fragment
                fragmentTransaction.commit()
            }
            R.id.maps -> {
                Toast.makeText(applicationContext, "Maps", Toast.LENGTH_SHORT).show()
                startActivity(Intent(this, MapsActivity::class.java))
            }
            R.id.settings -> {
                fragmentManager = supportFragmentManager                                            //initialise fragment manager first
                Toast.makeText(applicationContext, "Settings", Toast.LENGTH_SHORT).show()
                fragmentTransaction = fragmentManager.beginTransaction()                            //initialise fragment transaction
                fragmentTransaction.replace(R.id.container_fragment, SettingsFragment())            //pass in the fragment
                fragmentTransaction.commit()
            }

            else -> return true
        }
        return true
    }

//Reference: https://www.youtube.com/watch?v=zYVEMCiDcmY
//Reference2: https://www.youtube.com/watch?v=bjYstsO1PgI
//Reference3: https://www.youtube.com/watch?v=bjYstsO1PgI
}













