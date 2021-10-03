package com.example.ecommapp.main_activity

import android.annotation.SuppressLint
import android.content.ContentValues.TAG
import android.content.Context
import android.content.Intent
import android.graphics.Color
import android.net.ConnectivityManager
import android.net.NetworkCapabilities
import android.os.Build
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import android.view.*
import android.widget.Toast
import androidx.annotation.RequiresApi
import androidx.appcompat.app.ActionBarDrawerToggle
import androidx.core.view.GravityCompat
import androidx.drawerlayout.widget.DrawerLayout
import androidx.navigation.NavController
import androidx.navigation.NavDirections
import androidx.navigation.NavGraphNavigator
import androidx.navigation.NavOptions
import androidx.navigation.fragment.NavHostFragment
import androidx.navigation.fragment.findNavController
import androidx.navigation.ui.*
import com.example.ecommapp.MainActivityNavGraphDirections
import com.example.ecommapp.R
import com.example.ecommapp.user_auth.UserAuth
import com.google.android.material.navigation.NavigationView
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.android.synthetic.main.activity_main.*
import android.view.Menu as Menu1

@AndroidEntryPoint
class MainActivity : AppCompatActivity() {

    private lateinit var navController: NavController
    private lateinit var appBarConfiguration: AppBarConfiguration

    lateinit var drawer_toggle_btn : ActionBarDrawerToggle


    @RequiresApi(Build.VERSION_CODES.LOLLIPOP)
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        /*window.setFlags(
            WindowManager.LayoutParams.FLAG_LAYOUT_NO_LIMITS,
            WindowManager.LayoutParams.FLAG_LAYOUT_NO_LIMITS
        )*/
        /*if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.R) {
            window.insetsController?.hide(WindowInsets.Type.statusBars())
        }
        window?.decorView?.systemUiVisibility = (View.SYSTEM_UI_FLAG_LAYOUT_STABLE
                or View.SYSTEM_UI_FLAG_LAYOUT_FULLSCREEN)
        window.statusBarColor = Color.TRANSPARENT
*/
        // Toolbar
        setSupportActionBar(toolbar)


        // Navigation Container connection with Navigation Graph
        val navHostFragment = supportFragmentManager.findFragmentById(R.id.main_activity_fragment_host) as NavHostFragment
        navController = navHostFragment.findNavController()

        appBarConfiguration = AppBarConfiguration(
            setOf(
                R.id.homeFragment,
                R.id.settingsFragment,
                //R.id.categoriesFragment,
                R.id.newCollectionFragment,
                R.id.notificationsFragment,
                ),
            main_activity_drawer
        )

        // Connect toolbar with navigation graph controller
        setupActionBarWithNavController(navController, appBarConfiguration)

        // Drawer & Navigation view connection with navigation graph
        main_activity_nav_view.setupWithNavController(navController)

        // Drawer layout settings
        // -- Indicator Icon
         drawer_toggle_btn = ActionBarDrawerToggle(this, main_activity_drawer, R.string.open, R.string.close)
        drawer_toggle_btn.setHomeAsUpIndicator(R.drawable.drawer_icon)

        /*main_activity_drawer.addDrawerListener(object : DrawerLayout.DrawerListener{
            override fun onDrawerSlide(drawerView: View, slideOffset: Float) {
            }

            override fun onDrawerOpened(drawerView: View) {
            }

            override fun onDrawerClosed(drawerView: View) {
            }

            override fun onDrawerStateChanged(newState: Int) {
            }
        })*/

        main_activity_drawer.addDrawerListener(drawer_toggle_btn)

        drawer_toggle_btn.syncState()

        supportActionBar?.setHomeButtonEnabled(true)
        supportActionBar?.setDisplayHomeAsUpEnabled(true)
        //supportActionBar?.setHomeAsUpIndicator(R.drawable.drawer_icon)

        navController.addOnDestinationChangedListener { nav,_,_ ->

            // 3azma || Greatness
            if(nav.currentDestination?.id == R.id.categoriesFragment)
                supportActionBar?.setHomeAsUpIndicator(R.drawable.ic_indicator_up)
            else if(nav.currentDestination?.id == R.id.cartFragment)
                supportActionBar?.setHomeAsUpIndicator(R.drawable.ic_indicator_up)
            else if(nav.currentDestination?.id == R.id.categoriesFragment)
                supportActionBar?.setHomeAsUpIndicator(R.drawable.drawer_icon)
            else if(nav.currentDestination?.id == R.id.homeFragment)
                supportActionBar?.setHomeAsUpIndicator(R.drawable.drawer_icon)
            else if(nav.currentDestination?.id == R.id.newCollectionFragment)
                supportActionBar?.setHomeAsUpIndicator(R.drawable.drawer_icon)
            else if(nav.currentDestination?.id == R.id.notificationsFragment)
                supportActionBar?.setHomeAsUpIndicator(R.drawable.drawer_icon)
            else if(nav.currentDestination?.id == R.id.settingsFragment)
                supportActionBar?.setHomeAsUpIndicator(R.drawable.drawer_icon)

        }
        main_activity_drawer.setScrimColor(Color.parseColor("#9c99aa"))
        //


        logout_btn.setOnClickListener(object :View.OnClickListener{
            override fun onClick(v: View?) {
                val intent = Intent(this@MainActivity, UserAuth::class.java)
                startActivity( intent )
                finish()
            }
        })

    }

    // Connect Toolbar with options menu
    override fun onCreateOptionsMenu(menu: android.view.Menu?): Boolean {
        menuInflater.inflate(R.menu.main_activity_menu, menu)
        return super.onCreateOptionsMenu(menu)
    }

    // Options Menu Clicks and Navigation
    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        return if (item.itemId == R.id.cartFragment) {
            val action = MainActivityNavGraphDirections.actionGlobalCartFragment()
            navController.navigate(
                action,
                NavOptions.Builder().setLaunchSingleTop(true).build()

            )
            true
        } else {
            item.onNavDestinationSelected(navController) || super.onOptionsItemSelected(item)
        }
    }

    override fun onSupportNavigateUp(): Boolean {
        return navController.navigateUp(appBarConfiguration) || super.onSupportNavigateUp()
    }

}