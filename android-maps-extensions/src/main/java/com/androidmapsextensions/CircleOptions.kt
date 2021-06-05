package com.androidmapsextensions

import com.google.android.gms.maps.model.LatLng
import com.google.android.gms.maps.model.PatternItem

@Suppress("unused")
class CircleOptions {
    val real = com.google.android.gms.maps.model.CircleOptions()
    var data: Any? = null
        private set

    fun center(center: LatLng): CircleOptions {
        real.center(center)
        return this
    }

    fun clickable(clickable: Boolean): CircleOptions {
        real.clickable(clickable)
        return this
    }

    fun data(data: Any): CircleOptions {
        this.data = data
        return this
    }

    fun fillColor(color: Int): CircleOptions {
        real.fillColor(color)
        return this
    }

    val center: LatLng?
        get() = real.center
    val fillColor: Int
        get() = real.fillColor
    val radius: Double
        get() = real.radius
    val strokeColor: Int
        get() = real.strokeColor
    val strokePattern: List<PatternItem>?
        get() = real.strokePattern
    val strokeWidth: Float
        get() = real.strokeWidth
    val zIndex: Float
        get() = real.zIndex
    val isClickable: Boolean
        get() = real.isClickable
    val isVisible: Boolean
        get() = real.isVisible

    fun radius(radius: Double): CircleOptions {
        real.radius(radius)
        return this
    }

    fun strokeColor(color: Int): CircleOptions {
        real.strokeColor(color)
        return this
    }

    fun strokePattern(pattern: List<PatternItem?>?): CircleOptions {
        real.strokePattern(pattern)
        return this
    }

    fun strokeWidth(width: Float): CircleOptions {
        real.strokeWidth(width)
        return this
    }

    fun visible(visible: Boolean): CircleOptions {
        real.visible(visible)
        return this
    }

    fun zIndex(zIndex: Float): CircleOptions {
        real.zIndex(zIndex)
        return this
    }
}
