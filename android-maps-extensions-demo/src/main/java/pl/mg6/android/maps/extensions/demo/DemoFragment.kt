package pl.mg6.android.maps.extensions.demo

import android.annotation.SuppressLint
import android.graphics.Color
import android.os.Bundle
import android.os.Handler
import android.os.Looper
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.CheckBox
import android.widget.SeekBar
import android.widget.TextView
import com.androidmapsextensions.*
import com.google.android.gms.maps.model.LatLng
import com.google.android.gms.maps.model.LatLngBounds
import com.google.android.gms.maps.CameraUpdateFactory
import com.google.android.gms.maps.model.BitmapDescriptorFactory
import java.text.Collator
import java.util.*

class DemoFragment : BaseFragment() {
    private val dataArray = arrayOf(
        MutableData(6, LatLng(-50.0, 0.0)),
        MutableData(28, LatLng(-52.0, 1.0)),
        MutableData(496, LatLng(-51.0, -2.0))
    )

    private val handler = Handler(Looper.getMainLooper())
    private val dataUpdater: Runnable = object : Runnable {
        override fun run() {
            for (data in dataArray) {
                data.value = 7 + 3 * data.value
            }
            onDataUpdate()
            handler.postDelayed(this, 1000)
        }
    }
    private var clusterSizeSeekbar: SeekBar? = null
    private var clusterCheckbox: CheckBox? = null
    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        return inflater.inflate(R.layout.demo, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        setUpClusteringViews(view)
    }

    override fun setUpMap() {
        addCircles()
        map!!.setOnMapClickListener(object : GoogleMap.OnMapClickListener {
            override fun onMapClick(position: LatLng) {
                for (circle in map!!.circles) {
                    if (circle.contains(position)) {
                        ToastHelper.showToast(activity, "Clicked " + circle.getData<Any>())
                        return
                    }
                }
            }
        })
        updateClustering(clusterSizeSeekbar!!.progress, clusterCheckbox!!.isChecked)
        map!!.setInfoWindowAdapter(object : GoogleMap.InfoWindowAdapter {
            private var tv: TextView? = null
            private val collator = Collator.getInstance()
            private val comparator = Comparator<Marker> { lhs, rhs ->
                val leftTitle = lhs.title
                val rightTitle = rhs.title
                if (leftTitle == null && rightTitle == null) {
                    return@Comparator 0
                }
                if (leftTitle == null) {
                    return@Comparator 1
                }
                if (rightTitle == null) {
                    -1
                } else collator.compare(leftTitle, rightTitle)
            }

            override fun getInfoWindow(marker: Marker): View? {
                return null
            }

            @SuppressLint("SetTextI18n")
            override fun getInfoContents(marker: Marker): View? {
                if (marker.isCluster) {
                    val markers = marker.markers
                    var i = 0
                    var text = ""
                    while (i < 3 && markers.isNotEmpty()) {
                        val m = Collections.min(markers, comparator)!!
                        val title = m.title ?: break
                        text += """
                            $title
                            
                            """.trimIndent()
                        markers.remove(m)
                        i++
                    }
                    if (text.isEmpty()) {
                        text = "Markers with mutable data"
                    } else if (markers.isNotEmpty()) {
                        text += "and " + markers.size + " more..."
                    } else {
                        text = text.substring(0, text.length - 1)
                    }
                    tv!!.text = text
                    return tv
                } else {
                    if (marker.getData<Any>() is MutableData) {
                        val mutableData = marker.getData<MutableData>()
                        tv!!.text = "Value: ${mutableData.value}"
                        return tv
                    }
                }
                return null
            }

            init {
                tv = TextView(activity)
                tv!!.setTextColor(Color.BLACK)
            }
        })
        map!!.setOnInfoWindowClickListener(object : GoogleMap.OnInfoWindowClickListener {
            override fun onInfoWindowClick(marker: Marker) {
                if (marker.isCluster) {
                    val markers = marker.markers
                    val builder = LatLngBounds.builder()
                    markers.forEach {
                        builder.include(it.position)
                    }
                    val bounds = builder.build()
                    map!!.animateCamera(CameraUpdateFactory.newLatLngBounds(bounds, resources.getDimensionPixelSize(R.dimen.padding)))
                }
            }
        })
        MarkerGenerator.addMarkersInPoland(map)
        MarkerGenerator.addMarkersInWorld(map)
        val icon = BitmapDescriptorFactory.defaultMarker(BitmapDescriptorFactory.HUE_BLUE)
        for (data in dataArray) {
            map!!.addMarker(MarkerOptions().position(data.position).icon(icon).data(data))
        }
    }

    override fun onResume() {
        super.onResume()
        handler.post(dataUpdater)
    }

    override fun onPause() {
        super.onPause()
        handler.removeCallbacks(dataUpdater)
    }

    private fun onDataUpdate() {
        val m = map!!.markerShowingInfoWindow
        if (!m.isCluster && m.getData<Any>() is MutableData) {
            m.showInfoWindow()
        }
    }

    private fun addCircles() {
        val strokeWidth = resources.getDimension(R.dimen.circle_stroke_width)
        val options = CircleOptions().strokeWidth(strokeWidth)
        map!!.addCircle(options.center(LatLng(0.0, 0.0)).data("first circle").radius(2000000.0))
        map!!.addCircle(options.center(LatLng(30.0, 30.0)).data("second circle").radius(1000000.0))
    }

    private fun setUpClusteringViews(view: View) {
        clusterCheckbox = view.findViewById<View>(R.id.checkbox_cluster) as CheckBox
        clusterSizeSeekbar = view.findViewById<View>(R.id.seekbar_cluster_size) as SeekBar
        clusterCheckbox!!.setOnCheckedChangeListener { _, isChecked ->
            clusterSizeSeekbar!!.isEnabled = isChecked
            updateClustering(clusterSizeSeekbar!!.progress, isChecked)
        }
        clusterSizeSeekbar!!.setOnSeekBarChangeListener(object : SeekBar.OnSeekBarChangeListener {
            override fun onStartTrackingTouch(seekBar: SeekBar) {}
            override fun onProgressChanged(seekBar: SeekBar, progress: Int, fromUser: Boolean) {
                updateClustering(progress, clusterCheckbox!!.isChecked)
            }

            override fun onStopTrackingTouch(seekBar: SeekBar) {}
        })
    }

    fun updateClustering(clusterSizeIndex: Int, enabled: Boolean) {
        if (map == null) {
            return
        }
        val clusteringSettings = ClusteringSettings()
        clusteringSettings.addMarkersDynamically(true)
        if (enabled) {
            clusteringSettings.clusterOptionsProvider(DemoClusterOptionsProvider(resources))
            val clusterSize = CLUSTER_SIZES[clusterSizeIndex]
            clusteringSettings.clusterSize(clusterSize)
        } else {
            clusteringSettings.enabled(false)
        }
        map!!.setClustering(clusteringSettings)
    }

    private class MutableData(var value: Int, val position: LatLng)
    companion object {
        private val CLUSTER_SIZES = doubleArrayOf(180.0, 160.0, 144.0, 120.0, 96.0)
    }
}
