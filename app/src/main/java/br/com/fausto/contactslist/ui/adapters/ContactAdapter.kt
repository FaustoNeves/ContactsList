package br.com.fausto.contactslist.ui.adapters

import android.content.Context
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.RelativeLayout
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import br.com.fausto.contactslist.R
import br.com.fausto.contactslist.database.Contact

class ContactAdapter(
    private val context: Context,
    private val clickListener: (Contact) -> Unit
) :     RecyclerView.Adapter<MyViewHolder>() {

    private val contactsList = ArrayList<Contact>()

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): MyViewHolder {
        val view =
            LayoutInflater.from(context).inflate(R.layout.contact_list_item, parent, false)

        return MyViewHolder(view)
    }

    override fun onBindViewHolder(holder: MyViewHolder, position: Int) {
        holder.bind(contactsList[position], clickListener)
    }

    fun setList(contacts: List<Contact>) {
        contactsList.clear()
        contactsList.addAll(contacts.sortedBy { it.name })
    }

    override fun getItemCount(): Int {
        return contactsList.size
    }
}

class MyViewHolder(private val itemInList: View) : RecyclerView.ViewHolder(itemInList) {
    fun bind(contact: Contact, clickListener: (Contact) -> Unit) {
        val nameView: TextView = itemInList.findViewById(R.id.name_text_view)
        val emailView: TextView = itemInList.findViewById(R.id.email_text_view)
        val numberView: TextView = itemInList.findViewById(R.id.number_text_view)
        val itemLayout: RelativeLayout = itemInList.findViewById(R.id.list_item_layout)
        nameView.text = contact.name
        emailView.text = contact.email
        numberView.text = contact.phoneNumber
        itemLayout.setOnClickListener {
            clickListener(contact)
        }
    }
}