package pl.mg6.android.maps.extensions.demo

import android.content.Context
import android.widget.Toast

internal object ToastHelper {
    @JvmStatic
    fun showToast(context: Context?, text: String?) {
        if (context != null) {
            Toast.makeText(context, text, Toast.LENGTH_SHORT).show()
        }
    }
}
