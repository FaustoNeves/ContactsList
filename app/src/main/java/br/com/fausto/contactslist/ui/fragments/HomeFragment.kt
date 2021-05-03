package br.com.fausto.contactslist.ui.fragments

import android.app.Dialog
import android.content.ClipData
import android.content.ClipboardManager
import android.content.Context
import android.content.Intent
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.LinearLayout
import android.widget.Toast
import androidx.core.os.bundleOf
import androidx.core.widget.addTextChangedListener
import androidx.fragment.app.Fragment
import androidx.fragment.app.setFragmentResult
import androidx.fragment.app.viewModels
import androidx.navigation.fragment.findNavController
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import br.com.fausto.contactslist.R
import br.com.fausto.contactslist.database.Contact
import br.com.fausto.contactslist.ui.adapters.ContactAdapter
import br.com.fausto.contactslist.ui.viewmodel.HomeVM
import com.google.android.material.textfield.TextInputEditText
import com.reddit.indicatorfastscroll.FastScrollItemIndicator
import com.reddit.indicatorfastscroll.FastScrollerThumbView
import com.reddit.indicatorfastscroll.FastScrollerView
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class HomeFragment : Fragment() {

    private val homeViewModel: HomeVM by viewModels()
    private lateinit var recyclerView: RecyclerView
    private lateinit var contactAdapter: ContactAdapter
    private lateinit var findField: TextInputEditText
    private lateinit var fastScrollerView: FastScrollerView
    private lateinit var fastScrollerThumbView: FastScrollerThumbView
    private lateinit var teste_lista: List<Contact>

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        return inflater.inflate(R.layout.fragment_home, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        recyclerView = requireView().findViewById(R.id.contact_recycler_view)
        findField = requireView().findViewById(R.id.textInputSearchEdit)
        fastScrollerView = requireView().findViewById(R.id.fast_scroller_thumb)
        fastScrollerThumbView = requireView().findViewById(R.id.sample_styled_fastscroller_thumb)
        fastScrollerThumbView.setupWithFastScroller(fastScrollerView)
        initRecyclerView()

        findField.addTextChangedListener {
            homeViewModel.contacts.observe(viewLifecycleOwner, { list ->
                val tempList = mutableListOf<Contact>()
                for (subscriber in list) {
                    if (subscriber.name.contains(it.toString())) {
                        tempList.add(subscriber)
                    }
                    if (it.toString().isBlank()) {
                        contactAdapter.setList(list)
                    }
                    contactAdapter.setList(tempList)
                }
                contactAdapter.notifyDataSetChanged()
            })
        }

        homeViewModel.contactStatus.observe(viewLifecycleOwner, { it ->
            it.getContentIfNotHandled()?.let {
                Toast.makeText(context, it.message.toString(), Toast.LENGTH_SHORT).show()
            }
        })

        homeViewModel.contacts.observe(viewLifecycleOwner, {
            teste_lista = it
        })
        fastScrollerView.setupWithRecyclerView(
            recyclerView,
            { position ->
                val item = teste_lista.sortedBy { it.name }[position]
                FastScrollItemIndicator.Text(
                    item.name.substring(0, 1).toUpperCase()
                )
            }
        )
    }

    private fun initRecyclerView() {
        recyclerView.layoutManager = LinearLayoutManager(context)
        contactAdapter = ContactAdapter(requireContext()) { listItemClicked(it) }
        recyclerView.adapter = contactAdapter
        displayContactsList()

        homeViewModel.contacts.observe(viewLifecycleOwner, {
        })
    }

    private fun displayContactsList() {
        homeViewModel.contacts.observe(viewLifecycleOwner, {
            contactAdapter.setList(it)
            contactAdapter.notifyDataSetChanged()
        })
    }

    private fun listItemClicked(contact: Contact) {

        val dialog = Dialog(requireContext())
        dialog.setContentView(R.layout.contact_dialog)
        val closeLayout: LinearLayout = dialog.findViewById(R.id.cancel_layout)
        val excludeLayout: LinearLayout = dialog.findViewById(R.id.exclude_layout)
        val editLayout: LinearLayout = dialog.findViewById(R.id.edit_layout)
        val callLayout: LinearLayout = dialog.findViewById(R.id.call_layout)
        val emailLayout: LinearLayout = dialog.findViewById(R.id.email_layout)

        dialog.show()
        callLayout.setOnClickListener { makePhoneCall(contact.phoneNumber) }

        emailLayout.setOnClickListener {
            val clipboard =
                requireActivity().getSystemService(Context.CLIPBOARD_SERVICE) as ClipboardManager?
            val clip = ClipData.newPlainText("SUBSCRIBER_EMAIL", contact.email)
            clipboard!!.setPrimaryClip(clip)
            Toast.makeText(
                requireContext(),
                "Email address added to your clipboard",
                Toast.LENGTH_SHORT
            ).show()
            composeEmail()
        }

        editLayout.setOnClickListener {
            setFragmentResult("subscriber", bundleOf("SUBSCRIBER_UPDATE" to contact))
            findNavController().navigate(HomeFragmentDirections.actionHomeFragmentToRegisterFragment())
            dialog.dismiss()
        }
        excludeLayout.setOnClickListener {
            homeViewModel.delete(contact)
            contactAdapter.notifyDataSetChanged()
            dialog.dismiss()
        }
        closeLayout.setOnClickListener { dialog.dismiss() }
    }

    private fun makePhoneCall(number: String): Boolean = try {
        val intent = Intent(Intent.ACTION_DIAL)
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