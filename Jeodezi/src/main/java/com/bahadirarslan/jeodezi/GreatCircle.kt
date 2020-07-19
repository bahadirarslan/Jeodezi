package com.bahadirarslan.jeodezi

import java.lang.Math.sin
import kotlin.math.*

/**
 * A great circle, also known as an orthodrome, of a sphere is the intersection of the sphere and
 * a plane that passes through the center point of the sphere. A great circle is the largest circle
 * that can be drawn on any given sphere. (Wikipedia)
 * This library contains functions about great circle for calculation, bearings, distances or midpoints.
 * All these functions are taken from Chris Veness(https://github.com/chrisveness)'s amazing work
 * Geodesy Functions(https://github.com/chrisveness/geodesy) and ported to Kotlin.
 * Some of comments are copied from original library
 */
class GreatCircle {
    companion object {
        const val earthRadiusKm: Double = 6372.8
    }

    /**
     * Haversine formula. Giving great-circle distances between two points on a sphere from their longitudes and latitudes.
     * It is a special case of a more general formula in spherical trigonometry, the law of haversines, relating the
     * sides and angles of spherical "triangles".
     *
     * https://rosettacode.org/wiki/Haversine_formula#Java
     * Based on https://gist.github.com/jferrao/cb44d09da234698a7feee68ca895f491
     * @param startPoint Initial coordinates
     * @param endPoint Final coordinates
     * @return Distance in kilometers
     *
     * @sample
     * val istCoordinates = Coordinate(41.28111111, 28.75333333) // The coordinates of Istanbul Airport
     * val jfkCoordinates = Coordinate(40.63980103, -73.77890015) // The coordinates of New York JFK Airport
     * val greatCircle = GreatCircle()
     * val distance = greatCircle.distance(istCoordinates, jfkCoordinates)
     */
    fun distance(startPoint : Coordinate, endPoint : Coordinate ): Double {
        val dLat = Math.toRadians(endPoint.lat - startPoint.lat);
        val dLon = Math.toRadians(endPoint.lon - startPoint.lon);
        val originLat = Math.toRadians(startPoint.lat);
        val destinationLat = Math.toRadians(endPoint.lat);

        val a = sin(dLat / 2).pow(2.toDouble()) + sin(dLon / 2).pow(2.toDouble()) * cos(originLat) * cos(destinationLat);
        val c = 2 * asin(sqrt(a));
        return earthRadiusKm * c;
    }

    /**
     * Haversine formula. Giving great-circle distances between two points on a sphere from their longitudes and latitudes.
     * It is a special case of a more general formula in spherical trigonometry, the law of haversines, relating the
     * sides and angles of spherical "triangles".
     *
     * https://rosettacode.org/wiki/Haversine_formula#Java
     *
     * @param startPoint Initial coordinates
     * @param endPoint Final coordinates
     * @return Distance in nautical miles
     *
     * @sample
     * val istCoordinates = Coordinate(41.28111111, 28.75333333) // The coordinates of Istanbul Airport
     * val jfkCoordinates = Coordinate(40.63980103, -73.77890015) // The coordinates of New York JFK Airport
     * val greatCircle = GreatCircle()
     * val distance = greatCircle.distanceInNm(istCoordinates, jfkCoordinates)
     */
    fun distanceInNm(startPoint : Coordinate, endPoint : Coordinate): Double {
        val distanceInKm = distance(startPoint, endPoint)
        return distanceInKm * 0.539956803
    }

    /**
     * This function is for the initial bearing (sometimes referred to as forward azimuth) which if
     * followed in a straight line along a great-circle arc will take you from the start point to the end point
     *
     *
     * @param startPoint Initial coordinates
     * @param endPoint Final coordinates
     * @return Bearing in degrees from North, 0° ... 360°
     *
     * @sample
     * val istCoordinates = Coordinate(41.28111111, 28.75333333) // The coordinates of Istanbul Airport
     * val jfkCoordinates = Coordinate(40.63980103, -73.77890015) // The coordinates of New York JFK Airport
     * val greatCircle = GreatCircle()
     * val bearing = greatCircle.bearing(istCoordinates, jfkCoordinates)
     */
    fun bearing(startPoint : Coordinate, endPoint : Coordinate) : Double {
        if(startPoint == endPoint) return 0.0 // same points
        val deltaLon = endPoint.lonR-startPoint.lonR
        val y = sin(deltaLon) * cos(endPoint.latR);
        val x = (cos(startPoint.latR) * sin(endPoint.latR)) -
                (sin(startPoint.latR) * cos(endPoint.latR) * cos(deltaLon));
        val phi = atan2(y, x);
        return phi.toDegrees().wrap360()
    }

    /**
     * This function returns final bearing arriving at destination point from startPoint; the final
     * bearing will differ from the initial bearing by varying degrees according to distance and latitude
     *
     *
     * @param startPoint Initial coordinates
     * @param endPoint Final coordinates
     * @return Bearing in degrees from North, 0° ... 360°
     */
    fun finalBearing(startPoint : Coordinate, endPoint : Coordinate) : Double {
        val bearing = bearing(startPoint, endPoint) + 180;
        return bearing.wrap360();
    }

    /**
     * This function calculates the midpoint between startPoint and endPoint
     *
     * @param startPoint Initial coordinates
     * @param endPoint Final coordinates
     * @return Midpoint coordinates
     *
     * @sample
     * val istCoordinates = Coordinate(41.28111111, 28.75333333) // The coordinates of Istanbul Airport
     * val jfkCoordinates = Coordinate(40.63980103, -73.77890015) // The coordinates of New York JFK Airport
     * val greatCircle = GreatCircle()
     * val midpoint = greatCircle.midpoint(istCoordinates, jfkCoordinates)
     */
    fun midpoint(startPoint : Coordinate, endPoint : Coordinate) : Coordinate {
        val deltaLon = endPoint.lon-startPoint.lon
        val bx = cos(endPoint.lat) * cos(deltaLon)
        val by = cos(endPoint.lat) * sin(deltaLon)
        val lat = atan2((sin(startPoint.lat) + sin(endPoint.lat)),
            sqrt( (cos(startPoint.lat) +bx)*(cos(startPoint.lat) +bx) + by*by ) )
        val lon = startPoint.lon + atan2(by, cos(startPoint.lat) + bx)
        return Coordinate(lat, lon)
    }

    /**
     * This function returns the point at given fraction between startPoint and endPoint
     *
     * @param startPoint Initial coordinates
     * @param endPoint Final coordinates
     * @param fraction Fraction between coordinates
     * @return Intermediate coordinates between startPoint and endPoint
     */
    fun intermediate(startPoint : Coordinate, endPoint : Coordinate, fraction : Double) : Coordinate {
        if(startPoint == endPoint) return startPoint // same points

        val deltaLat = endPoint.latR - startPoint.latR
        val deltaLon = endPoint.lonR - startPoint.lonR
        val a = sin(deltaLat/2) * sin(deltaLat/2) + cos(startPoint.latR) * cos(endPoint.latR) * sin(deltaLon/2) * sin(deltaLon/2)
        val δ = 2 * atan2(sqrt(a), sqrt(1-a))

        val A = sin((1-fraction)*δ) / sin(δ)
        val B = sin(fraction*δ) / sin(δ)

        val x = A * cos(startPoint.latR) * cos(startPoint.lonR) + B * cos(endPoint.latR) * cos(endPoint.lonR)
        val y = A * cos(startPoint.latR) * sin(startPoint.lonR) + B * cos(endPoint.latR) * sin(endPoint.lonR)
        val z = A * sin(startPoint.latR) + B * sin(endPoint.latR)

        val lat = atan2(z, sqrt(x*x + y*y))
        val lon = atan2(y, x)

        return Coordinate(lat, lon)
    }

    /**
     * This function returns the point of intersection of two paths which one starts from firstPoint
     * with firstBearing and the other one starts from secondPoint with secondBearing
     *
     * @param firstPoint First point coordinates
     * @param firstBearing First path's bearing
     * @param secondPoint Second point coordinates
     * @param secondBearing Second path's bearing
     * @return Intersections coordinates of two paths or return null if there is no intersection
     */
    fun intersection(firstPoint : Coordinate, firstBearing : Double, secondPoint : Coordinate, secondBearing : Double) : Coordinate? {
        val deltaLat = secondPoint.latR - firstPoint.latR
        val deltaLon = secondPoint.lonR - firstPoint.lonR

        val delta12 = 2 * asin(sqrt(sin(deltaLat/2) * sin(deltaLat/2)
        + cos(firstPoint.latR) * cos(secondPoint.latR) * sin(deltaLon/2) * sin(deltaLon/2)))
        if (abs(delta12) < Math.E) return firstPoint // same points

        val cosTetaa = (sin(secondPoint.latR) - sin(firstPoint.latR)*cos(delta12)) / (sin(delta12)*cos(firstPoint.latR))
        val cosTetab = (sin(firstPoint.latR) - sin(secondPoint.latR)*cos(delta12)) / (sin(delta12)*cos(secondPoint.latR))
        val tetaa = acos(min(max(cosTetaa.toInt(), -1), 1).toDouble())
        val tetab = acos(min(max(cosTetab.toInt(), -1), 1).toDouble())
        val teta12 = if(sin(secondPoint.lonR-firstPoint.lonR)>0) tetaa else (2*Math.PI-tetaa)
        val teta21 = if(sin(secondPoint.lonR-firstPoint.lonR)>0) (2*Math.PI-tetab) else tetab

        val alpha1 = Math.toRadians(firstBearing) - teta12
        val alpha2 = teta21 - Math.toRadians(secondBearing)

        if (sin(alpha1) == 0.0 && sin(alpha2) == 0.0) return null
        if (sin(alpha1) * sin(alpha2) < 0) return null

        val cosalpha3 = -cos(alpha1)*cos(alpha2) + sin(alpha1)*sin(alpha2)*cos(delta12)

        val delta13 = atan2(sin(delta12)*sin(alpha1)*sin(alpha2), cos(alpha2) + cos(alpha1)*cosalpha3)

        val lat = asin(min(max(sin(firstPoint.latR)*cos(delta13) + cos(firstPoint.latR)*sin(delta13)*cos(Math.toRadians(firstBearing)), -1.0), 1.0))

        val deltaLon13 = atan2(sin(Math.toRadians(firstBearing))*sin(delta13)*cos(firstPoint.latR), cos(delta13) - sin(firstPoint.latR)*sin(lat))
        val lon = firstPoint.lonR + deltaLon13

        return Coordinate(lat, lon)
    }

    /**
     * This function calculates the destination point and final bearing travelling along a
     * (shortest distance) great circle arc for a given start point, initial bearing and distance
     *
     *
     * @param startPoint Initial coordinates
     * @param distance Distance on great circle
     * @param bearing Bearing
     * @return Destination coordinates
     *
     * @sample
     * val istCoordinates = Coordinate(41.28111111, 28.75333333) // The coordinates of Istanbul Airport
     * val jfkCoordinates = Coordinate(40.63980103, -73.77890015) // The coordinates of New York JFK Airport
     * val greatCircle = GreatCircle()
     * val bearing = greatCircle.bearing(istCoordinates, jfkCoordinates)
     *
     * val distance = 1000 // km
     * val destination = greatCircle.destination(istCoordinates, distance, bearing) // The coordinates of point which is at 1000th km great circle between Istanbul Airport and JFK Airport
     *
     */
    fun destination(startPoint : Coordinate, distance: Double, bearing: Double) : Coordinate{
        val sinLat = sin(startPoint.latR) * cos(distance/ earthRadiusKm) +
                cos(startPoint.latR) * sin(distance/earthRadiusKm) * cos(bearing.toRadians())
        val lat = asin( sinLat );
        val y = sin(bearing.toRadians()) * sin(distance/earthRadiusKm) * cos(startPoint.latR)
        val x = cos(distance/earthRadiusKm) - sin(startPoint.latR) * sinLat
        val lon = startPoint.lonR + atan2(y,x)
        return Coordinate(lat.toDegrees(), lon.toDegrees())
    }

    /**
     * This function returns distance from currentPoint to great circle between startPoint and endPoint
     *
     * @param currentPoint The point whose distance is wondering to great circle
     * @param startPoint Start of the great circle
     * @param endPoint End of the great circle
     * @return Distance to the great circle. If returns positive this means right of the path, otherwise it means left of the path.
     *
     */
    fun crossTrackDistance(currentPoint : Coordinate, startPoint : Coordinate, endPoint : Coordinate) : Double {
        if (currentPoint == startPoint) return 0.0 // same point

        val delta13 = distance(startPoint, currentPoint) / earthRadiusKm
        val teta13 = Math.toRadians(bearing(startPoint, currentPoint))
        val teta12 = Math.toRadians(bearing(startPoint, endPoint))

        val deltaCrossTrack = asin(sin(delta13) * sin(teta13 - teta12))

        return deltaCrossTrack * earthRadiusKm
    }
    /**
     * This function returns how far currentPoint is along a path from from startPoint, heading towards endPoint.
     * That is, if a perpendicular is drawn from currentPoint point to the (great circle) path, the
     * along-track distance is the distance from the start point to where the perpendicular crosses the path.
     *
     *
     * @param currentPoint The point whose distance is wondering to great circle
     * @param startPoint Start of the great circle
     * @param endPoint End of the great circle
     * @return Distance along great circle to point nearest currentPoint point.
     *
     */

    fun alongTrackDistanceTo(currentPoint : Coordinate, startPoint : Coordinate, endPoint : Coordinate) : Double {

        if (currentPoint == startPoint) return 0.0 // same point

        val delta13 = distance(startPoint, currentPoint) / earthRadiusKm
        val teta13 = Math.toRadians(bearing(startPoint, currentPoint))
        val teta12 = Math.toRadians(bearing(startPoint, endPoint))

        val deltaCrossTrack = asin(sin(delta13) * sin(teta13 - teta12))
        val deltaAlongTrack = acos(cos(delta13) / abs(cos(deltaCrossTrack)));

        return deltaAlongTrack*sign(cos(teta12-teta13)) * earthRadiusKm;
    }

    /**
     * This function returns maximum latitude reached when travelling on a great circle on given bearing from
     * startPoint point (‘Clairaut’s formula’). Negate the result for the minimum latitude (in the
     * southern hemisphere).
     *
     * The maximum latitude is independent of longitude; it will be the same for all points on a
     * given latitude.
     *
     * @param startPoint Initial coordinates
     * @param bearing Bearing
     * @return Destination coordinates
     *
     *
     */
    fun maxLatitude(startPoint : Coordinate, bearing: Double) : Double{
       val bearingInRad = Math.toRadians(bearing)
        val maxLat = acos(abs(sin(bearingInRad)*cos(startPoint.latR)))
        return Math.toDegrees(maxLat)
    }

    /**
     * This function returns the pair of meridians at which a great circle defined by two points crosses the given
     * latitude. If the great circle doesn't reach the given latitude, null is returned.
     *
     * The maximum latitude is independent of longitude; it will be the same for all points on a
     * given latitude.
     *
     * @param startPoint Initial coordinates
     * @param endPoint Final coordinates
     * @param latitude Latitude crossings are to be determined for.
     * @return Array containing { lon1, lon2 } or null if given latitude not reached.
     *
       *
     */
    fun crossingParallels(startPoint : Coordinate, endPoint : Coordinate, latitude: Double) : DoubleArray? {
        val latR = Math.toRadians(latitude)

        val deltaLon = endPoint.lonR - startPoint.lonR

        val x = sin(startPoint.latR) * cos(endPoint.latR) * cos(latR) * sin(deltaLon)
        val y = sin(startPoint.latR) * cos(endPoint.latR) * cos(latR) * cos(deltaLon) - cos(startPoint.latR) * sin(endPoint.latR) * cos(latR)
        val z = cos(startPoint.latR) * cos(endPoint.latR) * sin(latR) * sin(deltaLon)

        if (z * z > x * x + y * y) return null

        val deltaM = atan2(-y, x)
        val deltaLoni = acos(z / sqrt(x*x + y*y))

        val deltaI1 = startPoint.lonR + deltaM - deltaLoni
        val deltaI2 = startPoint.lonR + deltaM + deltaLoni

        val lon1 = Math.toDegrees(deltaI1)
        val lon2 = Math.toDegrees(deltaI2)

        return doubleArrayOf(
            lon1.wrap180(),
            lon2.wrap180())
    }

}

