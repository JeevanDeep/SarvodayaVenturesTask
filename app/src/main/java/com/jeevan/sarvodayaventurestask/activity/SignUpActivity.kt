package com.jeevan.sarvodayaventurestask.activity

import android.os.Bundle
import android.preference.PreferenceManager
import android.support.v7.app.AppCompatActivity
import android.view.View
import com.jeevan.sarvodayaventurestask.R
import com.jeevan.sarvodayaventurestask.db.AppDatabase
import com.jeevan.sarvodayaventurestask.model.SignUpModel
import kotlinx.android.synthetic.main.activity_sign_up.*
import kotlinx.android.synthetic.main.toolbar.*
import org.jetbrains.anko.doAsync
import org.jetbrains.anko.toast
import org.jetbrains.anko.uiThread

class SignUpActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_sign_up)

        setSupportActionBar(toolbar)
        if (intent?.getBooleanExtra("login", false) == true) {
            supportActionBar?.title = "Sign up"
        } else {
            supportActionBar?.title = "Edit profile"
            btnSignup.visibility = View.GONE
            btnUpdate.visibility = View.VISIBLE

            fillUserInfo()
        }

        addClickListeners()
    }

    private fun fillUserInfo() {
        doAsync {
            val number = PreferenceManager.getDefaultSharedPreferences(this@SignUpActivity).getString("number", "")
            val model = AppDatabase.getInstance(this@SignUpActivity).contactsDAO().getUserProfile(number)
            uiThread {
                etName.setText(model?.name)
                etGender.setText(model?.gender)
                etEmail.setText(model?.email)
                etNumber.setText(model?.phoneNumber)
                textInputLayout2.isEnabled = false
            }
        }
    }

    private fun addClickListeners() {
        btnSignup.setOnClickListener {
            if (checkFields()) {
                val model = SignUpModel(
                        etName.text.toString(), etNumber.text.toString(), etEmail.text.toString(),
                        etGender.text.toString(), etPassword.text.toString())

                signUp(model)
            }
        }

        btnUpdate.setOnClickListener {
            if (checkFields()) {
                val model = SignUpModel(
                        etName.text.toString(), etNumber.text.toString(), etEmail.text.toString(),
                        etGender.text.toString(), etPassword.text.toString())
                updateUser(model)
            }
        }
    }

    private fun signUp(model: SignUpModel) {
        doAsync {
            val user = AppDatabase.getInstance(this@SignUpActivity)
                    .contactsDAO().getUserProfile(model.phoneNumber)
            uiThread {
                if (user == null)
                    saveUser(model)
                else toast("This user already exists")
            }
        }
    }

    private fun updateUser(model: SignUpModel) {
        doAsync {
            AppDatabase.getInstance(this@SignUpActivity).contactsDAO().updateUser(model)
            uiThread {
                toast("Information updated successfully")
            }
        }
    }

    private fun saveUser(model: SignUpModel) {
        doAsync {
            AppDatabase.getInstance(this@SignUpActivity).contactsDAO().saveUser(model)
            uiThread {
                toast("User saved successfully")
            }
        }
    }

    private fun checkFields(): Boolean {
        if (etName.text?.isBlank() == true) {
            etName.error = "Enter a valid name"
            return false
        }
        if (etNumber.text?.length != 10) {
            etNumber.error = "Enter a valid phone number"
            return false
        }
        if (etEmail.text?.isBlank() == true) {
            etEmail.error = "Please enter a valid email"
            return false
        }
        if (etGender.text?.isBlank() == true) {
            etGender.error = "Gender cannot be blank"
            return false
        }
        if (etPassword.text?.isBlank() == true) {
            etPassword.error = "Please enter a valid password"
            return false
        }
        if (etConfirmPassword.text?.isBlank() != true) {
            if (etPassword.text.toString() != etConfirmPassword.text.toString()) {
                etConfirmPassword.error = "Password does not match"
                return false
            }
        }
        return true
    }
}
