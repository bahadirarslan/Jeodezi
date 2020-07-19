![GitHub license](https://img.shields.io/badge/license-MIT-lightgrey.svg) [![](https://jitpack.io/v/bahadirarslan/Jeodezi.svg)](https://jitpack.io/#bahadirarslan/Jeodezi) [![GitHub release](https://img.shields.io/github/release/bahadirarslan/Jeodezi.svg)](https://github.com/bahadirarslan/Jeodezi/releases)
# Jeodezi
Jeodezi is Kotlin version of [Chris Veness](https://github.com/chrisveness)'s amazing work [Geodesy Functions](https://github.com/chrisveness/geodesy). [Geodesy Functions](https://github.com/chrisveness/geodesy) is written in JavaScript and contains dozens of functions to make calculations about:
> - Geodesic calculations (distances, bearings, etc) covering both spherical earth and ellipsoidal earth models, and both trigonometry-based and vector-based approaches.
> - Ellipsoidal-earth coordinate systems covering both historical datums and modern terrestrial reference frames (TRFs).
> - Mapping functions including UTM/MGRS and UK OS Grid References.

## Quick Links
* [Installation](#installation)
* [Usage](#usage)
* [Example](#example)
* [Contributing](#contributing)
* [Licence](#licence)


## Installation
1. Add the code below to your project level build.gradle file
```
allprojects {
  repositories {
    ...
    maven { url 'https://jitpack.io' }
  }
}
```
2. Add the code below to your app level build.gradle file
```
dependencies {
    implementation 'com.github.bahadirarslan:Jeodezi:0.5'
}
```

## Usage
### Great Circle Calculations
#### Distance
To calculate distance between two geographical coordinate, distance method can be used. This method will calculate the distance between two points with [Haversine formula](https://en.wikipedia.org/wiki/Haversine_formula#:~:text=The%20haversine%20formula%20determines%20the,and%20angles%20of%20spherical%20triangles.). The result will be great circle distance in kilometers. 
```
val istCoordinates = Coordinate(41.28111111, 28.75333333) // The coordinates of Istanbul Airport
val jfkCoordinates = Coordinate(40.63980103, -73.77890015) // The coordinates of New York JFK Airport
val greatCircle = GreatCircle()
val distance = greatCircle.distance(istCoordinates, jfkCoordinates)
```
If you want to get distance in nautical miles you can use ```distanceInNm``` method with the same parameters

#### Bearing
This method is for the initial bearing (sometimes referred to as forward azimuth) which if followed in a straight line along a great-circle arc will take you from the start point to the end point. Bearing is in degrees 0° to 360°
```
val istCoordinates = Coordinate(41.28111111, 28.75333333) // The coordinates of Istanbul Airport
val jfkCoordinates = Coordinate(40.63980103, -73.77890015) // The coordinates of New York JFK Airport
val greatCircle = GreatCircle()
val bearing = greatCircle.bearing(istCoordinates, jfkCoordinates)
```

#### Midpoint
This method calculates the midpoint between startPoint and endPoint on the great circle. Result type is Coordinate
```
val istCoordinates = Coordinate(41.28111111, 28.75333333) // The coordinates of Istanbul Airport
val jfkCoordinates = Coordinate(40.63980103, -73.77890015) // The coordinates of New York JFK Airport
val greatCircle = GreatCircle()
val midpoint = greatCircle.midpoint(istCoordinates, jfkCoordinates)
```

#### Destination
This method calculates the destination point and final bearing travelling along a (shortest distance) great circle arc for a given start point, initial bearing and distance. Result type is Coordinate
```
val istCoordinates = Coordinate(41.28111111, 28.75333333) // The coordinates of Istanbul Airport
val jfkCoordinates = Coordinate(40.63980103, -73.77890015) // The coordinates of New York JFK Airport
val greatCircle = GreatCircle()
val bearing = greatCircle.bearing(istCoordinates, jfkCoordinates)

val distance = 1000 // km
val destination = greatCircle.destination(istCoordinates, distance, bearing) // The coordinates of point which is at 1000th km great circle between Istanbul Airport and JFK Airport
```
## Example
You can find an example application which uses Jeodezi library in the repo. 

## Contributing

Jeodezi is a work in progress. Currently only a little part of functions of [Geodesy Functions](https://github.com/chrisveness/geodesy) are ported to Kotlin.  There are lots to do and your contributions are most welcome. Feel free to fork the repo and submit Pull Request's.
You can help about
* Unit testing
* Better example
* Code refactoring and optimization
* Issues/bugs

## License

Jeodezi is released under the [MIT License](LICENSE.md).
