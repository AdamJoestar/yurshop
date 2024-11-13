package com.example.yurshop
import android.content.Intent
import android.os.Bundle
import android.text.InputType
import android.widget.Button
import android.widget.EditText
import android.widget.ImageView
import androidx.appcompat.app.AppCompatActivity

class RegisterActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_register)

        val etNewPassword = findViewById(R.id.etNewPassword) as EditText
        val ivTogglePassword = findViewById(R.id.ivTogglePassword) as ImageView

        ivTogglePassword.setOnClickListener {
            if (etNewPassword.inputType == InputType.TYPE_TEXT_VARIATION_VISIBLE_PASSWORD) {
                etNewPassword.inputType =
                    InputType.TYPE_CLASS_TEXT or InputType.TYPE_TEXT_VARIATION_PASSWORD
                ivTogglePassword.setImageResource(R.drawable.ice_eye_off)
            } else {
                etNewPassword.inputType = InputType.TYPE_TEXT_VARIATION_VISIBLE_PASSWORD
                ivTogglePassword.setImageResource(R.drawable.ice_eye)
            }
            etNewPassword.setSelection(etNewPassword.text.length)
        }

        val etNewConfirmPassword = findViewById(R.id.etNewConfirmPassword) as EditText
        val ivToggleConfirmPassword = findViewById(R.id.ivToggleConfirmPassword) as ImageView

        ivToggleConfirmPassword.setOnClickListener {
            if (etNewConfirmPassword.inputType == InputType.TYPE_TEXT_VARIATION_VISIBLE_PASSWORD) {
                etNewConfirmPassword.inputType =
                    InputType.TYPE_CLASS_TEXT or InputType.TYPE_TEXT_VARIATION_PASSWORD
                ivToggleConfirmPassword.setImageResource(R.drawable.ice_eye_off)
            } else {
                etNewConfirmPassword.inputType = InputType.TYPE_TEXT_VARIATION_VISIBLE_PASSWORD
                ivToggleConfirmPassword.setImageResource(R.drawable.ice_eye)
            }

            val btnRegister = findViewById<Button>(R.id.btnRegister)
            btnRegister.setOnClickListener {
                // Setelah register berhasil, kembali ke login
                val intent = Intent(this, LoginActivity::class.java)
                startActivity(intent)
                finish()
            }
        }
    }
}
