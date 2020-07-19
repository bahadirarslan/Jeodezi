package com.bahadirarslan.example

import android.os.Bundle
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import com.bahadirarslan.example.databinding.ActivityMainBinding
import com.bahadirarslan.jeodezi.Coordinate
import com.bahadirarslan.jeodezi.GreatCircle
import java.text.DecimalFormat

/**
 * This sample application created to be example for Jeodezi Great Circle functions.
 * Bahadir Arslan
 * July 2020
 */
class MainActivity : AppCompatActivity() {
    private lateinit var binding: ActivityMainBinding
    private var greatCircle = GreatCircle()
    override fun onCreate(savedInstanceState: Bundle?) {
        val dec = DecimalFormat("#,###.00")
        super.onCreate(savedInstanceState)
        binding = ActivityMainBinding.inflate(layoutInflater)
        val view = binding.root

        binding.distance.setOnClickListener {
            val istCoordinates = Coordinate(41.28111111, 28.75333333) // The coordinates of Istanbul Airport
            val jfkCoordinates = Coordinate(40.63980103, -73.77890015) // The coordinates of New York JFK Airport
            val distance = greatCircle.distance(istCoordinates, jfkCoordinates)
            Toast.makeText(this, "Great Circle distance between Istanbul Airport and JFK Airport is ${dec.format(distance)} km", Toast.LENGTH_LONG).show()
        }

        binding.distanceInNm.setOnClickListener {
            val istCoordinates = Coordinate(41.28111111, 28.75333333) // The coordinates of Istanbul Airport
            val jfkCoordinates = Coordinate(40.63980103, -73.77890015) // The coordinates of New York JFK Airport
            val distance = greatCircle.distanceInNm(istCoordinates, jfkCoordinates)
            Toast.makeText(this, "Great Circle distance between Istanbul Airport and JFK Airport is ${dec.format(distance)} nm", Toast.LENGTH_LONG).show()
        }

        binding.bearing.setOnClickListener {
            val istCoordinates = Coordinate(41.28111111, 28.75333333) // The coordinates of Istanbul Airport
            val jfkCoordinates = Coordinate(40.63980103, -73.77890015) // The coordinates of New York JFK Airport
            val bearing = greatCircle.bearing(istCoordinates, jfkCoordinates)
            Toast.makeText(this, "Initial bearing for great circle between Istanbul Airport and JFK Airport is ${dec.format(bearing)} degrees", Toast.LENGTH_LONG).show()
        }

        binding.finalBearing.setOnClickListener {
            val istCoordinates = Coordinate(41.28111111, 28.75333333) // The coordinates of Istanbul Airport
            val jfkCoordinates = Coordinate(40.63980103, -73.77890015) // The coordinates of New York JFK Airport
            val finalBearing = greatCircle.finalBearing(istCoordinates, jfkCoordinates)
            Toast.makeText(this, "Final bearing for great circle between Istanbul Airport and JFK Airport is ${dec.format(finalBearing)} degrees", Toast.LENGTH_LONG).show()
        }

        binding.midPoint.setOnClickListener {
            val istCoordinates = Coordinate(41.28111111, 28.75333333) // The coordinates of Istanbul Airport
            val jfkCoordinates = Coordinate(40.63980103, -73.77890015) // The coordinates of New York JFK Airport
            val midpoint = greatCircle.midpoint(istCoordinates, jfkCoordinates)
            Toast.makeText(this, "Midpoint's coordinates of great circle between Istanbul Airport and JFK Airport are $midpoint ", Toast.LENGTH_LONG).show()
        }

        binding.intermediate.setOnClickListener {
            val istCoordinates = Coordinate(41.28111111, 28.75333333) // The coordinates of Istanbul Airport
            val jfkCoordinates = Coordinate(40.63980103, -73.77890015) // The coordinates of New York JFK Airport
            val fraction = 0.25
            val intermediate = greatCircle.intermediate(istCoordinates, jfkCoordinates, fraction)
            Toast.makeText(this, "For $fraction fraction, intermediate point's coordinates of great circle between Istanbul Airport and JFK Airport are $intermediate ", Toast.LENGTH_LONG).show()
        }

        binding.intersection.setOnClickListener {
            val istCoordinates = Coordinate(41.28111111, 28.75333333) // The coordinates of Istanbul Airport
            val fcoCoordinates = Coordinate(41.8002778,12.2388889) // The coordinates of Roma Fiumicino Airport
            val bearingFromIstanbulToWest : Double = 270.0
            val bearingFromRomeToNorthEast : Double = 45.0
            val intersection = greatCircle.intersection(istCoordinates, bearingFromIstanbulToWest, fcoCoordinates, bearingFromRomeToNorthEast)
            Toast.makeText(this, "Intersection coordinates of from Istanbul Airport with bearing $bearingFromIstanbulToWest and from Roma Fiumicino Airport with bearing $bearingFromRomeToNorthEast is $intersection ", Toast.LENGTH_LONG).show()
        }

        binding.destination.setOnClickListener {
            val istCoordinates = Coordinate(41.28111111, 28.75333333) // The coordinates of Istanbul Airport
            val bearingFromIstanbulToWest : Double = 270.0
            val distanceInKm : Double = 168.0 //apprx 100 nm
            val destination = greatCircle.destination(istCoordinates, bearingFromIstanbulToWest, distanceInKm)
            Toast.makeText(this, "The destination coordinates of $distanceInKm km away from Istanbul Airport on bearing $bearingFromIstanbulToWest is $destination", Toast.LENGTH_LONG).show()
        }

        binding.crossTrack.setOnClickListener {
            val istCoordinates = Coordinate(41.28111111, 28.75333333) // The coordinates of Istanbul Airport
            val jfkCoordinates = Coordinate(40.63980103, -73.77890015) // The coordinates of New York JFK Airport
            val fcoCoordinates = Coordinate(41.8002778,12.2388889) // The coordinates of Roma Fiumicino Airport
            val crossTrackDistanceInKm = greatCircle.crossTrackDistance(fcoCoordinates, istCoordinates, jfkCoordinates)
            Toast.makeText(this, "Distance from Rome Fiumicino Airport to great circle between Istanbul Airport and JFK Airport is ${dec.format(crossTrackDistanceInKm)} km ", Toast.LENGTH_LONG).show()
        }

        binding.alongTrack.setOnClickListener {
            val istCoordinates = Coordinate(41.28111111, 28.75333333) // The coordinates of Istanbul Airport
            val jfkCoordinates = Coordinate(40.63980103, -73.77890015) // The coordinates of New York JFK Airport
            val fcoCoordinates = Coordinate(41.8002778,12.2388889) // The coordinates of Roma Fiumicino Airport
            val alongTrackDistanceTo = greatCircle.alongTrackDistanceTo(fcoCoordinates, istCoordinates, jfkCoordinates)
            Toast.makeText(this, "Distance from Istanbul Airport to the point where Roma Fiumicino Airport crosses great circle between Istanbul Airport and JFK Airport is ${dec.format(alongTrackDistanceTo)} km ", Toast.LENGTH_LONG).show()
        }

        binding.maxLatitudes.setOnClickListener {
            val istCoordinates = Coordinate(41.28111111, 28.75333333) // The coordinates of Istanbul Airport
            val bearingFromIstanbulToWest : Double = 270.0
            val maxLatitude = greatCircle.maxLatitude(istCoordinates, bearingFromIstanbulToWest)
            Toast.makeText(this, "The maximum latitude of the path from Istanbul Airport on bearing $bearingFromIstanbulToWest is $maxLatitude degrees", Toast.LENGTH_LONG).show()
        }

        binding.crossingParallels.setOnClickListener {
            val istCoordinates = Coordinate(41.28111111, 28.75333333) // The coordinates of Istanbul Airport
            val jfkCoordinates = Coordinate(40.63980103, -73.77890015) // The coordinates of New York JFK Airport
            val latitude : Double = 60.0 // means 60 degrees north
            val crossingParallels = greatCircle.crossingParallels(istCoordinates, jfkCoordinates, latitude)
            if (crossingParallels != null) {
                Toast.makeText(
                    this,
                    "The great circle between Istanbul Airport and JFK Airport crosses latitude $latitude at longitudes ${crossingParallels[0]} and ${crossingParallels[1]} ",
                    Toast.LENGTH_LONG
                ).show()
            }
            else {
                Toast.makeText(
                    this,
                    "The great circle between Istanbul Airport and JFK Airport does not cross latitude $latitude",
                    Toast.LENGTH_LONG
                ).show()
            }
        }

        setContentView(view)
    }
}