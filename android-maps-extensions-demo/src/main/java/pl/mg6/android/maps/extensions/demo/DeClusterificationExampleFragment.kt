package pl.mg6.android.maps.extensions.demo

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import com.google.android.gms.maps.CameraUpdateFactory
import com.google.android.gms.maps.model.LatLng
import com.androidmapsextensions.ClusteringSettings
import com.androidmapsextensions.ClusterGroup
import com.androidmapsextensions.GoogleMap
import com.androidmapsextensions.Marker

class DeClusterificationExampleFragment : BaseFragment() {
    private var declusterifiedMarkers: MutableList<Marker>? = null
    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        return inflater.inflate(R.layout.simple_map, container, false)
    }

    override fun setUpMap() {
        map!!.moveCamera(CameraUpdateFactory.newLatLngZoom(LatLng(51.0, 19.0), 7.0f))
        map!!.setClustering(ClusteringSettings().clusterOptionsProvider(DemoClusterOptionsProvider(resources)))
        MarkerGenerator.addMarkersInPoland(map)
        map!!.setOnMarkerClickListener(object : GoogleMap.OnMarkerClickListener {
            override fun onMarkerClick(marker: Marker?): Boolean {
                if (marker!!.isCluster) {
                    declusterify(marker)
                    return true
                }
                return false
            }
        })
        map!!.setOnMapClickListener(object : GoogleMap.OnMapClickListener {
            override fun onMapClick(position: LatLng) {
                clusterifyMarkers()
            }
        })
    }

    private fun declusterify(cluster: Marker?) {
        clusterifyMarkers()
        declusterifiedMarkers = cluster!!.markers
        val clusterPosition = cluster.position
        val distance = calculateDistanceBetweenMarkers()
        var currentDistance = -declusterifiedMarkers!!.size / 2 * distance
        for (marker in declusterifiedMarkers!!) {
            marker.setData(marker.position)
            marker.clusterGroup = ClusterGroup.NOT_CLUSTERED
            val newPosition = LatLng(clusterPosition.latitude, clusterPosition.longitude + currentDistance)
            marker.animatePosition(newPosition)
            currentDistance += distance
        }
    }

    private fun calculateDistanceBetweenMarkers(): Double {
        val projection = map!!.projection
        val point = projection!!.toScreenLocation(LatLng(0.0, 0.0))
        point.x += requireContext().resources.getDimensionPixelSize(R.dimen.distance_between_markers)
        val nextPosition = projection.fromScreenLocation(point)
        return nextPosition.longitude
    }

    private fun clusterifyMarkers() {
        if (declusterifiedMarkers != null) {
            for (marker in declusterifiedMarkers!!) {
                val position = marker.getData<LatLng>()
                marker.position = position
                marker.clusterGroup = ClusterGroup.DEFAULT
            }
            declusterifiedMarkers = null
        }
    }
}
