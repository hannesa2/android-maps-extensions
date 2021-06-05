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
package pl.mg6.android.maps.extensions.demo

import android.os.Bundle
import android.view.View
import androidx.fragment.app.Fragment
import com.androidmapsextensions.GoogleMap
import com.androidmapsextensions.OnMapReadyCallback
import com.androidmapsextensions.SupportMapFragment

abstract class BaseFragment : Fragment(), OnMapReadyCallback {

    private var mapFragment: SupportMapFragment? = null
    @JvmField
    protected var map: GoogleMap? = null
    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        createMapFragmentIfNeeded()
    }

    override fun onResume() {
        super.onResume()
        setUpMapIfNeeded()
    }

    private fun createMapFragmentIfNeeded() {
        val fm = childFragmentManager
        mapFragment = fm.findFragmentById(R.id.map_container) as SupportMapFragment
        if (mapFragment == null) {
            mapFragment = createMapFragment()
            val tx = fm.beginTransaction()
            tx.add(R.id.map_container, mapFragment!!)
            tx.commit()
        }
    }

    protected open fun createMapFragment(): SupportMapFragment? {
        return SupportMapFragment.newInstance()
    }

    private fun setUpMapIfNeeded() {
        if (map == null) {
            mapFragment!!.getExtendedMapAsync(this)
        }
    }

    protected abstract fun setUpMap()
    override fun onMapReady(googleMap: GoogleMap) {
        map = googleMap
        setUpMap()
    }
}
