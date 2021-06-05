package com.androidmapsextensions

import com.google.android.gms.maps.model.BitmapDescriptor
import com.google.android.gms.maps.model.LatLng
import com.google.android.gms.maps.model.LatLngBounds

interface GroundOverlay {
    var bearing: Float
    val bounds: LatLngBounds?
    fun <T> getData(): T
    val height: Float
    @get:Deprecated("")
    val id: String?

    var position: LatLng?
    var tag: Any?
    var transparency: Float
    val width: Float
    var zIndex: Float
    var isClickable: Boolean
    var isVisible: Boolean
    fun remove()
    fun setData(data: Any?)
    fun setDimensions(width: Float, height: Float)
    fun setDimensions(width: Float)
    fun setImage(image: BitmapDescriptor?)
    fun setPositionFromBounds(bounds: LatLngBounds?)
}
