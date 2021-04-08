package br.com.fausto.mypeople.ui.activities

import android.os.Bundle
import android.util.Log
import android.view.View
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import androidx.core.widget.addTextChangedListener
import androidx.databinding.DataBindingUtil
import androidx.lifecycle.ViewModelProvider
import androidx.recyclerview.widget.LinearLayoutManager
import br.com.fausto.mypeople.R
import br.com.fausto.mypeople.database.subscriber.Subscriber
import br.com.fausto.mypeople.database.subscriber.SubscriberDatabase
import br.com.fausto.mypeople.databinding.ActivityMainBinding
import br.com.fausto.mypeople.repository.subscriber.RSubscriber
import br.com.fausto.mypeople.ui.adapters.SubscriberAdapter
import br.com.fausto.mypeople.ui.viewmodel.SubscriberVM
import br.com.fausto.mypeople.ui.viewmodel.SubscriberVMFactory
import com.google.android.material.textfield.TextInputEditText

//https://developer.android.com/jetpack/guide?hl=en-us

class MainActivity : AppCompatActivity() {

    private lateinit var binding: ActivityMainBinding
    private lateinit var subscriberViewModel: SubscriberVM
    private lateinit var subscriperAdapter: SubscriberAdapter
    private lateinit var findField: TextInputEditText

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        binding = DataBindingUtil.setContentView(this, R.layout.activity_main)
        findField = findViewById(R.id.textInputSearchEdit)
        val subscriberDAO = SubscriberDatabase.getInstance(this).subscriberDAO
        val repository = RSubscriber(subscriberDAO)
        val factory = SubscriberVMFactory(repository)
        subscriberViewModel = ViewModelProvider(this, factory).get(SubscriberVM::class.java)
        binding.viewModel = subscriberViewModel
        binding.lifecycleOwner = this
        initRecyclerView()

        subscriberViewModel.message.observe(this, { it ->
            it.getContentIfNotHandled()?.let {
                Toast.makeText(this, it, Toast.LENGTH_SHORT).show()
            }
        })

        findField.addTextChangedListener {
            Toast.makeText(this, it.toString(), Toast.LENGTH_SHORT).show()
        }

    }

    fun findSubscriber(view: View, name: String) {
        subscriberViewModel.subscribers.observe(this, {
            if (it.contains(name)) {
                Toast.makeText(this, "Usuário encontrado", Toast.LENGTH_SHORT).show()
            }
        })
    }

    private fun initRecyclerView() {
        binding.subscriberRecyclerView.layoutManager = LinearLayoutManager(this)
        subscriperAdapter = SubscriberAdapter { listItemClicked(it) }
        binding.subscriberRecyclerView.adapter = subscriperAdapter
        displaySubscribersList()
    }

    private fun displaySubscribersList() {
        //List of subscribers (wich comes from database) in VM is OBSERVED and passed to the adapter
        subscriberViewModel.subscribers.observe(this, {
            Log.e("observable in main", it.toString())
            subscriperAdapter.setList(it)
            subscriperAdapter.notifyDataSetChanged()
        })
    }

    private fun listItemClicked(subscriber: Subscriber) {
        subscriberViewModel.initUpdateAndDelete(subscriber)
    }
}