package pl.mg6.android.maps.extensions.demo

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.view.animation.*
import com.google.android.gms.maps.model.LatLng
import com.google.android.gms.maps.model.BitmapDescriptorFactory
import com.androidmapsextensions.Marker.AnimationCallback.CancelReason
import com.androidmapsextensions.AnimationSettings
import com.androidmapsextensions.GoogleMap
import com.androidmapsextensions.Marker
import com.androidmapsextensions.MarkerOptions
import com.androidmapsextensions.utils.LatLngUtils
import java.lang.RuntimeException
import java.util.*

class AnimateMarkersFragment : BaseFragment() {
    private val random = Random()
    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        return inflater.inflate(R.layout.simple_map, container, false)
    }

    override fun setUpMap() {
        map!!.addMarker(
            MarkerOptions().title("RED").position(LatLng(-15.0, -15.0)).icon(BitmapDescriptorFactory.defaultMarker(BitmapDescriptorFactory.HUE_RED))
        )
        map!!.addMarker(
            MarkerOptions().title("GREEN").position(LatLng(-15.0, 15.0)).icon(BitmapDescriptorFactory.defaultMarker(BitmapDescriptorFactory.HUE_GREEN))
        )
        map!!.addMarker(
            MarkerOptions().title("BLUE").position(LatLng(15.0, -15.0)).icon(BitmapDescriptorFactory.defaultMarker(BitmapDescriptorFactory.HUE_BLUE))
        )
        map!!.addMarker(
            MarkerOptions().title("YELLOW").position(LatLng(15.0, 15.0)).icon(BitmapDescriptorFactory.defaultMarker(BitmapDescriptorFactory.HUE_YELLOW))
        )
        val callback: Marker.AnimationCallback = object : Marker.AnimationCallback {
            override fun onFinish(marker: Marker?) {
                ToastHelper.showToast(activity, "Animation finished: " + marker!!.title)
            }

            override fun onCancel(marker: Marker?, reason: CancelReason?) {
                ToastHelper.showToast(activity, "Animation canceled: " + marker!!.title + ", reason: " + reason)
            }
        }
        map!!.setOnMarkerClickListener(object : GoogleMap.OnMarkerClickListener {
            override fun onMarkerClick(marker: Marker?): Boolean {
                val position = marker!!.position
                val targetPosition = randomPositionAcrossTheOcean(position)
                val duration = (random.nextInt(1500) + 1500).toLong()
                val interpolator = randomInterpolator()
                val settings = AnimationSettings().duration(duration).interpolator(interpolator)
                marker.animatePosition(targetPosition, settings, callback)
                return true
            }
        })
        map!!.setOnMapClickListener(object : GoogleMap.OnMapClickListener {
            override fun onMapClick(position: LatLng) {
                var closest: Marker? = null
                var distanceToClosest = Float.MAX_VALUE
                for (marker in map!!.markers!!) {
                    val markerPosition = marker!!.position
                    val distance = LatLngUtils.distanceBetween(position, markerPosition)
                    if (distanceToClosest > distance) {
                        distanceToClosest = distance
                        closest = marker
                    }
                }
                closest?.remove()
            }
        })
    }

    private fun randomPositionAcrossTheOcean(position: LatLng): LatLng {
        val lat: Double = if (position.latitude < 0) {
            random.nextDouble() * 10 + 10
        } else {
            random.nextDouble() * 10 - 20
        }
        val lng: Double = if (position.longitude < 0) {
            random.nextDouble() * 10 + 10
        } else {
            random.nextDouble() * 10 - 20
        }
        return LatLng(lat, lng)
    }

    private fun randomInterpolator(): Interpolator {
        when (random.nextInt(14)) {
            0 -> return LinearInterpolator()
            1 -> return AccelerateDecelerateInterpolator()
            2 -> return AccelerateInterpolator()
            3 -> return AccelerateInterpolator(6.0f)
            4 -> return DecelerateInterpolator()
            5 -> return DecelerateInterpolator(6.0f)
            6 -> return BounceInterpolator()
            7 -> return AnticipateOvershootInterpolator()
            8 -> return AnticipateOvershootInterpolator(6.0f)
            9 -> return AnticipateInterpolator()
            10 -> return AnticipateInterpolator(6.0f)
            11 -> return OvershootInterpolator()
            12 -> return OvershootInterpolator(6.0f)
            13 -> return CycleInterpolator(1.25f)
        }
        throw RuntimeException()
    }
}
