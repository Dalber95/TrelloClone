package pl.krusiec.trelloclone.activities

import android.app.Activity
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
    private lateinit var assignedMembersList: ArrayList<User>
    private var anyChangesMade: Boolean = false

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_members)

        if (intent.hasExtra(Constants.BOARD_DETAIL)) {
            boardDetails = intent.getParcelableExtra(Constants.BOARD_DETAIL)!!
        }
        setupActionBar()

        showProgressDialog(resources.getString(R.string.please_wait))
        FirestoreClass().getAssignedMembersListDetails(this, boardDetails.assignedTo)
    }

    fun setupMembersList(list: ArrayList<User>) {
        assignedMembersList = list
        hideProgressDialog()

        rvMembersList.layoutManager = LinearLayoutManager(this)
        rvMembersList.setHasFixedSize(true)

        val adapter = MemberListItemsAdapter(this, list)
        rvMembersList.adapter = adapter
    }

    fun memberDetails(user: User){
        boardDetails.assignedTo.add(user.id)
        FirestoreClass().assignMemberToBoard(this, boardDetails, user)
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
        when (item.itemId) {
            R.id.action_add_member -> {
                dialogSearchMember()
                return true
            }
        }

        return super.onOptionsItemSelected(item)
    }

    private fun dialogSearchMember() {
        val dialog = Dialog(this)
        dialog.setContentView(R.layout.dialog_search_member)
        dialog.tvAdd.setOnClickListener {
            val email = dialog.etEmailSearchMember.text.toString()
            if (email.isNotEmpty()) {
                dialog.dismiss()
                showProgressDialog(resources.getString(R.string.please_wait))
                FirestoreClass().getMemberDetails(this, email)
            } else {
                Toast.makeText(
                    this@MembersActivity,
                    "Please enter members email address.",
                    Toast.LENGTH_SHORT
                ).show()
            }
        }
        dialog.tvCancel.setOnClickListener {
            dialog.dismiss()
        }
        dialog.show()
    }

    override fun onBackPressed() {
        if(anyChangesMade){
            setResult(Activity.RESULT_OK)
        }
        super.onBackPressed()
    }

    fun memberAssignSuccess(user: User){
        hideProgressDialog()
        assignedMembersList.add(user)
        anyChangesMade = true
        setupMembersList(assignedMembersList)
    }
}