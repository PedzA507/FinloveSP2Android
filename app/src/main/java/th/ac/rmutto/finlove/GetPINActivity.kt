package th.ac.rmutto.finlove

import android.content.Intent
import android.os.Bundle
import android.text.TextUtils
import android.view.View
import android.widget.Button
import android.widget.EditText
import android.widget.ProgressBar
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import okhttp3.Call
import okhttp3.Callback
import okhttp3.FormBody
import okhttp3.OkHttpClient
import okhttp3.Request
import okhttp3.Response
import java.io.IOException

class GetPINActivity : AppCompatActivity() {

    private lateinit var pinEditText: EditText
    private lateinit var submitButton: Button
    private lateinit var progressBar: ProgressBar
    private val client = OkHttpClient()
    private lateinit var email: String

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_get_pinactivity)

        pinEditText = findViewById(R.id.pinEditText)
        submitButton = findViewById(R.id.submitButton)
        progressBar = findViewById(R.id.progressBar)

        // รับ email จาก Intent
        email = intent.getStringExtra("email").toString()

        submitButton.setOnClickListener {
            validatePIN()
        }
    }

    private fun validatePIN() {
        val pin = pinEditText.text.toString().trim()

        if (TextUtils.isEmpty(pin)) {
            pinEditText.error = "กรุณากรอก PIN"
            return
        }

        progressBar.visibility = View.VISIBLE
        submitButton.isEnabled = false

        val requestBody = FormBody.Builder()
            .add("email", email)
            .add("pin", pin)
            .build()

        val request = Request.Builder()
            .url("http://192.168.1.49:4000/api/verify-pin")  // URL สำหรับ verify PIN
            .post(requestBody)
            .build()

        client.newCall(request).enqueue(object : Callback {
            override fun onFailure(call: Call, e: IOException) {
                runOnUiThread {
                    progressBar.visibility = View.GONE
                    submitButton.isEnabled = true
                    Toast.makeText(this@GetPINActivity, "Error: ${e.message}", Toast.LENGTH_LONG).show()
                }
            }

            override fun onResponse(call: Call, response: Response) {
                runOnUiThread {
                    progressBar.visibility = View.GONE
                    submitButton.isEnabled = true
                    if (response.isSuccessful) {
                        Toast.makeText(this@GetPINActivity, "PIN ถูกต้อง", Toast.LENGTH_LONG).show()
                        // นำไปหน้า ResetPasswordActivity
                        val intent = Intent(this@GetPINActivity, ResetPasswordActivity::class.java)
                        intent.putExtra("email", email)
                        startActivity(intent)
                        finish() // ปิดหน้าปัจจุบัน
                    } else {
                        Toast.makeText(this@GetPINActivity, "PIN ไม่ถูกต้อง", Toast.LENGTH_LONG).show()
                    }
                }
            }
        })
    }

    // เมื่อผู้ใช้ปัดกลับ (Back Pressed) จะกลับไปหน้า ForgotPasswordActivity ทันที
    override fun onBackPressed() {
        val intent = Intent(this@GetPINActivity, ForgotPasswordActivity::class.java)
        intent.flags = Intent.FLAG_ACTIVITY_CLEAR_TOP or Intent.FLAG_ACTIVITY_SINGLE_TOP
        startActivity(intent)
        finish()  // ปิดหน้า GetPINActivity
    }
}
