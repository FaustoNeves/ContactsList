package br.com.fausto.mypeople.ui.adapters

import android.content.Context
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.RelativeLayout
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import br.com.fausto.mypeople.R
import br.com.fausto.mypeople.database.Subscriber

class SubscriberAdapter(
    private val context: Context,
    private val clickListener: (Subscriber) -> Unit
) :
    RecyclerView.Adapter<MyViewHolder>() {

    private val subscribersList = ArrayList<Subscriber>()

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): MyViewHolder {
        val view =
            LayoutInflater.from(context).inflate(R.layout.subscriber_list_item, parent, false)

        return MyViewHolder(view)
    }

    override fun onBindViewHolder(holder: MyViewHolder, position: Int) {
        holder.bind(subscribersList[position], clickListener)
    }

    fun setList(subscribers: List<Subscriber>) {
        subscribersList.clear()
        subscribersList.addAll(subscribers)
    }

    override fun getItemCount(): Int {
        return subscribersList.size
    }
}

class MyViewHolder(private val itemInList: View) : RecyclerView.ViewHolder(itemInList) {
    fun bind(subscriber: Subscriber, clickListener: (Subscriber) -> Unit) {
        val nameView: TextView = itemInList.findViewById(R.id.name_text_view)
        val emailView: TextView = itemInList.findViewById(R.id.email_text_view)
        val numberView: TextView = itemInList.findViewById(R.id.number_text_view)
        val itemLayout: RelativeLayout = itemInList.findViewById(R.id.list_item_layout)
        nameView.text = subscriber.name
        emailView.text = subscriber.email
        numberView.text = subscriber.phoneNumber
        itemLayout.setOnClickListener {
            clickListener(subscriber)
        }
    }
}