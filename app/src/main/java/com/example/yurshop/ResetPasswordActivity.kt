package com.example.yurshop // Ganti dengan package Anda

import android.annotation.SuppressLint
import android.content.Intent
import android.os.Bundle
import android.text.InputType
import android.widget.Button
import android.widget.EditText
import android.widget.ImageView
import androidx.appcompat.app.AlertDialog
import androidx.appcompat.app.AppCompatActivity

class ResetPasswordActivity : AppCompatActivity() {
    @SuppressLint("MissingInflatedId")
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_reset_password)

        // Referensi ke komponen UI
        val etNewPassword = findViewById<EditText>(R.id.etNewPassword)
        val ivNewPasswordReset = findViewById<ImageView>(R.id.ivNewPasswordReset)
        val etNewConfirmPassword = findViewById<EditText>(R.id.etNewConfirmPassword)
        val ivResetPassword = findViewById<ImageView>(R.id.ivResetPassword)
        val btnKonfirmasi = findViewById<Button>(R.id.btnKonfirmasi)

        // Logika untuk toggle visibilitas password (etNewPassword)
        ivNewPasswordReset.setOnClickListener {
            togglePasswordVisibility(etNewPassword, ivNewPasswordReset)
        }

        // Logika untuk toggle visibilitas password (etNewConfirmPassword)
        ivResetPassword.setOnClickListener {
            togglePasswordVisibility(etNewConfirmPassword, ivResetPassword)
        }

        // Logika untuk tombol konfirmasi reset password
        btnKonfirmasi.setOnClickListener {
            // Buat AlertDialog untuk pop-up sukses
            val builder = AlertDialog.Builder(this)
            val view = layoutInflater.inflate(R.layout.activity_success_popup, null) // Ganti layout sesuai dengan pop-up Anda
            builder.setView(view)

            // Tampilkan dialog
            val dialog = builder.create()
            dialog.show()

            // Referensi ke tombol dalam dialog
            val btnContinueSuccess = view.findViewById<Button>(R.id.btnContinueSuccess) // Sesuaikan ID tombol di layout pop-up

            // Logika untuk tombol "Lanjutkan" di pop-up
            btnContinueSuccess.setOnClickListener {
                val intent = Intent(this, LoginActivity::class.java) // Ganti LoginActivity dengan nama kelas halaman login Anda
                startActivity(intent)
                finish() // Tutup ResetPasswordActivity
            }
        }
    }

    /**
     * Fungsi untuk toggle visibilitas password
     * @param editText EditText di mana visibilitas password akan diubah
     * @param imageView ImageView yang mewakili tombol toggle
     */
    private fun togglePasswordVisibility(editText: EditText, imageView: ImageView) {
        if (editText.inputType == InputType.TYPE_TEXT_VARIATION_VISIBLE_PASSWORD) {
            editText.inputType = InputType.TYPE_CLASS_TEXT or InputType.TYPE_TEXT_VARIATION_PASSWORD
            imageView.setImageResource(R.drawable.ice_eye_off) // Ganti dengan ikon "mata tertutup"
        } else {
            editText.inputType = InputType.TYPE_TEXT_VARIATION_VISIBLE_PASSWORD
            imageView.setImageResource(R.drawable.ice_eye) // Ganti dengan ikon "mata terbuka"
        }
        // Set kursor ke akhir teks
        editText.setSelection(editText.text.length)
    }
}
