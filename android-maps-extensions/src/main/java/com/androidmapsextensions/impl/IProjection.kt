package com.androidmapsextensions.impl

import android.graphics.Point
import com.google.android.gms.maps.Projection
import com.google.android.gms.maps.model.LatLng
import com.google.android.gms.maps.model.VisibleRegion

//TODO: to be deleted when com.google.android.gms.maps.Projection becomes an interface
internal interface IProjection {
    fun fromScreenLocation(point: Point?): LatLng?
    val visibleRegion: VisibleRegion?
    fun toScreenLocation(location: LatLng?): Point?
    val projection: Projection?
}
