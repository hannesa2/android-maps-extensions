package com.androidmapsextensions

import android.content.Context
import android.os.Bundle
import com.google.android.gms.maps.GoogleMapOptions

class MapFragment : com.google.android.gms.maps.MapFragment(), MapHolder.Delegate {
    private val mapHolder = MapHolder(this)
    val extendedMap: GoogleMap
        get() = mapHolder.extendedMap

    fun getExtendedMapAsync(callback: OnMapReadyCallback?) {
        mapHolder.getExtendedMapAsync(callback)
    }

    companion object {
        // value taken from google-play-services.jar
        private const val MAP_OPTIONS = "MapOptions"

        fun newInstance(): MapFragment {
            return MapFragment()
        }

        fun newInstance(options: GoogleMapOptions?): MapFragment {
            val f = MapFragment()
            val args = Bundle()
            args.putParcelable(MAP_OPTIONS, options)
            f.arguments = args
            return f
        }
    }
}
