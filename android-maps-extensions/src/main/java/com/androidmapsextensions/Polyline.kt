/*
 * Copyright (C) 2013 Maciej Górski
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 * http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */
package com.androidmapsextensions

import com.google.android.gms.maps.model.Cap
import com.google.android.gms.maps.model.LatLng
import com.google.android.gms.maps.model.PatternItem

interface Polyline {
    var color: Int
    fun <T> getData(): T
    var endCap: Cap?
    @get:Deprecated("")
    val id: String?

    var jointType: Int
    var pattern: List<PatternItem?>?
    var points: List<LatLng?>?
    var startCap: Cap?
    var tag: Any?
    var width: Float
    var zIndex: Float
    var isClickable: Boolean
    var isGeodesic: Boolean
    var isVisible: Boolean
    fun remove()
    fun setData(data: Any?)
}