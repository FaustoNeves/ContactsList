package br.com.fausto.mypeople.ui.fragments

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Button
import android.widget.Toast
import androidx.fragment.app.Fragment
import androidx.fragment.app.setFragmentResultListener
import androidx.fragment.app.viewModels
import br.com.fausto.mypeople.R
import br.com.fausto.mypeople.database.Subscriber
import br.com.fausto.mypeople.ui.viewmodel.RegisterVM
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
        registerViewModel.message.observe(viewLifecycleOwner, { it ->
            Toast.makeText(context, it.toString(), Toast.LENGTH_SHORT).show()
        })
    }

    private fun setupUpdateState(subscriberToUpdate: Subscriber) {
        inputName.setText(subscriberToUpdate!!.name)
        inputEmail.setText(subscriberToUpdate!!.email)
        inputCel.setText(subscriberToUpdate!!.phoneNumber)
    }

    private fun saveContact(subscriber: Subscriber) {
        GlobalScope.launch {
            if (subscriberToUpdate != null) {
                subscriberToUpdate!!.name = inputName.text.toString()
                subscriberToUpdate!!.email = inputEmail.text.toString()
                subscriberToUpdate!!.phoneNumber = inputCel.text.toString()
                registerViewModel.update(subscriberToUpdate!!)
            } else {
                registerViewModel.add(subscriber)
            }
        }
        clearFields()
    }

    private fun clearFields() {
        inputName.setText("")
        inputEmail.setText("")
        inputCel.setText("")
    }

    override fun onDestroy() {
        super.onDestroy()
        subscriberToUpdate = null
    }
}
