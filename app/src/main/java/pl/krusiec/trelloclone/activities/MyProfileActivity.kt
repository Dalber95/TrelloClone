package pl.krusiec.trelloclone.activities

import android.os.Bundle
import kotlinx.android.synthetic.main.activity_my_profile.*
import pl.krusiec.trelloclone.R

class MyProfileActivity : BaseActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_my_profile)

        setupActionBar()
    }

    private fun setupActionBar() {
        setSupportActionBar(toolbarMyProfileActivity)
        val actionBar = supportActionBar
        actionBar?.setDisplayHomeAsUpEnabled(true)
        actionBar?.setHomeAsUpIndicator(R.drawable.ic_white_color_back_24dp)
        actionBar?.title = resources.getString(R.string.my_profile_title)

        toolbarMyProfileActivity.setNavigationOnClickListener { onBackPressed() }
    }
}