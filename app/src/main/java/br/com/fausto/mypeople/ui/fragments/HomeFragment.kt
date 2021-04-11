package br.com.fausto.mypeople.ui.fragments

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.core.widget.addTextChangedListener
import androidx.fragment.app.Fragment
import androidx.lifecycle.ViewModelProvider
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import br.com.fausto.mypeople.R
import br.com.fausto.mypeople.database.subscriber.Subscriber
import br.com.fausto.mypeople.database.subscriber.SubscriberDatabase
import br.com.fausto.mypeople.databinding.FragmentHomeBinding
import br.com.fausto.mypeople.repository.subscriber.RSubscriber
import br.com.fausto.mypeople.ui.adapters.SubscriberAdapter
import br.com.fausto.mypeople.ui.viewmodel.SubscriberVM
import br.com.fausto.mypeople.ui.viewmodel.SubscriberVMFactory
import com.google.android.material.textfield.TextInputEditText

class HomeFragment : Fragment() {

    private lateinit var subscriberViewModel: SubscriberVM
    private lateinit var subscriperAdapter: SubscriberAdapter
    private lateinit var findField: TextInputEditText

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {

        val subscriberDAO =
            SubscriberDatabase.getInstance(activity?.applicationContext!!).subscriberDAO
        val repository = RSubscriber(subscriberDAO)
        val factory = SubscriberVMFactory(repository)
        return FragmentHomeBinding.inflate(inflater, container, false).apply {
            lifecycleOwner = viewLifecycleOwner
            subscriberViewModel =
                ViewModelProvider(this@HomeFragment, factory).get(SubscriberVM::class.java)
        }.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        initRecyclerView()

        findField.addTextChangedListener {
            subscriberViewModel.subscribers.observe(viewLifecycleOwner, { list ->
                var tempList = mutableListOf<Subscriber>()
                for (subscriber in list) {
                    if (subscriber.name.contains(it.toString())) {
                        tempList.add(subscriber)
                    }
                    if (it.toString().isBlank()) {
                        subscriperAdapter.setList(list)
                        subscriperAdapter.notifyDataSetChanged()
                    }
                    subscriperAdapter.setList(tempList)
                    subscriperAdapter.notifyDataSetChanged()
                }
            })
        }
    }

    fun initRecyclerView() {
        var recyclerView = getView()?.findViewById<RecyclerView>(R.id.subscriber_recycler_view)
        recyclerView!!.layoutManager = LinearLayoutManager(context)
        subscriperAdapter = SubscriberAdapter { listItemClicked(it) }
        recyclerView.adapter = subscriperAdapter
        displaySubscribersList()
    }

    fun displaySubscribersList() {
        subscriberViewModel.subscribers.observe(viewLifecycleOwner, {
            subscriperAdapter.setList(it)
            subscriperAdapter.notifyDataSetChanged()
        })
    }

    fun listItemClicked(subscriber: Subscriber) {
        subscriberViewModel.initUpdateAndDelete(subscriber)
    }
}