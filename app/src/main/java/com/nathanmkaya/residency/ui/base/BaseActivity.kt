package com.nathanmkaya.residency.ui.base

import android.content.Intent
import android.os.Bundle
import android.support.design.widget.NavigationView
import android.support.v4.view.GravityCompat
import android.support.v7.app.ActionBarDrawerToggle
import android.support.v7.app.AppCompatActivity
import android.view.Menu
import android.view.MenuItem
import android.view.View
import com.google.firebase.auth.FirebaseAuth
import com.nathanmkaya.residency.R
import com.nathanmkaya.residency.R.*
import com.nathanmkaya.residency.ui.auth.LoginActivity
import com.nathanmkaya.residency.ui.devices.DevicesActivity
import com.nathanmkaya.residency.ui.news.NewsActivity
import com.nathanmkaya.residency.ui.students.StudentsActivity
import kotlinx.android.synthetic.main.activity_base.*
import kotlinx.android.synthetic.main.app_bar_base.*
import kotlinx.android.synthetic.main.app_bar_base.view.*


open class BaseActivity : AppCompatActivity(), NavigationView.OnNavigationItemSelectedListener {

    lateinit var root: View

    override fun setContentView(layoutResID: Int) {
        (root as View?)!!.coordinator.content.addView(layoutInflater.inflate(layoutResID, null))
        super.setContentView(root)
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        //setContentView(layout.activity_base)
        root = layoutInflater.inflate(layout.activity_base, null)

        FirebaseAuth.getInstance().currentUser?.let {
            println(it.displayName)
        } ?: run {
            startActivity(Intent(this@BaseActivity, LoginActivity::class.java))
            finish()
        }
    }

    override fun onPostCreate(savedInstanceState: Bundle?) {
        super.onPostCreate(savedInstanceState)
        setSupportActionBar(toolbar)

        fab.hide()
        /*fab.setOnClickListener { view ->
          Snackbar.make(view, "Replace with your own action", Snackbar.LENGTH_LONG)
              .setAction("Action", null).show()
        }*/

        val toggle = ActionBarDrawerToggle(
                this, drawer_layout, toolbar, string.navigation_drawer_open,
                string.navigation_drawer_close)
        drawer_layout.addDrawerListener(toggle)
        toggle.syncState()

        nav_view.setNavigationItemSelectedListener(this)
    }

    override fun onBackPressed() {
        if (drawer_layout.isDrawerOpen(GravityCompat.START)) {
            drawer_layout.closeDrawer(GravityCompat.START)
        } else {
            super.onBackPressed()
            overridePendingTransition(R.anim.activity_back_in, R.anim.activity_back_out)
        }
    }

    override fun onCreateOptionsMenu(menu: Menu): Boolean {
        // Inflate the menu; this adds items to the action bar if it is present.
        menuInflater.inflate(R.menu.base, menu)
        return true
    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        when (item.itemId) {
            id.home -> {
                overridePendingTransition(R.anim.activity_back_in, R.anim.activity_back_out)
                return true
            }
            id.action_settings -> return true
            else -> return super.onOptionsItemSelected(item)
        }
    }

    override fun onNavigationItemSelected(item: MenuItem): Boolean {
        // Handle navigation view item clicks here.
        when (item.itemId) {
            id.nav_students -> {
                startActivity(Intent(this@BaseActivity, StudentsActivity::class.java))
                overridePendingTransition(R.anim.activity_in, R.anim.activity_out)
            }
            id.nav_devices -> {
                startActivity(Intent(this@BaseActivity, DevicesActivity::class.java))
                overridePendingTransition(R.anim.activity_in, R.anim.activity_out)
            }
            id.nav_news -> {
                startActivity(Intent(this@BaseActivity, NewsActivity::class.java))
                overridePendingTransition(R.anim.activity_in, R.anim.activity_out)
            }
        }

        drawer_layout.closeDrawer(GravityCompat.START)
        return true
    }
}
