package com.example.sample.view


import android.os.Bundle
import android.view.View
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.GravityCompat
import androidx.drawerlayout.widget.DrawerLayout
import androidx.navigation.NavController
import androidx.navigation.findNavController
import androidx.navigation.ui.AppBarConfiguration
import androidx.navigation.ui.navigateUp
import androidx.navigation.ui.setupActionBarWithNavController
import androidx.navigation.ui.setupWithNavController
import com.example.sample.R
import com.example.sample.databinding.ActivityMainBinding
import com.google.android.material.navigation.NavigationView
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class MainActivity : AppCompatActivity() {

    private var drawerLayout: DrawerLayout ?= null
    private var navController: NavController ?= null
    private  var appBarConfiguration: AppBarConfiguration ?= null
    private var binding: ActivityMainBinding ?= null

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding?.root)

        setSupportActionBar(binding?.toolbar)

        drawerLayout = findViewById(R.id.drawerLayout)
        val navigationView: NavigationView = findViewById(R.id.navigation_view)

        navController = findNavController(R.id.nav_host_fragment)

        drawerLayout?.setDrawerLockMode(DrawerLayout.LOCK_MODE_LOCKED_CLOSED)

        appBarConfiguration = AppBarConfiguration(
            setOf(R.id.nav_order_summary), drawerLayout
        )

        navController?.let { setupActionBarWithNavController(it, appBarConfiguration!!) }

        navController?.let { navigationView.setupWithNavController(it) }


        binding?.ivnavigation?.setOnClickListener {
            showDrawerOpenClose()
        }

        navController?.addOnDestinationChangedListener { _, destination, _ ->
            when (destination.id) {
                R.id.productListFragment -> {
                    showHomeActionBar()
                }
                R.id.productDetailFragment -> {
                    hideHomeActionBar()
                }
                R.id.orderSummaryFragment -> {
                    hideHomeActionBar()
                }
            }
        }



        // Handle Navigation Item Click
        navigationView.setNavigationItemSelectedListener { menuItem ->
            when (menuItem.itemId) {
                R.id.nav_order_summary -> {
                    // Navigate to OrderSummaryFragment
                    navController?.navigate(R.id.orderSummaryFragment)
                    drawerLayout?.closeDrawers() // Close drawer after navigation
                    hideHomeActionBar()
                    true
                }
                else -> false
            }
        }

    }

    private fun hideHomeActionBar() {
        showActionBar()
        binding?.toolbar?.visibility = View.VISIBLE
        binding?.llhomeheader?.visibility = View.GONE
    }

    private fun showHomeActionBar() {
        binding?.toolbar?.visibility = View.GONE
        binding?.llhomeheader?.visibility = View.VISIBLE
        binding?.tvProductListName?.text = " Product List "
    }

    private fun showDrawerOpenClose() {
        if (binding?.drawerLayout?.isDrawerOpen(GravityCompat.START) == true) {
            binding?.drawerLayout?.closeDrawers()
        } else {
            binding?.drawerLayout?.openDrawer(GravityCompat.START)
        }
    }

    override fun onSupportNavigateUp(): Boolean {
        return appBarConfiguration?.let { navController?.navigateUp(it) } == true || super.onSupportNavigateUp()
    }
    private fun showActionBar() {
        // calling the action bar
        val actionBar = supportActionBar
        if (actionBar != null) {
            // Customize the back button
            actionBar.setHomeAsUpIndicator(R.drawable.baseline_arrow_back_24)
            // showing the back button in action bar
            actionBar.setDisplayHomeAsUpEnabled(true)
            actionBar.setHomeButtonEnabled(true)
        }
    }
}



