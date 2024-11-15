package com.example.yurshop

import android.annotation.SuppressLint
import android.os.Bundle
import android.text.InputType
import android.widget.EditText
import android.widget.ImageView
import androidx.appcompat.app.AppCompatActivity

class ResetPasswordActivity : AppCompatActivity() {
    @SuppressLint("MissingInflatedId")
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_reset_password)

        val etNewPassword: EditText = findViewById(R.id.etNewPassword)
        val ivTogglePassword: ImageView = findViewById(R.id.ivNewPasswordReset)

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

        val etNewConfirmPassword: EditText = findViewById(R.id.etNewConfirmPassword)
        val ivToggleConfirmPassword: ImageView = findViewById(R.id.ivResetPassword)

        ivToggleConfirmPassword.setOnClickListener {
            if (etNewConfirmPassword.inputType == InputType.TYPE_TEXT_VARIATION_VISIBLE_PASSWORD) {
                etNewConfirmPassword.inputType =
                    InputType.TYPE_CLASS_TEXT or InputType.TYPE_TEXT_VARIATION_PASSWORD
                ivToggleConfirmPassword.setImageResource(R.drawable.ice_eye_off)
            } else {
                etNewConfirmPassword.inputType = InputType.TYPE_TEXT_VARIATION_VISIBLE_PASSWORD
                ivToggleConfirmPassword.setImageResource(R.drawable.ice_eye)
            }
        }

    }
}