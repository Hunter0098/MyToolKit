package com.example.vaibhav.mytoolkit

import android.support.v7.app.AppCompatActivity
import android.os.Bundle
import android.support.design.widget.TabItem
import android.support.design.widget.TabLayout
import android.support.v4.view.ViewPager
import android.support.v4.app.Fragment
import android.support.v4.app.FragmentManager
import android.support.v4.app.FragmentPagerAdapter
import android.content.Context
import android.widget.ScrollView
import android.widget.TextView

class TimeTable : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_time_table)

        val actionbar = supportActionBar
        actionbar!!.title = "Timetable"
        actionbar.setDisplayHomeAsUpEnabled(true)

        var mainScrollView = findViewById<ScrollView>(R.id.mainScrollView)
        mainScrollView.isFillViewport = true

        var daysOfWeek = arrayOf("Wednesday", "Thursday", "Friday", "Saturday", "Sunday")
        var tablayout = findViewById<TabLayout>(R.id.tabLayout)

        for (day in daysOfWeek) {
            tablayout.addTab(tablayout.newTab().setText(day))
        }
        tablayout.tabGravity = TabLayout.GRAVITY_FILL

        val viewpager = findViewById<ViewPager>(R.id.viewPager)
        val adapter = MyAdapter(this, supportFragmentManager, tablayout.tabCount)
        viewpager.adapter = adapter

        viewpager.addOnPageChangeListener(TabLayout.TabLayoutOnPageChangeListener(tablayout))

        tablayout.addOnTabSelectedListener(object : TabLayout.OnTabSelectedListener {
            override fun onTabSelected(tab: TabLayout.Tab) {
                viewpager.currentItem = tab.position
            }

            override fun onTabUnselected(tab: TabLayout.Tab) {

            }

            override fun onTabReselected(tab: TabLayout.Tab) {

            }
        })

    }

    class MyAdapter(private val myContext: Context, fm: FragmentManager, internal var totalTabs: Int) :
        FragmentPagerAdapter(fm) {

        //For fragment tabs
        override fun getItem(position: Int): Fragment? {
            when (position) {
                0 -> {
                    return Fragmenttimetable()
                }
                1 -> {
                    return Fragment_tuesday()
                }
                2 -> {
                    return Fragment_wednesday()
                }
                3 -> {
                    return Fragment_thursday()
                }
                4 -> {
                    return Fragment_friday()
                }
                5 -> {
                    return Fragment_saturday()
                }
                6 -> {
                    return Fragment_sunday()
                }
                else -> return Fragmenttimetable()
            }
        }

        override fun getCount(): Int {
            return totalTabs
        }
    }

    override fun onSupportNavigateUp(): Boolean {
        onBackPressed()
        return true
    }
}