package com.jeevan.sarvodayaventurestask.activity

import android.os.Bundle
import android.preference.PreferenceManager
import android.support.v7.app.AppCompatActivity
import android.view.Menu
import android.view.MenuItem
import android.widget.EditText
import android.widget.LinearLayout
import com.jeevan.sarvodayaventurestask.CsvWriter
import com.jeevan.sarvodayaventurestask.R
import com.jeevan.sarvodayaventurestask.adapter.ViewPagerAdapter
import com.jeevan.sarvodayaventurestask.fragment.ContactsFragment
import com.jeevan.sarvodayaventurestask.fragment.WhatsappContactsFragment
import com.karumi.dexter.Dexter
import com.karumi.dexter.PermissionToken
import com.karumi.dexter.listener.PermissionDeniedResponse
import com.karumi.dexter.listener.PermissionGrantedResponse
import com.karumi.dexter.listener.PermissionRequest
import com.karumi.dexter.listener.single.PermissionListener
import kotlinx.android.synthetic.main.activity_main.*
import org.jetbrains.anko.*

class MainActivity : AppCompatActivity() {

    lateinit var contactsFragment: ContactsFragment
    lateinit var whatsappContactsFragment: WhatsappContactsFragment
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        setSupportActionBar(toolbar)
        supportActionBar?.title = "Sarvodya Ventures"

        setupViewPager()

        fab.setOnClickListener {
            askForPermission()
        }
    }

    private fun askForPermission() {
        Dexter.withActivity(this)
                .withPermission(android.Manifest.permission.WRITE_EXTERNAL_STORAGE)
                .withListener(object : PermissionListener {
                    override fun onPermissionGranted(response: PermissionGrantedResponse?) {
                        if (viewpager.currentItem == 0) {
                            saveContacts()
                        } else {
                            saveWhatsAppContacts()
                        }
                    }

                    override fun onPermissionRationaleShouldBeShown(permission: PermissionRequest?, token: PermissionToken?) {
                        token?.continuePermissionRequest()
                    }

                    override fun onPermissionDenied(response: PermissionDeniedResponse?) {
                        toast("Please allow permission")
                    }
                }).check()
    }

    private fun saveWhatsAppContacts() {
        val list = whatsappContactsFragment.getWhatsAppContactsList()

        val editText = EditText(this)
        val layoutParams = LinearLayout.LayoutParams(
                LinearLayout.LayoutParams.MATCH_PARENT,
                LinearLayout.LayoutParams.WRAP_CONTENT)
        layoutParams.setMargins(10, 0, 10, 0)


        alert(title = "Save whatsapp contacts", message = "Enter file name") {
            customView {
                this.addView(editText, layoutParams)
            }
            yesButton {
                if (!editText.text.toString().isBlank()) {
                    CsvWriter.saveToCsv(editText.text.toString().trim(), list)
                    toast("file saved successfully")
                } else toast("file name cannot be blank")
            }
            noButton {

            }
        }.show()
    }

    private fun saveContacts() {

        val editText = EditText(this)
        val layoutParams = LinearLayout.LayoutParams(
                LinearLayout.LayoutParams.MATCH_PARENT,
                LinearLayout.LayoutParams.WRAP_CONTENT)
        layoutParams.setMargins(10, 0, 10, 0)

        val list = contactsFragment.getContactsList()

        alert(title = "Save contacts", message = "Enter file name") {
            customView {
                this.addView(editText, layoutParams)
            }
            yesButton {
                if (!editText.text.toString().isBlank()) {
                    CsvWriter.saveToCsv(editText.text.toString().trim(), list)
                    toast("file saved successfully")
                } else toast("file name cannot be blank")
            }
            noButton {

            }
        }.show()
    }

    private fun setupViewPager() {
        val pagerAdapter = ViewPagerAdapter(supportFragmentManager)

        contactsFragment = ContactsFragment()
        whatsappContactsFragment = WhatsappContactsFragment()

        pagerAdapter.addFragment(contactsFragment, "Contacts")
        pagerAdapter.addFragment(whatsappContactsFragment, "WhatsApp Contacts")

        viewpager.adapter = pagerAdapter
        tabs.setupWithViewPager(viewpager)
    }

    override fun onCreateOptionsMenu(menu: Menu?): Boolean {
        menuInflater.inflate(R.menu.menu_main, menu)
        return super.onCreateOptionsMenu(menu)
    }

    override fun onOptionsItemSelected(item: MenuItem?): Boolean {
        return when (item?.itemId) {
            R.id.action_logout -> {
                logout()
                true
            }
            R.id.action_profile -> {
                openEditProfileScreen()
                true
            }
            else -> super.onOptionsItemSelected(item)
        }
    }

    private fun openEditProfileScreen() {
        startActivity(intentFor<SignUpActivity>("login" to false))
    }

    private fun logout() {
        PreferenceManager.getDefaultSharedPreferences(this).edit().clear().apply()
        startActivity<LoginActivity>()
        finish()
    }

}
