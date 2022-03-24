package com.yoti.android.cryptocurrencychallenge.ui.activity

import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import androidx.navigation.ActivityNavigator
import androidx.navigation.NavController
import androidx.navigation.NavDestination
import androidx.navigation.fragment.NavHostFragment
import androidx.navigation.ui.AppBarConfiguration
import androidx.navigation.ui.setupActionBarWithNavController
import com.yoti.android.cryptocurrencychallenge.R
import com.yoti.android.cryptocurrencychallenge.databinding.HomeActivityBinding
import com.yoti.android.cryptocurrencychallenge.ui.shared.extension.hideKeyboard
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class HomeActivity : AppCompatActivity() , NavController.OnDestinationChangedListener{

    private lateinit var binding: HomeActivityBinding

    private lateinit var appBarConfiguration: AppBarConfiguration
    private val navHostFragment: NavHostFragment
        get() = (supportFragmentManager.findFragmentById(
            R.id.fragmentContainerHome
        ) as NavHostFragment)

    private val navController: NavController
        get() = navHostFragment.navController

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(HomeActivityBinding.inflate(layoutInflater).also {
            binding = it
        }.root)
        val navGraph = navController.navInflater.inflate(R.navigation.home_nav_graph)
        navController.graph = navGraph
        navController.addOnDestinationChangedListener(this)
        appBarConfiguration = AppBarConfiguration(navController.graph)
        setupActionBarWithNavController(navController)
    }


    override fun onSupportNavigateUp(): Boolean {
        return navController.navigateUp() || super.onSupportNavigateUp()
    }

    override fun onDestinationChanged(
        controller: NavController,
        destination: NavDestination,
        arguments: Bundle?
    ) {
        this.hideKeyboard(currentFocus?.windowToken)
    }

    override fun finish() {
        super.finish()
        ActivityNavigator.applyPopAnimationsToPendingTransition(this)
    }
}