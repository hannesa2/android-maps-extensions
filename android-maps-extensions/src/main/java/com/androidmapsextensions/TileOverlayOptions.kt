package com.androidmapsextensions

import com.google.android.gms.maps.model.TileProvider

class TileOverlayOptions {
    @JvmField
    val real = com.google.android.gms.maps.model.TileOverlayOptions()
    var data: Any? = null
        private set

    fun data(data: Any?): TileOverlayOptions {
        this.data = data
        return this
    }

    fun fadeIn(fadeIn: Boolean): TileOverlayOptions {
        real.fadeIn(fadeIn)
        return this
    }

    val fadeIn: Boolean
        get() = real.fadeIn
    val tileProvider: TileProvider
        get() = real.tileProvider
    val transparency: Float
        get() = real.transparency
    val zIndex: Float
        get() = real.zIndex
    val isVisible: Boolean
        get() = real.isVisible

    fun tileProvider(tileProvider: TileProvider?): TileOverlayOptions {
        real.tileProvider(tileProvider)
        return this
    }

    fun transparency(transparency: Float): TileOverlayOptions {
        real.transparency(transparency)
        return this
    }

    fun visible(visible: Boolean): TileOverlayOptions {
        real.visible(visible)
        return this
    }

    fun zIndex(zIndex: Float): TileOverlayOptions {
        real.zIndex(zIndex)
        return this
    }
}
