package com.jeevan.sarvodayaventurestask.fragment


import android.content.ContentResolver
import android.os.Bundle
import android.provider.ContactsContract
import android.support.v4.app.Fragment
import android.support.v7.widget.LinearLayoutManager
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import com.jeevan.sarvodayaventurestask.R
import com.jeevan.sarvodayaventurestask.adapter.ContactsAdapter
import com.jeevan.sarvodayaventurestask.model.ContactsModel
import com.karumi.dexter.Dexter
import com.karumi.dexter.PermissionToken
import com.karumi.dexter.listener.PermissionDeniedResponse
import com.karumi.dexter.listener.PermissionGrantedResponse
import com.karumi.dexter.listener.PermissionRequest
import com.karumi.dexter.listener.single.PermissionListener
import kotlinx.android.synthetic.main.fragment_contacts.*
import org.jetbrains.anko.doAsync
import org.jetbrains.anko.uiThread


class ContactsFragment : Fragment() {

    private var contactList = mutableListOf<ContactsModel>()
    lateinit var contactAdapter: ContactsAdapter
    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?,
                              savedInstanceState: Bundle?): View? {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_contacts, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        contactAdapter = ContactsAdapter(contactList)
        askPermission()
        recyclerView.apply {
            layoutManager = LinearLayoutManager(this@ContactsFragment.context)
            adapter = contactAdapter
        }
    }

    private fun askPermission() {
        Dexter.withActivity(requireActivity())
                .withPermission(android.Manifest.permission.READ_CONTACTS)
                .withListener(object : PermissionListener {
                    override fun onPermissionGranted(response: PermissionGrantedResponse?) {
                        getContacts()
                    }

                    override fun onPermissionRationaleShouldBeShown(permission: PermissionRequest?, token: PermissionToken?) {
                        token?.continuePermissionRequest()
                    }

                    override fun onPermissionDenied(response: PermissionDeniedResponse?) {
                        Toast.makeText(context, "Please accept contact permission", Toast.LENGTH_SHORT).show()
                    }
                }).check()
    }

    private fun getContacts() {
        doAsync {

            val resolver: ContentResolver? = activity?.contentResolver
            val cursor = resolver?.query(ContactsContract.CommonDataKinds.Phone.CONTENT_URI, null, null, null, ContactsContract.CommonDataKinds.Phone.DISPLAY_NAME + " ASC")
            while (cursor?.moveToNext() == true) {

                val id = cursor.getString(cursor.getColumnIndex(ContactsContract.Contacts._ID))
                val name = cursor.getString(cursor.getColumnIndex(ContactsContract.Contacts.DISPLAY_NAME))
                val phoneNumber = cursor.getString(cursor.getColumnIndex(ContactsContract.CommonDataKinds.Phone.NUMBER))

                contactList.add(ContactsModel(phoneNumber, id, name))
            }

            cursor?.close()
            uiThread {

                contactAdapter.notifyDataSetChanged()
            }
        }

    }

    fun getContactsList(): MutableList<ContactsModel> = contactList
}
