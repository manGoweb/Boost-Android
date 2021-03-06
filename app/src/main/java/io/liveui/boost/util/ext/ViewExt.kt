package io.liveui.boost.util.ext

/**
 * Various extension functions for AppCompatActivity.
 */

import android.animation.Animator
import android.animation.AnimatorListenerAdapter
import android.content.Context
import android.view.View
import android.view.inputmethod.InputMethodManager
import androidx.annotation.StringRes
import androidx.recyclerview.widget.RecyclerView
import com.google.android.material.snackbar.Snackbar
import com.google.android.material.tabs.TabLayout


fun View.toggleVisibilityWithFade(show: Boolean) {
    visibility = if (show) View.VISIBLE else View.GONE
    animate()
            .setDuration(resources.getInteger(android.R.integer.config_shortAnimTime).toLong())
            .alpha((if (show) 1 else 0).toFloat())
            .setListener(object : AnimatorListenerAdapter() {
                override fun onAnimationEnd(animation: Animator) {
                    visibility = if (show) View.VISIBLE else View.GONE
                }
            })

}

fun View.hideKeyboard(view: View?) {
    val imm = context.getSystemService(Context.INPUT_METHOD_SERVICE) as InputMethodManager
    imm.hideSoftInputFromWindow(view?.windowToken, 0)
}

fun View.showSnackBar(message: String, duration: Int) {
    Snackbar.make(this, message, duration).show()
}

fun View.showSnackBar(@StringRes message: Int, duration: Int) {
    Snackbar.make(this, message, duration).show()
}

inline fun <reified T : RecyclerView.ItemDecoration> RecyclerView.replaceItemDecoration(decoration: T) {
    removeItemDecoration(decoration)
    addItemDecoration(decoration)
}

inline fun TabLayout.setTabSelectedListener(crossinline onReselected: (tab: TabLayout.Tab) -> Unit = {},
                                            crossinline onUnselected: (tab: TabLayout.Tab) -> Unit = {},
                                            crossinline onSelected: (tab: TabLayout.Tab) -> Unit = {}) {
    addOnTabSelectedListener(object : TabLayout.OnTabSelectedListener {
        override fun onTabReselected(tab: TabLayout.Tab) {
            onReselected(tab)
        }

        override fun onTabUnselected(tab: TabLayout.Tab) {
            onUnselected(tab)
        }

        override fun onTabSelected(tab: TabLayout.Tab) {
            onSelected(tab)
        }
    })
}