package com.androidmapsextensions

import com.google.android.gms.maps.model.LatLng
import com.google.android.gms.maps.model.PatternItem

interface Polygon {
    fun <T> getData(): T
    var fillColor: Int
    var holes: List<List<LatLng?>?>?
    @get:Deprecated("")
    val id: String?

    var points: List<LatLng?>?
    var strokeColor: Int
    var strokeJointType: Int
    var strokePattern: List<PatternItem?>?
    var strokeWidth: Float
    var tag: Any?
    var zIndex: Float
    var isClickable: Boolean
    var isGeodesic: Boolean
    var isVisible: Boolean
    fun remove()
    fun setData(data: Any?)
}
