package com.androidmapsextensions

import android.content.Context
import com.google.android.gms.maps.GoogleMapOptions
import android.os.Bundle

class SupportMapFragment : com.google.android.gms.maps.SupportMapFragment(), MapHolder.Delegate {
    private val mapHolder = MapHolder(this)
    val extendedMap: GoogleMap
        get() = mapHolder.extendedMap

    fun getExtendedMapAsync(callback: OnMapReadyCallback?) {
        mapHolder.getExtendedMapAsync(callback)
    }

    override fun getContext(): Context? {
        return activity
    }

    companion object {
        // value taken from google-play-services.jar
        private const val MAP_OPTIONS = "MapOptions"
        fun newInstance(): SupportMapFragment {
            return SupportMapFragment()
        }

        fun newInstance(options: GoogleMapOptions?): SupportMapFragment {
            val f = SupportMapFragment()
            val args = Bundle()
            args.putParcelable(MAP_OPTIONS, options)
            f.arguments = args
            return f
        }
    }
}
