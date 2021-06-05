package com.androidmapsextensions.lazy

import kotlin.jvm.JvmOverloads
import com.google.android.gms.maps.GoogleMap
import com.google.android.gms.maps.model.LatLng
import com.google.android.gms.maps.model.BitmapDescriptor
import com.google.android.gms.maps.model.Marker
import com.google.android.gms.maps.model.MarkerOptions

class LazyMarker @JvmOverloads constructor(map: GoogleMap?, options: MarkerOptions, listener: OnMarkerCreateListener? = null) {

    interface OnMarkerCreateListener {
        fun onMarkerCreate(marker: LazyMarker?)
    }

    var marker: Marker? = null
        private set
    private var map: GoogleMap? = null
    private var markerOptions: MarkerOptions? = null
    private var listener: OnMarkerCreateListener? = null
    var alpha: Float
        get() = if (marker != null) {
            marker!!.alpha
        } else {
            markerOptions!!.alpha
        }
        set(alpha) {
            marker?.let {
                it.alpha = alpha
            } ?: run {
                markerOptions!!.alpha(alpha)
            }
        }

    @get:Deprecated("")
    val id: String
        get() {
            createMarker()
            return marker!!.id
        }
    var position: LatLng?
        get() = if (marker != null) {
            marker!!.position
        } else {
            markerOptions!!.position
        }
        set(position) {
            if (marker != null) {
                marker!!.position = position
            } else {
                markerOptions!!.position(position)
            }
        }
    var rotation: Float
        get() = if (marker != null) {
            marker!!.rotation
        } else {
            markerOptions!!.rotation
        }
        set(rotation) {
            if (marker != null) {
                marker!!.rotation = rotation
            } else {
                markerOptions!!.rotation(rotation)
            }
        }
    var snippet: String?
        get() = if (marker != null) {
            marker!!.snippet
        } else {
            markerOptions!!.snippet
        }
        set(snippet) {
            if (marker != null) {
                marker!!.setSnippet(snippet)
            } else {
                markerOptions!!.snippet(snippet)
            }
        }

    @get:Deprecated("")
    @set:Deprecated("")
    var tag: Any?
        get() {
            createMarker()
            return marker!!.tag
        }
        set(tag) {
            createMarker()
            marker!!.tag = tag
        }
    var title: String?
        get() = if (marker != null) {
            marker!!.title
        } else {
            markerOptions!!.title
        }
        set(title) {
            if (marker != null) {
                marker!!.setTitle(title)
            } else {
                markerOptions!!.title(title)
            }
        }
    val zIndex: Float
        get() = if (marker != null) {
            marker!!.zIndex
        } else {
            markerOptions!!.zIndex
        }

    fun hideInfoWindow() {
        if (marker != null) {
            marker!!.hideInfoWindow()
        }
    }

    var isDraggable: Boolean
        get() = if (marker != null) {
            marker!!.isDraggable
        } else {
            markerOptions!!.isDraggable
        }
        set(draggable) {
            if (marker != null) {
                marker!!.isDraggable = draggable
            } else {
                markerOptions!!.draggable(draggable)
            }
        }
    var isFlat: Boolean
        get() = if (marker != null) {
            marker!!.isFlat
        } else {
            markerOptions!!.isFlat
        }
        set(flat) {
            if (marker != null) {
                marker!!.isFlat = flat
            } else {
                markerOptions!!.flat(flat)
            }
        }
    val isInfoWindowShown: Boolean
        get() = if (marker != null) {
            marker!!.isInfoWindowShown
        } else {
            false
        }
    var isVisible: Boolean
        get() = if (marker != null) {
            marker!!.isVisible
        } else {
            false
        }
        set(visible) {
            if (marker != null) {
                marker!!.isVisible = visible
            } else if (visible) {
                markerOptions!!.visible(true)
                createMarker()
            }
        }

    fun remove() {
        if (marker != null) {
            marker!!.remove()
            marker = null
        } else {
            map = null
            markerOptions = null
            listener = null
        }
    }

    fun setAnchor(anchorU: Float, anchorV: Float) {
        marker?.setAnchor(anchorU, anchorV) ?: run {
            markerOptions!!.anchor(anchorU, anchorV)
        }
    }

    fun setIcon(icon: BitmapDescriptor?) {
        marker?.setIcon(icon) ?: run {
            markerOptions!!.icon(icon)
        }
    }

    fun setInfoWindowAnchor(anchorU: Float, anchorV: Float) {
        marker?.setInfoWindowAnchor(anchorU, anchorV) ?: run {
            markerOptions!!.infoWindowAnchor(anchorU, anchorV)
        }
    }

    fun zIndex(zIndex: Float) {
        marker?.let {
            marker!!.zIndex = zIndex
        } ?: run {
            markerOptions!!.zIndex(zIndex)
        }
    }

    fun showInfoWindow() {
        if (marker != null) {
            marker!!.showInfoWindow()
        }
    }

    private fun createMarker() {
        if (marker == null) {
            createMarker(map, markerOptions, listener)
            map = null
            markerOptions = null
            listener = null
        }
    }

    private fun createMarker(map: GoogleMap?, options: MarkerOptions?, listener: OnMarkerCreateListener?) {
        marker = map!!.addMarker(options)
        listener?.onMarkerCreate(this)
    }

    companion object {
        private var GOOGLE_PLAY_SERVICES_4_0 = true
        private var GOOGLE_PLAY_SERVICES_9_2 = true
        private fun copy(options: MarkerOptions): MarkerOptions {
            val copy = MarkerOptions()
            if (GOOGLE_PLAY_SERVICES_4_0) {
                try {
                    copy.alpha(options.alpha)
                } catch (error: NoSuchMethodError) {
                    // not the cutest way to handle backward compatibility
                    GOOGLE_PLAY_SERVICES_4_0 = false
                }
            }
            copy.anchor(options.anchorU, options.anchorV)
            copy.draggable(options.isDraggable)
            copy.flat(options.isFlat)
            copy.icon(options.icon)
            copy.infoWindowAnchor(options.infoWindowAnchorU, options.infoWindowAnchorV)
            copy.position(options.position)
            copy.rotation(options.rotation)
            copy.snippet(options.snippet)
            copy.title(options.title)
            copy.visible(options.isVisible)
            if (GOOGLE_PLAY_SERVICES_9_2) {
                try {
                    copy.zIndex(options.zIndex)
                } catch (error: NoSuchMethodError) {
                    GOOGLE_PLAY_SERVICES_9_2 = false
                }
            }
            return copy
        }
    }

    init {
        if (options.isVisible) {
            createMarker(map, options, listener)
        } else {
            this.map = map
            markerOptions = copy(options)
            this.listener = listener
        }
    }
}
