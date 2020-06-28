package pl.krusiec.trelloclone.activities

import android.app.Dialog
import android.os.Bundle
import android.view.Menu
import android.view.MenuItem
import android.widget.Toast
import androidx.recyclerview.widget.LinearLayoutManager
import kotlinx.android.synthetic.main.activity_members.*
import kotlinx.android.synthetic.main.dialog_search_member.*
import pl.krusiec.trelloclone.R
import pl.krusiec.trelloclone.adapters.MemberListItemsAdapter
import pl.krusiec.trelloclone.firebase.FirestoreClass
import pl.krusiec.trelloclone.models.Board
import pl.krusiec.trelloclone.models.User
import pl.krusiec.trelloclone.utils.Constants

class MembersActivity : BaseActivity() {

    private lateinit var boardDetails: Board

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_members)

        if (intent.hasExtra(Constants.BOARD_DETAIL)){
            boardDetails = intent.getParcelableExtra(Constants.BOARD_DETAIL)!!
        }
        setupActionBar()

        showProgressDialog(resources.getString(R.string.please_wait))
        FirestoreClass().getAssignedMembersListDetails(this, boardDetails.assignedTo)
    }

    fun setupMembersList(list: ArrayList<User>){
        hideProgressDialog()

        rvMembersList.layoutManager = LinearLayoutManager(this)
        rvMembersList.setHasFixedSize(true)

        val adapter = MemberListItemsAdapter(this, list)
        rvMembersList.adapter = adapter
    }

    private fun setupActionBar() {
        setSupportActionBar(toolbarMembersActivity)
        val actionBar = supportActionBar
        actionBar?.setDisplayHomeAsUpEnabled(true)
        actionBar?.setHomeAsUpIndicator(R.drawable.ic_white_color_back_24dp)
        actionBar?.title = resources.getString(R.string.members)

        toolbarMembersActivity.setNavigationOnClickListener { onBackPressed() }
    }

    override fun onCreateOptionsMenu(menu: Menu?): Boolean {
        menuInflater.inflate(R.menu.menu_add_member, menu)
        return super.onCreateOptionsMenu(menu)
    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        when(item.itemId){
            R.id.action_add_member ->{
                dialogSearchMember()
                return true
            }
        }

        return super.onOptionsItemSelected(item)
    }

    private fun dialogSearchMember(){
        val dialog = Dialog(this)
        dialog.setContentView(R.layout.dialog_search_member)
        dialog.tvAdd.setOnClickListener {
            val email = dialog.etEmailSearchMember.text.toString()
            if (email.isNotEmpty()){
                dialog.dismiss()
                // TODO implement adding member logic
            } else {
                Toast.makeText(this@MembersActivity, "Please enter members email address.", Toast.LENGTH_SHORT).show()
            }
        }
        dialog.tvCancel.setOnClickListener {
            dialog.dismiss()
        }
        dialog.show()
    }
}