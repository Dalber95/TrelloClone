package pl.krusiec.trelloclone

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.WindowManager
import kotlinx.android.synthetic.main.activity_intro.*

class IntroActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_intro)

        window.setFlags(
            WindowManager.LayoutParams.FLAG_FULLSCREEN,
            WindowManager.LayoutParams.FLAG_FULLSCREEN
        )

        btnSignUpIntro.setOnClickListener {
            startActivity(Intent(this, SignUpActivity::class.java))
        }

        btnSignInIntro.setOnClickListener {
            startActivity(Intent(this, SignInActivity::class.java))
        }
    }
}