package com.jeevan.sarvodayaventurestask.adapter

import android.support.v7.widget.RecyclerView
import android.view.LayoutInflater
import android.view.ViewGroup
import com.jeevan.sarvodayaventurestask.viewholder.ContactsViewHolder
import com.jeevan.sarvodayaventurestask.R
import com.jeevan.sarvodayaventurestask.model.ContactsModel

class ContactsAdapter : RecyclerView.Adapter<ContactsViewHolder> {

    private val contactList: MutableList<ContactsModel>

    constructor(contactList: MutableList<ContactsModel>) {
        this.contactList = contactList
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewtType: Int): ContactsViewHolder {
        val v = LayoutInflater.from(parent.context).inflate(R.layout.contact_item_row, parent, false)
        return ContactsViewHolder(v)
    }

    override fun getItemCount(): Int {
        return contactList.size
    }

    override fun onBindViewHolder(viewHolder: ContactsViewHolder, position: Int) {
        viewHolder.setItems(contactList[position])
    }

}