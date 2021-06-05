package com.androidmapsextensions

interface TileOverlay {
    fun clearTileCache()
    fun <T> getData(): T
    var fadeIn: Boolean
    @get:Deprecated("")
    val id: String?

    var zIndex: Float
    var transparency: Float
    var isVisible: Boolean
    fun remove()
    fun setData(data: Any?)
}
