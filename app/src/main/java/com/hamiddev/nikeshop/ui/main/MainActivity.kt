package com.hamiddev.nikeshop.ui.main

import android.app.Dialog
import android.os.Build
import android.os.Bundle
import android.view.View
import android.view.ViewGroup
import android.view.animation.TranslateAnimation
import android.widget.EditText
import androidx.activity.viewModels
import androidx.annotation.ColorRes
import androidx.core.content.ContextCompat
import androidx.lifecycle.LiveData
import androidx.navigation.NavController
import com.google.android.material.badge.BadgeDrawable
import com.google.android.material.color.MaterialColors
import com.hamiddev.nikeshop.R
import com.hamiddev.nikeshop.common.*
import com.hamiddev.nikeshop.data.model.CartItemCount
import com.hamiddev.nikeshop.databinding.ActivityMainBinding
import dagger.hilt.android.AndroidEntryPoint
import org.greenrobot.eventbus.Subscribe
import org.greenrobot.eventbus.ThreadMode
import timber.log.Timber

@AndroidEntryPoint
class MainActivity : BaseActivity() {
    private lateinit var binding: ActivityMainBinding
    private var currentNavController: LiveData<NavController>? = null
    private val viewModel: MainViewModel by viewModels()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)
        if (savedInstanceState == null) {
            setupBottomNavigationBar()
        } // Else, need to wait for onRestoreInstanceState


        val sh = getSharedPreferences("app_settings", MODE_PRIVATE)
        Timber.i("timber main refresh_token ${sh.getString("refresh_token", "")}")
        Timber.i("timber main access_token ${sh.getString("access_token", "")}")
        Timber.i("timber main username ${sh.getString("username", "")}")

    }

    override fun onRestoreInstanceState(savedInstanceState: Bundle) {
        super.onRestoreInstanceState(savedInstanceState)
        // Now that BottomNavigationBar has restored its instance state
        // and its selectedItemId, we can proceed with setting up the
        // BottomNavigationBar with Navigation
        setupBottomNavigationBar()
    }

    /**
     * Called on first creation and when restoring state.
     */
    private fun setupBottomNavigationBar() {
        val navGraphIds =
            listOf(R.navigation.home, R.navigation.search, R.navigation.cart, R.navigation.profile)

        // Setup the bottom navigation view with a list of navigation graphs

        val controller = binding.bottomNavigation.setupWithNavController(
            navGraphIds = navGraphIds,
            fragmentManager = supportFragmentManager,
            containerId = R.id.nav_host_container,
            intent = intent
        )
        controller.observe(this) {
            it.addOnDestinationChangedListener { _, destination, _ ->
                when (destination.id) {
                    R.id.introFragment -> uiConfig(false, showStatus = false)
                    R.id.homeFragment -> uiConfig(true)
                    R.id.searchFragment -> uiConfig(true)
                    R.id.cartFragment -> uiConfig(true)
                    R.id.proFileFragment -> uiConfig(true)
                    R.id.productDetailFragment -> uiConfig(false)
                    R.id.productDetailFragment1 -> uiConfig(false)
                    R.id.productDetailFragment2 -> uiConfig(false)
                    R.id.productDetailFragment3 -> uiConfig(false)
                    R.id.productListFragment -> uiConfig(false)
                    R.id.favoriteFragment -> uiConfig(false)
                    R.id.orderHistoryFragment -> uiConfig(false)
                    R.id.loginFragment -> uiConfig(false, R.color.blue, false)
                    R.id.signUpFragment -> uiConfig(false, R.color.blue, false)
                    R.id.editProfileFragment -> uiConfig(false, R.color.blue, false)
                    R.id.shippingFragment -> uiConfig(false)
                    R.id.checkOutFragment -> uiConfig(false)
                }
            }
        }
        currentNavController = controller
    }

    private fun uiConfig(
        showBottomNavigation: Boolean,
        @ColorRes color: Int = R.color.gray,
        isLight: Boolean = true,
        showStatus: Boolean = true
    ) {
        if (showBottomNavigation) showBottomNavigation() else hideBottomNavigation()

        window.statusBarColor = ContextCompat.getColor(this, color)

        if (showStatus) showStatusBar() else hideStatusBar()

        if (!isLight) dark() else light()

        Build.VERSION_CODES.M
    }

    private fun showBottomNavigation() {
        if (binding.bottomNavigation.visibility != View.VISIBLE) animBottomNavigation(false)
    }

    private fun hideBottomNavigation() {
        if (binding.bottomNavigation.visibility == View.VISIBLE) animBottomNavigation(true)
    }

    private fun animBottomNavigation(hide: Boolean) {
        with(binding) {
            val animation = if (!hide)
                TranslateAnimation(0f, 0f, bottomNavigation.measuredHeight.toFloat(), 0f)
            else
                TranslateAnimation(0f, 0f, 0f, bottomNavigation.measuredHeight.toFloat())
            animation.duration = 60
            animation.fillAfter = false

            bottomNavigation.startAnimation(animation)
            animation.onAnimationEnd {
                bottomNavigation.visibility = if (hide) View.GONE else View.VISIBLE
            }
        }
    }

    override fun onResume() {
        super.onResume()
        viewModel.getCount()
    }

    @Subscribe(threadMode = ThreadMode.MAIN)
    fun onCartItemCountChange(cartItemCount: CartItemCount) {
        Timber.i("timber main ${cartItemCount.count}")
        val badge = binding.bottomNavigation.getOrCreateBadge(R.id.cart)
        badge.badgeGravity = BadgeDrawable.BOTTOM_START
        badge.backgroundColor = ContextCompat.getColor(this,R.color.blue)
        badge.number = convertEnToFa(cartItemCount.count.toString()).toInt()
        badge.verticalOffset = convertDpToPixel(10f, this).toInt()

        badge.isVisible = cartItemCount.count > 0

    }

    override fun onSupportNavigateUp(): Boolean {
        return currentNavController?.value?.navigateUp() ?: false
    }
}