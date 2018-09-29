package com.jeevan.sarvodayaventurestask.fragment


import android.os.Bundle
import android.provider.ContactsContract
import android.support.v4.app.Fragment
import android.support.v7.widget.LinearLayoutManager
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import com.jeevan.sarvodayaventurestask.R
import com.jeevan.sarvodayaventurestask.adapter.ContactsAdapter
import com.jeevan.sarvodayaventurestask.model.ContactsModel
import com.karumi.dexter.Dexter
import com.karumi.dexter.PermissionToken
import com.karumi.dexter.listener.PermissionDeniedResponse
import com.karumi.dexter.listener.PermissionGrantedResponse
import com.karumi.dexter.listener.PermissionRequest
import com.karumi.dexter.listener.single.PermissionListener
import kotlinx.android.synthetic.main.fragment_whatsapp_contacts.*
import org.jetbrains.anko.doAsync
import org.jetbrains.anko.support.v4.toast
import org.jetbrains.anko.uiThread


class WhatsappContactsFragment : Fragment() {

    private val whatsappContactList = mutableListOf<ContactsModel>()
    lateinit var whatsAppContactsAdapter: ContactsAdapter

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?,
                              savedInstanceState: Bundle?): View? {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_whatsapp_contacts, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        whatsAppContactsAdapter = ContactsAdapter(whatsappContactList)
        recyclerView.apply {
            adapter = whatsAppContactsAdapter
            layoutManager = LinearLayoutManager(this@WhatsappContactsFragment.context)
        }


    }

    override fun onResume() {
        super.onResume()
        Dexter.withActivity(activity)
                .withPermission(android.Manifest.permission.READ_CONTACTS)
                .withListener(object : PermissionListener {
                    override fun onPermissionGranted(response: PermissionGrantedResponse?) {
                        getContacts()
                    }

                    override fun onPermissionRationaleShouldBeShown(permission: PermissionRequest?, token: PermissionToken?) {
                        token?.continuePermissionRequest()
                    }

                    override fun onPermissionDenied(response: PermissionDeniedResponse?) {
                        toast("Please accept permission")
                    }
                }).check()
    }
    private fun getContacts() {
        doAsync {
            val projection = arrayOf(ContactsContract.Data.CONTACT_ID, ContactsContract.Data.DISPLAY_NAME, ContactsContract.Data.MIMETYPE, "account_type", ContactsContract.Data.DATA3)

            val selection = ContactsContract.Data.MIMETYPE + " =? and account_type=?"
            val selectionArgs = arrayOf("vnd.android.cursor.item/vnd.com.whatsapp.profile", "com.whatsapp")

            val cr = context?.contentResolver
            val c = cr?.query(
                    ContactsContract.Data.CONTENT_URI,
                    projection,
                    selection,
                    selectionArgs,
                    ContactsContract.CommonDataKinds.Phone.DISPLAY_NAME + " ASC")

            while (c?.moveToNext() == true) {
                val id = c.getString(c.getColumnIndex(ContactsContract.Data.CONTACT_ID))
                var number = c.getString(c.getColumnIndex(ContactsContract.Data.DATA3))
                val name = c.getString(c.getColumnIndex(ContactsContract.Contacts.DISPLAY_NAME))

                number = number.removePrefix("Message ")
                whatsappContactList.add(ContactsModel(number, id, name))

            }
            c?.close()

            uiThread {
                //                println("size is " + whatsappContactList.size)
                whatsAppContactsAdapter.notifyDataSetChanged()
            }
        }

    }
    fun getWhatsAppContactsList(): MutableList<ContactsModel> = whatsappContactList

}
