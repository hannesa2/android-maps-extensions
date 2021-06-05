package com.androidmapsextensions

import com.google.android.gms.maps.model.BitmapDescriptor
import com.google.android.gms.maps.model.LatLng

interface Marker {
    interface AnimationCallback {
        enum class CancelReason {
            ANIMATE_POSITION, DRAG_START, REMOVE, SET_POSITION
        }

        fun onFinish(marker: Marker?)
        fun onCancel(marker: Marker?, reason: CancelReason?)
    }

    fun animatePosition(target: LatLng?)
    fun animatePosition(target: LatLng?, settings: AnimationSettings?)
    fun animatePosition(target: LatLng?, callback: AnimationCallback?)
    fun animatePosition(target: LatLng?, settings: AnimationSettings?, callback: AnimationCallback?)
    var alpha: Float
    var clusterGroup: Int
    /**
     * WARNING: may be changed in future API when this is fixed: http://code.google.com/p/gmaps-api-issues/issues/detail?id=4650
     */
    fun <T> getData(): T

    /**
     * http://code.google.com/p/gmaps-api-issues/issues/detail?id=5101
     */
    @get:Deprecated("")
    val id: String?

    /**
     * @return list of markers inside cluster when isCluster() returns true, null otherwise
     */
    val markers: MutableList<Marker>

    var position: LatLng
    var rotation: Float
    var snippet: String?
    /**
     * Calling this method forced marker to be created via Google Maps Android API
     * and disables most of the optimizations when using clustering and addMarkersDynamically method.
     * Use setData instead whenever possible.
     */
    /**
     * Calling this method forced marker to be created via Google Maps Android API
     * and disables most of the optimizations when using clustering and addMarkersDynamically method.
     * Use setData instead whenever possible.
     */
    @get:Deprecated("")
    @set:Deprecated("")
    var tag: Any?

    var title: String?
    var zIndex: Float
    fun hideInfoWindow()
    /**
     * @return true if this marker groups other markers, false otherwise
     */
    val isCluster: Boolean

    var isDraggable: Boolean
    var isFlat: Boolean
    val isInfoWindowShown: Boolean
    var isVisible: Boolean
    fun remove()
    fun setAnchor(anchorU: Float, anchorV: Float)
    /**
     * WARNING: may be changed in future API when this is fixed: http://code.google.com/p/gmaps-api-issues/issues/detail?id=4650
     */
    fun setData(data: Any?)

    fun setIcon(icon: BitmapDescriptor?)
    fun setInfoWindowAnchor(anchorU: Float, anchorV: Float)

    fun showInfoWindow()
}
