package dmax.scara.android.present.main

import android.os.Bundle
import androidx.fragment.app.Fragment
import androidx.fragment.app.FragmentActivity
import androidx.viewpager2.adapter.FragmentStateAdapter
import androidx.viewpager2.widget.ViewPager2
import com.google.android.material.bottomnavigation.BottomNavigationView
import dmax.scara.android.R
import dmax.scara.android.present.control.ControlFragment
import dmax.scara.android.present.home.HomeFragment
import dmax.scara.android.present.locate.LocateFragment
import dmax.scara.android.present.shapes.ShapesFragment

private const val PAGES = 4

class MainActivity : FragmentActivity() {

    private val viewPager by lazy { findViewById<ViewPager2>(R.id.pager) }
    private val bottomBar by lazy { findViewById<BottomNavigationView>(R.id.bottom_bar) }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        with(viewPager) {
            adapter = PagerAdapter()
            registerOnPageChangeCallback(PagerCallback())
        }
        bottomBar.setOnNavigationItemSelectedListener {
            val page = when (it.itemId) {
                R.id.page_home -> 0
                R.id.page_control -> 1
                R.id.page_locate -> 2
                R.id.page_shapes -> 3
                else -> error("no page")
            }
            viewPager.setCurrentItem(page, true)
            true
        }
    }

    private inner class PagerCallback : ViewPager2.OnPageChangeCallback() {
        override fun onPageSelected(position: Int) {
            bottomBar.selectedItemId = when(position) {
                0 -> R.id.page_home
                1 -> R.id.page_control
                2 -> R.id.page_locate
                3 -> R.id.page_shapes
                else -> error("no page")
            }
        }
    }

    private inner class PagerAdapter : FragmentStateAdapter(this@MainActivity) {
        override fun getItemCount(): Int = PAGES
        override fun createFragment(position: Int): Fragment = when(position) {
            0 -> HomeFragment()
            1 -> ControlFragment()
            2 -> LocateFragment()
            3 -> ShapesFragment()
            else -> error("no page")
        }
    }
}
