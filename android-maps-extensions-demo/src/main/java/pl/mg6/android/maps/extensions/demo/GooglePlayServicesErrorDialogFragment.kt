package pl.mg6.android.maps.extensions.demo

import android.app.Dialog
import pl.mg6.android.maps.extensions.demo.ToastHelper.showToast
import android.os.Bundle
import androidx.fragment.app.DialogFragment
import androidx.fragment.app.FragmentActivity
import com.google.android.gms.common.GooglePlayServicesUtil
import com.google.android.gms.common.ConnectionResult

class GooglePlayServicesErrorDialogFragment : DialogFragment() {
    override fun onCreateDialog(savedInstanceState: Bundle?): Dialog {
        val args = arguments
        var status = 0
        if (args != null) {
            status = args.getInt(KEY_STATUS)
        }
        return GooglePlayServicesUtil.getErrorDialog(status, activity, 0)
    }

    companion object {
        private val TAG = GooglePlayServicesErrorDialogFragment::class.java.simpleName
        private const val KEY_STATUS = "status"
        fun newInstance(status: Int): GooglePlayServicesErrorDialogFragment {
            val f = GooglePlayServicesErrorDialogFragment()
            val args = Bundle()
            args.putInt(KEY_STATUS, status)
            f.arguments = args
            return f
        }

        fun showDialog(status: Int, activity: FragmentActivity) {
            val fm = activity.supportFragmentManager
            val f = newInstance(status)
            f.show(fm, TAG)
        }

        fun showDialogIfNotAvailable(activity: FragmentActivity): Boolean {
            removeDialog(activity)
            val status = GooglePlayServicesUtil.isGooglePlayServicesAvailable(activity)
            val available = status == ConnectionResult.SUCCESS
            if (!available) {
                if (GooglePlayServicesUtil.isUserRecoverableError(status)) {
                    showDialog(status, activity)
                } else {
                    showToast(activity.application, "Google Play services not available")
                }
            }
            return available
        }

        fun removeDialog(activity: FragmentActivity) {
            val fm = activity.supportFragmentManager
            val f = fm.findFragmentByTag(TAG)
            if (f != null) {
                fm.beginTransaction().remove(f).commit()
            }
        }
    }
}