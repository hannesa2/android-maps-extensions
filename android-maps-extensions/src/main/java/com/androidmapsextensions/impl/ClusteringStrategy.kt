package com.androidmapsextensions.impl

import com.google.android.gms.maps.model.CameraPosition
import com.google.android.gms.maps.model.Marker

internal interface ClusteringStrategy {
    fun cleanup()
    fun onCameraChange(cameraPosition: CameraPosition)
    fun onClusterGroupChange(marker: DelegatingMarker)
    fun onAdd(marker: DelegatingMarker)
    fun onRemove(marker: DelegatingMarker)
    fun onPositionChange(marker: DelegatingMarker)
    fun onVisibilityChangeRequest(marker: DelegatingMarker, visible: Boolean)
    fun onShowInfoWindow(marker: DelegatingMarker)
    fun map(original: Marker): com.androidmapsextensions.Marker
    val displayedMarkers: List<com.androidmapsextensions.Marker>
    fun getMinZoomLevelNotClustered(marker: com.androidmapsextensions.Marker): Float
}
