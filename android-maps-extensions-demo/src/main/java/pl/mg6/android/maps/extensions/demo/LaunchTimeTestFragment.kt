package pl.mg6.android.maps.extensions.demo

import android.os.Bundle
import android.os.SystemClock
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import com.google.android.gms.maps.GoogleMapOptions
import com.google.android.gms.maps.model.CameraPosition
import com.google.android.gms.maps.model.LatLng
import com.androidmapsextensions.ClusteringSettings
import com.androidmapsextensions.MarkerOptions
import com.androidmapsextensions.SupportMapFragment
import java.util.*

class LaunchTimeTestFragment : BaseFragment() {

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        return inflater.inflate(R.layout.launch_time_test, container, false)
    }

    override fun createMapFragment(): SupportMapFragment {
        val options = GoogleMapOptions()
        options.camera(CameraPosition.builder().target(LatLng(0.0, 0.0)).zoom(4.0f).build())
        return SupportMapFragment.newInstance(options)
    }

    override fun setUpMap() {
        var clusteringType = 0
        if (arguments != null) {
            clusteringType = requireArguments().getInt(EXTRA_CLUSTERING_TYPE, CLUSTERING_DISABLED)
        }
        val settings = ClusteringSettings()
        when (clusteringType) {
            CLUSTERING_DISABLED_DYNAMIC -> settings.enabled(false).addMarkersDynamically(true)
            CLUSTERING_ENABLED -> settings.clusterOptionsProvider(DemoClusterOptionsProvider(resources))
            CLUSTERING_ENABLED_DYNAMIC -> settings.clusterOptionsProvider(DemoClusterOptionsProvider(resources)).addMarkersDynamically(true)
            else -> settings.enabled(false)
        }
        map!!.setClustering(settings)
        val r = Random(0)
        val options = MarkerOptions()
        val start = SystemClock.uptimeMillis()
        for (i in 0 until MARKERS_COUNT) {
            val position = LatLng(r.nextDouble() * 170 - 85, r.nextDouble() * 360 - 180)
            map!!.addMarker(options.position(position))
        }
        val end = SystemClock.uptimeMillis()
        val time = end - start
        val zoom = map!!.cameraPosition.zoom
        val format = "Time adding %d markers (option: %d, zoom: %.1f): %d"
        val text = String.format(Locale.US, format, MARKERS_COUNT, clusteringType, zoom, time)
        Log.i(TAG, text)
    }

    companion object {
        private val TAG = LaunchTimeTestFragment::class.java.simpleName
        private const val EXTRA_CLUSTERING_TYPE = "clusteringType"
        const val CLUSTERING_DISABLED = 0
        const val CLUSTERING_DISABLED_DYNAMIC = 1
        const val CLUSTERING_ENABLED = 2
        const val CLUSTERING_ENABLED_DYNAMIC = 3
        private const val MARKERS_COUNT = 20000
        fun newInstance(clusteringType: Int): LaunchTimeTestFragment {
            val launchTimeTestFragment = LaunchTimeTestFragment()
            val args = Bundle()
            args.putInt(EXTRA_CLUSTERING_TYPE, clusteringType)
            launchTimeTestFragment.arguments = args
            return launchTimeTestFragment
        }
    }
}
