package pl.krusiec.trelloclone.activities

import android.content.Intent
import android.os.Bundle
import android.text.TextUtils
import android.view.WindowManager
import android.widget.Toast
import com.google.firebase.auth.FirebaseAuth
import kotlinx.android.synthetic.main.activity_sign_in.*
import pl.krusiec.trelloclone.R
import pl.krusiec.trelloclone.firebase.FirestoreClass
import pl.krusiec.trelloclone.models.User

class SignInActivity : BaseActivity() {

    private lateinit var auth: FirebaseAuth

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_sign_in)

        auth = FirebaseAuth.getInstance()

        window.setFlags(
            WindowManager.LayoutParams.FLAG_FULLSCREEN,
            WindowManager.LayoutParams.FLAG_FULLSCREEN
        )

        btnSignIn.setOnClickListener { signInRegisteredUser() }
        setupActionBar()
    }

    fun signInSuccess(user: User) {
        hideProgressDialog()
        startActivity(Intent(this, MainActivity::class.java))
        finish()
    }

    private fun setupActionBar() {
        setSupportActionBar(toolbarSignInActivity)

        val actionBar = supportActionBar
        actionBar?.setDisplayHomeAsUpEnabled(true)
        actionBar?.setHomeAsUpIndicator(R.drawable.ic_black_color_back_24dp)

        toolbarSignInActivity.setNavigationOnClickListener { onBackPressed() }
    }

    private fun signInRegisteredUser() {
        val email: String = etEmailSignIn.text.toString().trim { it <= ' ' }
        val password: String = etPasswordSignIn.text.toString().trim { it <= ' ' }

        if (validateForm(email, password)) {
            showProgressDialog(resources.getString(R.string.please_wait))
            auth.signInWithEmailAndPassword(email, password).addOnCompleteListener(this) { task ->
                if (task.isSuccessful) {
                    FirestoreClass().loadUserData(this)
                } else {
                    Toast.makeText(this, task.exception!!.message, Toast.LENGTH_LONG).show()
                }
            }
        }
    }

    private fun validateForm(email: String, password: String): Boolean {
        return when {
            TextUtils.isEmpty(email) -> {
                showErrorSnackBar("Please enter a email address")
                false
            }
            TextUtils.isEmpty(password) -> {
                showErrorSnackBar("Please enter a password")
                false
            }
            else -> true
        }
    }
}