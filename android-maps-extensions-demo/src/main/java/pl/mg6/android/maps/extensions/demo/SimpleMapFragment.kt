package pl.mg6.android.maps.extensions.demo

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import com.androidmapsextensions.ClusteringSettings
import com.androidmapsextensions.GoogleMap
import com.androidmapsextensions.Marker
import com.androidmapsextensions.MarkerOptions
import com.google.android.gms.maps.model.LatLng

class SimpleMapFragment : BaseFragment() {

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        return inflater.inflate(R.layout.simple_map, container, false)
    }

    override fun setUpMap() {
        map!!.setClustering(ClusteringSettings())
        map!!.addMarker(MarkerOptions().position(LatLng(0.0, 0.0)))
        map!!.addMarker(MarkerOptions().position(LatLng(15.0, 3.0)))
        map!!.addMarker(MarkerOptions().position(LatLng(14.99, 3.01)))
        map!!.setOnMarkerClickListener(object : GoogleMap.OnMarkerClickListener {
            override fun onMarkerClick(marker: Marker?): Boolean {
                ToastHelper.showToast(activity, "Clicked marker at: " + marker!!.position)
                return false
            }
        })
    }
}
