package br.com.fausto.contactslist.ui.fragments

import android.content.Context
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.view.inputmethod.InputMethodManager
import android.widget.Button
import android.widget.LinearLayout
import android.widget.Toast
import androidx.activity.OnBackPressedCallback
import androidx.fragment.app.Fragment
import androidx.fragment.app.setFragmentResultListener
import androidx.fragment.app.viewModels
import androidx.navigation.fragment.findNavController
import br.com.fausto.contactslist.R
import br.com.fausto.contactslist.database.Contact
import br.com.fausto.contactslist.ui.viewmodel.RegisterVM
import br.com.fausto.contactslist.utils.Status
import com.google.android.material.textfield.TextInputEditText
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.coroutines.GlobalScope
import kotlinx.coroutines.launch

@AndroidEntryPoint
class RegisterFragment : Fragment() {
    private val registerViewModel: RegisterVM by viewModels()

    private lateinit var inputName: TextInputEditText
    private lateinit var inputEmail: TextInputEditText
    private lateinit var inputCel: TextInputEditText
    private lateinit var confirmButton: Button
    var contactToUpdate: Contact? = null
    var isUpdate: Boolean = false

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        return inflater.inflate(R.layout.fragment_register, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        inputName = requireView().findViewById(R.id.textInputName)
        inputEmail = requireView().findViewById(R.id.textInputEmail)
        inputCel = requireView().findViewById(R.id.textInputCel)
        confirmButton = requireView().findViewById(R.id.save_update_button)
        super.onViewCreated(view, savedInstanceState)
        val confirmButton = requireView().findViewById<Button>(R.id.save_update_button)
        val clearButton = requireView().findViewById<Button>(R.id.clear_button)

        setFragmentResultListener("subscriber") { _, bundle ->
            contactToUpdate = bundle.getSerializable("SUBSCRIBER_UPDATE") as Contact
            setupUpdateState(bundle.getSerializable("SUBSCRIBER_UPDATE") as Contact)
            isUpdate = true
            confirmButton.setText(R.string.update)
        }

        confirmButton.setOnClickListener {
            saveContact(
                Contact(
                    0,
                    inputName.text.toString(),
                    inputEmail.text.toString(),
                    inputCel.text.toString()
                )
            )
        }

        clearButton.setOnClickListener { clearFields() }

        registerViewModel.contactStatus.observe(viewLifecycleOwner, { it ->
            it.getContentIfNotHandled()?.let {
                Toast.makeText(context, it.message, Toast.LENGTH_SHORT).show()
                if ((it.status) == Status.SUCCESS) {
                    clearFields()
                    requireView().findViewById<LinearLayout>(R.id.mainLinearLayout).requestFocus()
                    requireView().hideKeyboard()
                    confirmButton.setText(R.string.register)
                }
            }
        })

        val callback = object : OnBackPressedCallback(true) {
            override fun handleOnBackPressed() {
                findNavController().popBackStack()
            }
        }
        requireActivity().onBackPressedDispatcher.addCallback(callback)
    }

    private fun setupUpdateState(contactToUpdate: Contact) {
        inputName.setText(contactToUpdate.name)
        inputEmail.setText(contactToUpdate.email)
        inputCel.setText(contactToUpdate.phoneNumber)
    }

    private fun saveContact(contact: Contact) {
        GlobalScope.launch {
            if (confirmButton.text.equals(resources.getString(R.string.update))) {
                contactToUpdate!!.name = inputName.text.toString()
                contactToUpdate!!.email = inputEmail.text.toString()
                contactToUpdate!!.phoneNumber = inputCel.text.toString()
                registerViewModel.addOrUpdate(contactToUpdate!!)
            } else {
                registerViewModel.addOrUpdate(contact)
            }
        }
    }

    private fun clearFields() {
        inputName.setText("")
        inputEmail.setText("")
        inputCel.setText("")
    }

    private fun View.hideKeyboard() {
        val imm = context.getSystemService(Context.INPUT_METHOD_SERVICE) as InputMethodManager
        imm.hideSoftInputFromWindow(windowToken, 0)
    }
}
