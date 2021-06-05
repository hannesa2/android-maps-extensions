package pl.mg6.android.maps.extensions.demo

import android.os.Bundle
import android.view.MenuItem
import android.widget.AdapterView
import android.widget.ArrayAdapter
import android.widget.ListView
import androidx.appcompat.app.ActionBarDrawerToggle
import androidx.appcompat.app.AppCompatActivity
import androidx.drawerlayout.widget.DrawerLayout
import androidx.fragment.app.Fragment

class MainActivity : AppCompatActivity() {

    private lateinit var drawerLayout: DrawerLayout
    private lateinit var toggle: ActionBarDrawerToggle

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.main)
        drawerLayout = findViewById(R.id.drawer)

        toggle = ActionBarDrawerToggle(this, drawerLayout, R.string.open_drawer, R.string.close_drawer)
        drawerLayout.addDrawerListener(toggle)
        supportActionBar?.apply {
            setDisplayHomeAsUpEnabled(true)
            setHomeButtonEnabled(true)
        }

        val screens = arrayOf(
            "Demo", "Animate markers", "Cluster groups", "Declusterification",
            "Grid clustering (dynamic)", "No clustering (dynamic)", "Grid clustering", "No clustering"
        )
        val adapter = ArrayAdapter(this, android.R.layout.simple_list_item_1, screens)
        val listView = findViewById<ListView>(R.id.list)
        listView.adapter = adapter
        listView.onItemClickListener = AdapterView.OnItemClickListener { _, _, position, _ ->
            if (GooglePlayServicesErrorDialogFragment.showDialogIfNotAvailable(this@MainActivity)) {
                startExample(position)
            }
        }
        if (savedInstanceState == null) {
            if (GooglePlayServicesErrorDialogFragment.showDialogIfNotAvailable(this)) {
                replaceMainFragment(DemoFragment())
            }
        }
    }

    private fun startExample(position: Int) {
        when (position) {
            0 -> replaceMainFragment(DemoFragment())
            1 -> replaceMainFragment(AnimateMarkersFragment())
            2 -> replaceMainFragment(ClusterGroupsFragment())
            3 -> replaceMainFragment(DeClusterificationExampleFragment())
            else -> {
                val clusteringType: Int = when (position) {
                    4 -> LaunchTimeTestFragment.CLUSTERING_ENABLED_DYNAMIC
                    5 -> LaunchTimeTestFragment.CLUSTERING_DISABLED_DYNAMIC
                    6 -> LaunchTimeTestFragment.CLUSTERING_ENABLED
                    else -> LaunchTimeTestFragment.CLUSTERING_DISABLED
                }
                val fragment: Fragment = LaunchTimeTestFragment.newInstance(clusteringType)
                replaceMainFragment(fragment)
            }
        }
        drawerLayout.closeDrawers()
    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        return if (toggle.onOptionsItemSelected(item)) {
            true
        } else super.onOptionsItemSelected(item)
    }

    override fun onPostCreate(savedInstanceState: Bundle?) {
        super.onPostCreate(savedInstanceState)
        toggle.syncState()
    }

    private fun replaceMainFragment(fragment: Fragment) {
        val fm = supportFragmentManager
        val tx = fm.beginTransaction()
        tx.replace(R.id.main_container, fragment)
        tx.commit()
    }
}
