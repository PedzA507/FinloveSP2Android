package th.ac.rmutto.finlove

import android.content.Intent
import android.os.Bundle
import android.widget.Button
import androidx.appcompat.app.AppCompatActivity

class FirstPageActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_first_page)

        val loginButton = findViewById<Button>(R.id.btn_login)
        val registerButton = findViewById<Button>(R.id.btn_register)

        loginButton.setOnClickListener {
            val intent = Intent(this@FirstPageActivity, LoadingActivity::class.java)
            intent.putExtra("nextActivity", "Login")
            startActivity(intent)
        }

        registerButton.setOnClickListener {
            val intent = Intent(this@FirstPageActivity, LoadingActivity::class.java)
            intent.putExtra("nextActivity", "Register")
            startActivity(intent)
        }

    }
}

