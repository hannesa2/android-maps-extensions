package pl.mg6.android.maps.extensions.demo

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import com.androidmapsextensions.*
import com.google.android.gms.maps.model.BitmapDescriptorFactory
import com.google.android.gms.maps.model.LatLng

class ClusterGroupsFragment : BaseFragment() {
    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        return inflater.inflate(R.layout.simple_map, container, false)
    }

    override fun setUpMap() {
        map!!.setClustering(ClusteringSettings().clusterOptionsProvider(object : ClusterOptionsProvider {
            override fun getClusterOptions(markers: List<Marker?>?): ClusterOptions? {
                val hue: Float = if (markers!![0]!!.clusterGroup == DYNAMIC_GROUP) {
                    BitmapDescriptorFactory.HUE_ORANGE
                } else {
                    BitmapDescriptorFactory.HUE_ROSE
                }
                val blueIcon = BitmapDescriptorFactory.defaultMarker(hue)
                return ClusterOptions().icon(blueIcon)
            }
        }))
        map!!.addMarker(MarkerOptions().position(LatLng(0.0, 0.0)))
        map!!.addMarker(MarkerOptions().position(LatLng(3.0, 1.0)))
        map!!.addMarker(MarkerOptions().position(LatLng(2.0, 0.5)))
        map!!.addMarker(MarkerOptions().position(LatLng(0.5, 2.0)))
        val greenIcon = BitmapDescriptorFactory.defaultMarker(BitmapDescriptorFactory.HUE_GREEN)
        val single = map!!.addMarker(MarkerOptions().position(LatLng(10.0, 10.0)).icon(greenIcon).clusterGroup(ClusterGroup.NOT_CLUSTERED))
        map!!.setOnMapClickListener(object : GoogleMap.OnMapClickListener {
            override fun onMapClick(position: LatLng) {
                single.position = position
            }
        })
        map!!.setOnMapLongClickListener(object : GoogleMap.OnMapLongClickListener {
            override fun onMapLongClick(position: LatLng) {
                val yellowIcon = BitmapDescriptorFactory.defaultMarker(BitmapDescriptorFactory.HUE_YELLOW)
                map!!.addMarker(MarkerOptions().position(position).icon(yellowIcon).clusterGroup(DYNAMIC_GROUP))
            }
        })
    }

    companion object {
        private const val DYNAMIC_GROUP = ClusterGroup.FIRST_USER
    }
}
