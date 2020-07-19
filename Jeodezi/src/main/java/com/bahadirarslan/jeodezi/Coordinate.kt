package com.bahadirarslan.jeodezi

data class Coordinate(val lat: Double, val lon: Double) {
    val latR get() = Math.toRadians(lat)
    val lonR get() = Math.toRadians(lon)
    override fun equals(other: Any?): Boolean {
        other as Coordinate
        return this.lat == other.lat && this.lon == other?.lon
        return super.equals(other)
    }
}