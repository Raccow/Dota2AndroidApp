package github.com.rhacco.dota2androidapp.activities

import android.os.Bundle
import android.support.v4.app.Fragment
import android.support.v4.app.FragmentManager
import android.support.v4.app.FragmentPagerAdapter
import android.support.v7.app.AlertDialog
import android.view.ContextThemeWrapper
import android.view.Menu
import android.view.MenuItem
import github.com.rhacco.dota2androidapp.R
import github.com.rhacco.dota2androidapp.base.BaseNavigationDrawerActivity
import github.com.rhacco.dota2androidapp.fragments.TopMatchesFragment
import kotlinx.android.synthetic.main.activity_top_matches.*

class TopMatchesActivity : BaseNavigationDrawerActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_top_matches)
        super.initNavigationDrawer(drawer_layout)
        view_pager.adapter = CustomPagerAdapter(supportFragmentManager)
    }

    override fun onCreateOptionsMenu(menu: Menu?): Boolean {
        menuInflater.inflate(R.menu.button_info, menu)
        return true
    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        if (item.itemId == R.id.button_info) {
            val alertDialog = AlertDialog.Builder(
                    ContextThemeWrapper(this, R.style.AlertDialogTheme)).create()
            alertDialog.setMessage(getString(R.string.info_top_matches))
            alertDialog.setButton(AlertDialog.BUTTON_NEUTRAL, getString(R.string.dialog_ok),
                    { dialog, _ -> dialog.dismiss() })
            alertDialog.show()
            return true
        }
        return super.onOptionsItemSelected(item)
    }

    private class CustomPagerAdapter(fm: FragmentManager) : FragmentPagerAdapter(fm) {
        override fun getItem(position: Int): Fragment? =
                when (position) {
                    0 -> {
                        val fragment = TopMatchesFragment()
                        val bundle = Bundle()
                        bundle.putInt("tab_position", 0)
                        fragment.arguments = bundle
                        fragment
                    }
                    else -> {
                        val fragment = TopMatchesFragment()
                        val bundle = Bundle()
                        bundle.putInt("tab_position", 1)
                        fragment.arguments = bundle
                        fragment
                    }
                }

        override fun getCount(): Int = 2

        override fun getPageTitle(position: Int): CharSequence =
                when (position) {
                    0 -> {
                        "Live"
                    }
                    else -> {
                        "Recent"
                    }
                }
    }
}