package com.androidmapsextensions.impl

import com.androidmapsextensions.CircleOptions
import com.androidmapsextensions.GoogleMap
import com.google.android.gms.maps.model.Circle
import java.util.*

internal class CircleManager(private val factory: IGoogleMap) {
    private val circles: MutableMap<Circle?, com.androidmapsextensions.Circle> = HashMap()

    fun addCircle(circleOptions: CircleOptions): com.androidmapsextensions.Circle {
        val circle = createCircle(circleOptions.real)
        circleOptions.data?.let {
            circle.setData(it)
        }
        return circle
    }

    private fun createCircle(circleOptions: com.google.android.gms.maps.model.CircleOptions): com.androidmapsextensions.Circle {
        val real = factory.addCircle(circleOptions)
        val circle: com.androidmapsextensions.Circle = DelegatingCircle(real, this)
        circles[real] = circle
        return circle
    }

    fun clear() {
        circles.clear()
    }

    fun getCircles(): List<com.androidmapsextensions.Circle> {
        return ArrayList(circles.values)
    }

    fun onRemove(real: Circle?) {
        circles.remove(real)
    }

    fun setOnCircleClickListener(onCircleClickListener: GoogleMap.OnCircleClickListener?) {
        var realOnCircleClickListener: com.google.android.gms.maps.GoogleMap.OnCircleClickListener? = null
        if (onCircleClickListener != null) {
            realOnCircleClickListener = DelegatingOnCircleClickListener(onCircleClickListener)
        }
        factory.setOnCircleClickListener(realOnCircleClickListener)
    }

    private inner class DelegatingOnCircleClickListener(private val onCircleClickListener: GoogleMap.OnCircleClickListener) : com.google.android.gms.maps.GoogleMap.OnCircleClickListener {
        override fun onCircleClick(circle: Circle) {
            circles[circle]?.let {
                onCircleClickListener.onCircleClick(it)
            }
        }

    }

}
