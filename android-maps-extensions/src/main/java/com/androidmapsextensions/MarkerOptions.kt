package com.androidmapsextensions

import com.google.android.gms.maps.model.BitmapDescriptor
import com.google.android.gms.maps.model.LatLng

class MarkerOptions {
    @JvmField
    val real = com.google.android.gms.maps.model.MarkerOptions()
    var data: Any? = null
        private set
    var clusterGroup = 0
        private set

    fun alpha(alpha: Float): MarkerOptions {
        real.alpha(alpha)
        return this
    }

    fun anchor(u: Float, v: Float): MarkerOptions {
        real.anchor(u, v)
        return this
    }

    fun clusterGroup(clusterGroup: Int): MarkerOptions {
        this.clusterGroup = clusterGroup
        return this
    }

    fun data(data: Any?): MarkerOptions {
        this.data = data
        return this
    }

    fun draggable(draggable: Boolean): MarkerOptions {
        real.draggable(draggable)
        return this
    }

    fun flat(flat: Boolean): MarkerOptions {
        real.flat(flat)
        return this
    }

    val alpha: Float
        get() = real.alpha
    val anchorU: Float
        get() = real.anchorU
    val anchorV: Float
        get() = real.anchorV
    val icon: BitmapDescriptor?
        get() = real.icon
    val infoWindowAnchorU: Float
        get() = real.infoWindowAnchorU
    val infoWindowAnchorV: Float
        get() = real.infoWindowAnchorV
    val position: LatLng
        get() = real.position
    val rotation: Float
        get() = real.rotation
    val snippet: String?
        get() = real.snippet
    val title: String?
        get() = real.title
    val zIndex: Float
        get() = real.zIndex

    fun icon(icon: BitmapDescriptor?): MarkerOptions {
        real.icon(icon)
        return this
    }

    fun infoWindowAnchor(u: Float, v: Float): MarkerOptions {
        real.infoWindowAnchor(u, v)
        return this
    }

    val isDraggable: Boolean
        get() = real.isDraggable
    val isFlat: Boolean
        get() = real.isFlat
    val isVisible: Boolean
        get() = real.isVisible

    fun position(position: LatLng): MarkerOptions {
        real.position(position)
        return this
    }

    fun rotation(rotation: Float): MarkerOptions {
        real.rotation(rotation)
        return this
    }

    fun snippet(snippet: String?): MarkerOptions {
        real.snippet(snippet)
        return this
    }

    fun title(title: String?): MarkerOptions {
        real.title(title)
        return this
    }

    fun visible(visible: Boolean): MarkerOptions {
        real.visible(visible)
        return this
    }

    fun zIndex(zIndex: Float): MarkerOptions {
        real.zIndex(zIndex)
        return this
    }
}
