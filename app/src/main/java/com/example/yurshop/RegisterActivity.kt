package com.example.yurshop

import android.content.Intent
import android.os.Bundle
import android.text.InputType
import android.text.SpannableString
import android.text.Spanned
import android.text.method.LinkMovementMethod
import android.text.style.ClickableSpan
import android.util.Log
import android.view.View
import android.widget.Button
import android.widget.EditText
import android.widget.ImageView
import android.widget.TextView
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import com.google.firebase.database.FirebaseDatabase

class RegisterActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_register)

        val etEmail = findViewById<EditText>(R.id.etNewEmail)
        val etNewPassword = findViewById<EditText>(R.id.etNewPassword)
        val etNewConfirmPassword = findViewById<EditText>(R.id.etNewConfirmPassword)
        val ivTogglePassword = findViewById<ImageView>(R.id.ivTogglePassword)
        val ivToggleConfirmPassword = findViewById<ImageView>(R.id.ivToggleConfirmPassword)
        val btnRegister = findViewById<Button>(R.id.btnRegister)
        val punyaAkunTextView = findViewById<TextView>(R.id.PunyaAkun)

        // Toggle visibility password
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

        // Register button logic
        btnRegister.setOnClickListener {
            val email = etEmail.text.toString().trim()
            val password = etNewPassword.text.toString().trim()
            val confirmPassword = etNewConfirmPassword.text.toString().trim()

            if (email.isEmpty() || password.isEmpty() || confirmPassword.isEmpty()) {
                Toast.makeText(this, "Semua field harus diisi!", Toast.LENGTH_SHORT).show()
                return@setOnClickListener
            }

            if (password != confirmPassword) {
                Toast.makeText(this, "Password tidak cocok!", Toast.LENGTH_SHORT).show()
                return@setOnClickListener
            }

            // Simpan ke Firebase Realtime Database
            val database = FirebaseDatabase.getInstance()
            val userRef = database.getReference("users")
            val userId = userRef.push().key ?: ""

            val user = mapOf(
                "email" to email,
                "password" to password // Jangan simpan password plaintext di produksi!
            )

            userRef.child(userId).setValue(user)
                .addOnSuccessListener {
                    Toast.makeText(this, "Register berhasil!", Toast.LENGTH_SHORT).show()
                    val intent = Intent(this, LoginActivity::class.java)
                    startActivity(intent)
                    finish()
                }
                .addOnFailureListener { exception ->
                    Log.e("RegisterActivity", "Gagal menyimpan data: ${exception.message}")
                    Toast.makeText(this, "Register gagal. Coba lagi!", Toast.LENGTH_SHORT).show()
                }
        }

        // Klik "Masuk" untuk kembali ke LoginActivity
        val spannableString = SpannableString(punyaAkunTextView.text)
        val clickableSpan = object : ClickableSpan() {
            override fun onClick(widget: View) {
                val intent = Intent(this@RegisterActivity, LoginActivity::class.java)
                startActivity(intent)
            }
        }
        val masukStartIndex = spannableString.indexOf("Masuk")
        val masukEndIndex = masukStartIndex + "Masuk".length
        spannableString.setSpan(clickableSpan, masukStartIndex, masukEndIndex, Spanned.SPAN_EXCLUSIVE_EXCLUSIVE)
        punyaAkunTextView.text = spannableString
        punyaAkunTextView.movementMethod = LinkMovementMethod.getInstance()
    }
}
