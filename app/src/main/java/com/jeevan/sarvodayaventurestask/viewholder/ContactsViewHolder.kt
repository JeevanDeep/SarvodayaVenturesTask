package com.jeevan.sarvodayaventurestask.viewholder

import android.support.v7.widget.RecyclerView
import android.view.View
import android.widget.ImageView
import android.widget.TextView
import com.jeevan.sarvodayaventurestask.model.ContactsModel
import kotlinx.android.synthetic.main.contact_item_row.view.*

class ContactsViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
    val name: TextView = itemView.tvName
    val phoneNumber: TextView = itemView.tvPhoneNumber
    val contactImage: ImageView = itemView.ivContact

    fun setItems(currentContact: ContactsModel) {
        name.text = currentContact.name
        phoneNumber.text = currentContact.phoneNumber
    }

}