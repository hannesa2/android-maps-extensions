package com.androidmapsextensions

interface ClusterOptionsProvider {
    fun getClusterOptions(markers: List<Marker?>?): ClusterOptions?
}
