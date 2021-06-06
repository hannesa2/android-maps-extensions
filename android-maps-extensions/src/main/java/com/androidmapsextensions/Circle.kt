package com.androidmapsextensions

import com.google.android.gms.maps.model.LatLng
import com.google.android.gms.maps.model.PatternItem

interface Circle {
    operator fun contains(position: LatLng?): Boolean
    var center: LatLng?
    fun <T> getData(): T
    var fillColor: Int

    @get:Deprecated("Who needs this ?")
    val id: String?

    var radius: Double
    var strokeColor: Int
    var strokePattern: List<PatternItem?>?
    var strokeWidth: Float
    var tag: Any?
    var zIndex: Float
    var isClickable: Boolean
    var isVisible: Boolean
    fun remove()
    fun setData(data: Any)
}
