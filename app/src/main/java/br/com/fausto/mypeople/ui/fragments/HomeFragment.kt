package br.com.fausto.mypeople.ui.fragments

import android.app.Dialog
import android.content.ClipData
import android.content.ClipboardManager
import android.content.Context
import android.content.Intent
import android.net.Uri
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.LinearLayout
import android.widget.Toast
import androidx.core.widget.addTextChangedListener
import androidx.fragment.app.Fragment
import androidx.lifecycle.ViewModelProvider
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import br.com.fausto.mypeople.R
import br.com.fausto.mypeople.database.subscriber.Subscriber
import br.com.fausto.mypeople.database.subscriber.SubscriberDatabase
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
        return inflater.inflate(R.layout.fragment_home, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        val subscriberDAO =
            SubscriberDatabase.getInstance(activity?.applicationContext!!).subscriberDAO
        val repository = RSubscriber(subscriberDAO)
        val factory = SubscriberVMFactory(repository)
        subscriberViewModel =
            ViewModelProvider(this@HomeFragment, factory).get(SubscriberVM::class.java)

        initRecyclerView()
        findField = requireView().findViewById(R.id.textInputSearchEdit)
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

        subscriberViewModel.message.observe(viewLifecycleOwner, { it ->
            it.getContentIfNotHandled()?.let { message ->
                Toast.makeText(context, message, Toast.LENGTH_SHORT).show()
            }
        })
    }

    private fun initRecyclerView() {
        val recyclerView = view?.findViewById<RecyclerView>(R.id.subscriber_recycler_view)
        recyclerView!!.layoutManager = LinearLayoutManager(context)
        subscriperAdapter = SubscriberAdapter(requireContext()) { listItemClicked(it) }
        recyclerView.adapter = subscriperAdapter
        displaySubscribersList()
    }

    private fun displaySubscribersList() {
        subscriberViewModel.subscribers.observe(viewLifecycleOwner, {
            subscriperAdapter.setList(it)
            subscriperAdapter.notifyDataSetChanged()
        })
    }

    private fun listItemClicked(subscriber: Subscriber) {
//        subscriberViewModel.setupUpdate(subscriber)

        val dialog = Dialog(requireContext())
        dialog.setContentView(R.layout.contact_dialog)
        val closeLayout: LinearLayout = dialog.findViewById(R.id.cancel_layout)
        val excludeLayout: LinearLayout = dialog.findViewById(R.id.exclude_layout)
        val editLayout: LinearLayout = dialog.findViewById(R.id.edit_layout)
        val callLayout: LinearLayout = dialog.findViewById(R.id.call_layout)
        val emailLayout: LinearLayout = dialog.findViewById(R.id.email_layout)

        dialog.show()
        closeLayout.setOnClickListener {
            dialog.dismiss()
        }

        callLayout.setOnClickListener {
            context?.makePhoneCall(subscriber.phoneNumber)
        }

        emailLayout.setOnClickListener {
            val clipboard =
                requireActivity().getSystemService(Context.CLIPBOARD_SERVICE) as ClipboardManager?
            val clip = ClipData.newPlainText("SUBSCRIBER_EMAIL", subscriber.email)
            clipboard!!.setPrimaryClip(clip)
            Toast.makeText(
                requireContext(),
                "Email address added to your clipboard",
                Toast.LENGTH_SHORT
            ).show()
            composeEmail()
        }

        excludeLayout.setOnClickListener {
            subscriberViewModel.delete(subscriber)
            subscriperAdapter.notifyDataSetChanged()
            dialog.dismiss()
        }
        editLayout.setOnClickListener {
            val registerFragment = RegisterFragment()
            val bundle = Bundle()
            bundle.putSerializable("SUBSCRIBER_UPDATE", subscriber)
            registerFragment.arguments = bundle
            parentFragmentManager.beginTransaction().replace(R.id.fragmentHost, registerFragment)
                .commit()
            dialog.dismiss()
        }
    }

    private fun Context.makePhoneCall(number: String): Boolean = try {
        val intent = Intent(Intent.ACTION_DIAL, Uri.parse("tel:$number"))
        startActivity(intent)
        true
    } catch (e: Exception) {
        e.printStackTrace()
        false
    }

    private fun composeEmail() {
        val email = Intent(Intent.ACTION_SEND)
        email.type = "message/rfc822"
        startActivity(Intent.createChooser(email, "Choose an app:"))
    }
}