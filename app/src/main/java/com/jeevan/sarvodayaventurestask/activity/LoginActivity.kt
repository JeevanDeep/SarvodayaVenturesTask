package com.jeevan.sarvodayaventurestask.activity

import android.os.Bundle
import android.preference.PreferenceManager
import android.support.v7.app.AppCompatActivity
import com.jeevan.sarvodayaventurestask.R
import com.jeevan.sarvodayaventurestask.db.AppDatabase
import kotlinx.android.synthetic.main.activity_login.*
import kotlinx.android.synthetic.main.toolbar.*
import org.jetbrains.anko.*

class LoginActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_login)


        setSupportActionBar(toolbar)
        supportActionBar?.title = "Login"

        val isLogin = PreferenceManager
                .getDefaultSharedPreferences(this)
                .getString("number", "")

        if (!isLogin.isBlank()) {
            startActivity<MainActivity>()
            finish()
        }
        addClickListeners()
    }

    private fun addClickListeners() {
        btnLogin.setOnClickListener {
            if (checkFields()) {
                login()
            }
        }
        tvSignUp.setOnClickListener {
            startActivity(intentFor<SignUpActivity>("login" to true))
        }
    }

    private fun login() {
        doAsync {
            val user = AppDatabase.getInstance(this@LoginActivity)
                    .contactsDAO()
                    .login(etNumber.text.toString(), etPassword.text.toString())

            uiThread {

                if (user == null) {
                    toast("Invalid number or password")
                } else {
                    PreferenceManager
                            .getDefaultSharedPreferences(this@LoginActivity)
                            .edit()
                            .putString("number", user.phoneNumber)
                            .apply()
                    startActivity<MainActivity>()
                    finish()
                }
            }
        }
    }

    private fun checkFields(): Boolean {
        if (etNumber.text?.length != 10) {
            etNumber.error = "Please enter a valid mobile number"
            return false
        }
        if (etPassword.text?.isBlank() == true) {
            etPassword.error = "Password cannot be blank"
            return false
        }
        return true
    }
}
