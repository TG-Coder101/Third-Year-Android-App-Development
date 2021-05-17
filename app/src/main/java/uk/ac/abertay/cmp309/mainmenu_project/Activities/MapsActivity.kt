package uk.ac.abertay.cmp309.mainmenu_project.Activities

import android.Manifest
import android.content.pm.PackageManager
import android.location.Address
import android.location.Geocoder
import android.location.Location
import android.os.Bundle
import android.util.Log
import android.view.KeyEvent
import android.view.inputmethod.EditorInfo
import android.widget.EditText
import android.widget.ImageView
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import androidx.core.app.ActivityCompat
import androidx.core.content.ContextCompat
import com.google.android.gms.location.FusedLocationProviderClient
import com.google.android.gms.location.LocationCallback
import com.google.android.gms.location.LocationResult
import com.google.android.gms.location.LocationServices
import com.google.android.gms.maps.CameraUpdateFactory
import com.google.android.gms.maps.GoogleMap
import com.google.android.gms.maps.OnMapReadyCallback
import com.google.android.gms.maps.SupportMapFragment
import com.google.android.gms.maps.model.LatLng
import com.google.android.gms.maps.model.MarkerOptions
import com.google.android.gms.tasks.Task
import uk.ac.abertay.cmp309.mainmenu_project.R
import java.io.IOException


class MapsActivity : AppCompatActivity(), OnMapReadyCallback {

    //lateinnit
    lateinit var locationCallback: LocationCallback
    lateinit var mfusedLocationProviderClient: FusedLocationProviderClient
    lateinit var locationResult: LocationResult
    lateinit var latLng: LatLng
    lateinit var mSearchText: EditText
    lateinit var mGps: ImageView

    //private lateinnit
    private lateinit var mMap: GoogleMap
    private lateinit var fusedLocationClient: FusedLocationProviderClient

    //private val
    private val LOCATION_PERMISSION_REQUEST_CODE = 1234
    private val Fine_Location = Manifest.permission.ACCESS_FINE_LOCATION
    private val Course_Location = Manifest.permission.ACCESS_COARSE_LOCATION
    private var mLocationPermissionGranted = false
    private val TAG = "MapActivity"

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_maps)
        mSearchText = findViewById(R.id.input_search)
        // Obtain the SupportMapFragment and get notified when the map is ready to be used.
        getLocationPermission() //call getLocationPermissionFunction
    }

    /**
     * Manipulates the map once available.
     * This callback is triggered when the map is ready to be used.
     * This is where we can add markers or lines, add listeners or move the camera. In this case,
     * we just add a marker near Sydney, Australia.
     * If Google Play services is not installed on the device, the user will be prompted to install
     * it inside the SupportMapFragment. This method will only be triggered once the user has
     * installed Google Play services and returned to the app.
     */

    override fun onMapReady(googleMap: GoogleMap) {
        mMap = googleMap
        Toast.makeText(applicationContext, "Map is Ready", Toast.LENGTH_SHORT).show()
        Log.d(TAG, "onMapReady: map is ready")
        if (mLocationPermissionGranted) {
            getDeviceLocation()
            if (ActivityCompat.checkSelfPermission(
                    this,
                    android.Manifest.permission.ACCESS_FINE_LOCATION
                )
                != PackageManager.PERMISSION_GRANTED && ActivityCompat.checkSelfPermission(
                    this,
                    android.Manifest.permission.ACCESS_COARSE_LOCATION
                )
                != PackageManager.PERMISSION_GRANTED
            ) {
                return
            }
            mMap.isMyLocationEnabled = true
            mMap.uiSettings.isMyLocationButtonEnabled = false //remove location button
            mMap.uiSettings.isCompassEnabled = true
            mMap.uiSettings.isMapToolbarEnabled = true
            mMap.uiSettings.setAllGesturesEnabled(true)
            mMap.isTrafficEnabled = true    //enable traffic
            mMap.mapType = GoogleMap.MAP_TYPE_HYBRID //enables hybrid map
            init() //call init function

            //Reference for map settings: https://developers.google.com/maps/documentation/android-sdk/map#maps_android_map_type-kotlin
        }
    }

    private fun getDeviceLocation() {  //get devices location
        Log.d(TAG, "getDeviceLocation: get device location")
        mfusedLocationProviderClient = LocationServices.getFusedLocationProviderClient(this)
        try {
            if (mLocationPermissionGranted) {
                if (ActivityCompat.checkSelfPermission(
                        this,
                        Manifest.permission.ACCESS_FINE_LOCATION
                    )
                    != PackageManager.PERMISSION_GRANTED && ActivityCompat.checkSelfPermission(
                        this,
                        Manifest.permission.ACCESS_COARSE_LOCATION
                    )
                    != PackageManager.PERMISSION_GRANTED
                ) {
                    return
                }
                val location = mfusedLocationProviderClient.lastLocation
                location.addOnCompleteListener {
                    fun onComplete(task: Task<Location>) {
                        if (task.isSuccessful) {
                            Log.d(TAG, "onComplete: found location!")
                            val currentLocation = task.result as Location
                            moveCamera(
                                LatLng(currentLocation.latitude, currentLocation.longitude),
                                "My Location"
                            )
                        } else {
                            Log.d(TAG, "onComplete: no location")
                            Toast.makeText(
                                applicationContext,
                                "unable to get location",
                                Toast.LENGTH_SHORT
                            ).show()
                        }
                    }
                }
            }
        } catch (e: Exception) {
            Log.e(TAG, "getDeviceLocation: SecurityException: " + e.message)
        }
    }

    private fun getLocationPermission() {
        val permissions = arrayOf(
            android.Manifest.permission.ACCESS_COARSE_LOCATION,
            android.Manifest.permission.ACCESS_FINE_LOCATION
        )
        if (ContextCompat.checkSelfPermission(
                this.applicationContext,
                Fine_Location
            ) == PackageManager.PERMISSION_GRANTED
        ) {
            if (ContextCompat.checkSelfPermission(
                    this.applicationContext,
                    Course_Location
                ) == PackageManager.PERMISSION_GRANTED
            ) {
                mLocationPermissionGranted = true
                initialiseMap()   //initialise the map if all permission's are granted
            } else {
                ActivityCompat.requestPermissions(
                    this,
                    permissions,
                    LOCATION_PERMISSION_REQUEST_CODE
                )
            }
        } else {
            ActivityCompat.requestPermissions(
                this,
                permissions,
                LOCATION_PERMISSION_REQUEST_CODE
            )
        }
    }

    fun init() {
        Log.d(TAG, "init: initialising")
        mSearchText.setOnEditorActionListener { textView, actionId, keyEvent ->
            if (actionId == EditorInfo.IME_ACTION_SEARCH
                || actionId == EditorInfo.IME_ACTION_DONE
                || keyEvent.action == KeyEvent.ACTION_DOWN
                || keyEvent.action == KeyEvent.KEYCODE_ENTER
            ) {
                geoLocate() //execute the method for searching
            }
            false
        }
        //Reference: https://www.youtube.com/watch?v=M0bYvXlhgSI
    }

    fun geoLocate() {
        Log.d(TAG, "geoLocate: geolocating")
        val searchString = mSearchText.text.toString()
        val geocoder = Geocoder(this@MapsActivity)
        var list: List<Address> = ArrayList()
        try {
            list = geocoder.getFromLocationName(searchString, 1)
        } catch (e: IOException) {
            Log.d(TAG, "geoLocate: IOException" + e.message)
        }
        if (list.isNotEmpty()) {
            val address = list[0]
            Log.d(TAG, "geoLocate: found location: " + address.toString())
            moveCamera(LatLng(address.latitude, address.longitude), address.getAddressLine(0))
        }
    }

    private fun moveCamera(latLng: LatLng, title: String) {
        mMap.moveCamera(CameraUpdateFactory.newLatLng(latLng)/*, zoom*/)
        val options = MarkerOptions()
            .position(latLng)
            .title(title)
        mMap.addMarker(options)

    }


    //request permission result
    override fun onRequestPermissionsResult(
        requestCode: Int,
        permissions: Array<out String>,
        grantResults: IntArray
    ) {
        super.onRequestPermissionsResult(requestCode, permissions, grantResults)
        Log.d(TAG, "onRequestPermissionResult: call.")
        mLocationPermissionGranted = false
        when (requestCode) {
            LOCATION_PERMISSION_REQUEST_CODE -> {
                if (grantResults.isNotEmpty()) {
                    for (i in grantResults.indices) {   //loop through multiple permissions
                        if (grantResults[i] != PackageManager.PERMISSION_GRANTED) {
                            mLocationPermissionGranted = false
                            Log.d(TAG, "onRequestPermissionResult: permission failed")
                            return
                        }
                    }
                    Log.d(TAG, "onRequestPermissionResult: permission granted")
                    mLocationPermissionGranted = true // initialise the map
                    initialiseMap()
                }
            }
        }
    }

    private fun initialiseMap() {
        val mapFragment = supportFragmentManager
            .findFragmentById(R.id.map) as SupportMapFragment
        mapFragment.getMapAsync(this)

    }

//Reference For setting up API: https://www.youtube.com/watch?v=urLA8z6-l3k
//Documentation Used: https://developer.android.com/guide


}


