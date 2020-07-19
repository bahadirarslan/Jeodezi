package com.bahadirarslan.jeodezi

fun Double.toDegrees() : Double {
    return Math.toDegrees(this)
}
fun Double.toRadians() : Double {
    return Math.toRadians(this)
}
fun Double.wrap360() : Double {
    if (0<=this && this<360) return this // avoid rounding due to arithmetic ops if within range
    return (this%360+360) % 360 // sawtooth wave p:360, a:360
}

fun Double.wrap180() : Double {
    if (-180<this && this<=180) return this // avoid rounding due to arithmetic ops if within range
    return (this+540)%360-180 // sawtooth wave p:180, a:±180
}