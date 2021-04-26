package br.com.fausto.mypeople.ui.fragments

import android.content.Context
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.view.inputmethod.InputMethodManager
import android.widget.Button
import android.widget.LinearLayout
import android.widget.Toast
import androidx.fragment.app.Fragment
import androidx.fragment.app.setFragmentResultListener
import androidx.fragment.app.viewModels
import br.com.fausto.mypeople.R
import br.com.fausto.mypeople.database.Subscriber
import br.com.fausto.mypeople.ui.viewmodel.RegisterVM
import br.com.fausto.mypeople.utils.Status
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
    var subscriberToUpdate: Subscriber? = null
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
        super.onViewCreated(view, savedInstanceState)
        val confirmButton = requireView().findViewById<Button>(R.id.save_update_button)
        val clearButton = requireView().findViewById<Button>(R.id.clear_button)

        setFragmentResultListener("subscriber") { _, bundle ->
            subscriberToUpdate = bundle.getSerializable("SUBSCRIBER_UPDATE") as Subscriber
            setupUpdateState(bundle.getSerializable("SUBSCRIBER_UPDATE") as Subscriber)
            isUpdate = true
        }

        confirmButton.setOnClickListener {
            saveContact(
                Subscriber(
                    0,
                    inputName.text.toString(),
                    inputEmail.text.toString(),
                    inputCel.text.toString()
                )
            )
        }

        clearButton.setOnClickListener { clearFields() }

        registerViewModel.subscriberStatus.observe(viewLifecycleOwner, { it ->
            it.getContentIfNotHandled()?.let {
                Toast.makeText(context, it.message, Toast.LENGTH_SHORT).show()
                if ((it.status) == Status.SUCCESS) {
                    clearFields()
                    requireView().findViewById<LinearLayout>(R.id.mainLinearLayout).requestFocus()
                    requireView().hideKeyboard()
                }
            }
        })
    }

    private fun setupUpdateState(subscriberToUpdate: Subscriber) {
        inputName.setText(subscriberToUpdate.name)
        inputEmail.setText(subscriberToUpdate.email)
        inputCel.setText(subscriberToUpdate.phoneNumber)
    }

    private fun saveContact(subscriber: Subscriber) {
        GlobalScope.launch {
            if (isUpdate) {
                subscriberToUpdate!!.name = inputName.text.toString()
                subscriberToUpdate!!.email = inputEmail.text.toString()
                subscriberToUpdate!!.phoneNumber = inputCel.text.toString()
                registerViewModel.update(subscriberToUpdate!!)
            } else {
                registerViewModel.add(subscriber)
            }
        }
    }

    private fun clearFields() {
        inputName.setText("")
        inputEmail.setText("")
        inputCel.setText("")
    }

    fun View.hideKeyboard() {
        val imm = context.getSystemService(Context.INPUT_METHOD_SERVICE) as InputMethodManager
        imm.hideSoftInputFromWindow(windowToken, 0)
    }
}
