package pl.krusiec.trelloclone.activities

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import kotlinx.android.synthetic.main.activity_members.*
import pl.krusiec.trelloclone.R
import pl.krusiec.trelloclone.models.Board
import pl.krusiec.trelloclone.utils.Constants

class MembersActivity : AppCompatActivity() {

    private lateinit var boardDetails: Board

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_members)
        setupActionBar()

        if (intent.hasExtra(Constants.BOARD_DETAIL)){
            boardDetails = intent.getParcelableExtra(Constants.BOARD_DETAIL)!!
        }
    }

    private fun setupActionBar() {
        setSupportActionBar(toolbarMembersActivity)
        val actionBar = supportActionBar
        actionBar?.setDisplayHomeAsUpEnabled(true)
        actionBar?.setHomeAsUpIndicator(R.drawable.ic_white_color_back_24dp)
        actionBar?.title = resources.getString(R.string.members)

        toolbarMembersActivity.setNavigationOnClickListener { onBackPressed() }
    }
}