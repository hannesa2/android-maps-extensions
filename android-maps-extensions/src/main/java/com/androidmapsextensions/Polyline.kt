package com.androidmapsextensions

import com.google.android.gms.maps.model.Cap
import com.google.android.gms.maps.model.LatLng
import com.google.android.gms.maps.model.PatternItem

interface Polyline {
    var color: Int
    fun <T> getData(): T
    var endCap: Cap?
    @get:Deprecated("")
    val id: String?

    var jointType: Int
    var pattern: List<PatternItem?>?
    var points: List<LatLng?>?
    var startCap: Cap?
    var tag: Any?
    var width: Float
    var zIndex: Float
    var isClickable: Boolean
    var isGeodesic: Boolean
    var isVisible: Boolean
    fun remove()
    fun setData(data: Any?)
}
