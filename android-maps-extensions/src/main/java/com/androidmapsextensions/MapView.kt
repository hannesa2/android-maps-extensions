package com.androidmapsextensions

import android.content.Context
import android.util.AttributeSet
import com.google.android.gms.maps.MapView

class MapView : MapView, MapHolder.Delegate {
    private val mapHolder = MapHolder(this)

    constructor(context: Context) : super(context) {}
    constructor(context: Context, attrs: AttributeSet?) : super(context, attrs) {}
    constructor(context: Context, attrs: AttributeSet, defStyle: Int) : super(context, attrs, defStyle) {}

    val extendedMap: GoogleMap
        get() = mapHolder.extendedMap

    fun getExtendedMapAsync(callback: OnMapReadyCallback?) {
        mapHolder.getExtendedMapAsync(callback)
    }
}
