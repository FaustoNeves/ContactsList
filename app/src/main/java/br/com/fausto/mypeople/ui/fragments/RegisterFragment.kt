package br.com.fausto.mypeople.ui.fragments

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Button
import android.widget.Toast
import androidx.fragment.app.Fragment
import br.com.fausto.mypeople.R
import br.com.fausto.mypeople.database.subscriber.Subscriber
import br.com.fausto.mypeople.database.subscriber.SubscriberDatabase
import br.com.fausto.mypeople.repository.subscriber.RSubscriber
import com.google.android.material.textfield.TextInputEditText
import kotlinx.coroutines.GlobalScope
import kotlinx.coroutines.launch


class RegisterFragment : Fragment() {

    private lateinit var inputName: TextInputEditText
    private lateinit var inputEmail: TextInputEditText
    private lateinit var inputCel: TextInputEditText
    lateinit var repository: RSubscriber
    var subscriberToUpdate: Subscriber? = null

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        val bundle = this.arguments
        if (bundle != null) {
            subscriberToUpdate = bundle.getSerializable("SUBSCRIBER_UPDATE") as Subscriber?
        }
        val subscriberDAO =
            SubscriberDatabase.getInstance(activity?.applicationContext!!).subscriberDAO
        repository = RSubscriber(subscriberDAO)
        return inflater.inflate(R.layout.fragment_register, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        inputName = requireView().findViewById(R.id.textInputName)
        inputEmail = requireView().findViewById(R.id.textInputEmail)
        inputCel = requireView().findViewById(R.id.textInputCel)
        super.onViewCreated(view, savedInstanceState)
        val confirmButton = requireView().findViewById<Button>(R.id.save_update_button)
        val clearButton = requireView().findViewById<Button>(R.id.clear_button)

        setupUpdateState()

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

    }

    private fun setupUpdateState() {
        if (subscriberToUpdate != null) {
            inputName.setText(subscriberToUpdate!!.name)
            inputEmail.setText(subscriberToUpdate!!.email)
            inputCel.setText(subscriberToUpdate!!.phoneNumber)
        }
    }

    private fun saveContact(subscriber: Subscriber) {
        GlobalScope.launch {
            if (subscriberToUpdate != null) {
                subscriberToUpdate!!.name = inputName.text.toString()
                subscriberToUpdate!!.email = inputEmail.text.toString()
                subscriberToUpdate!!.phoneNumber = inputCel.text.toString()
                repository.update(subscriberToUpdate!!)
            } else {
                repository.insert(subscriber)
            }
        }
        Toast.makeText(requireContext(), "Done!", Toast.LENGTH_SHORT).show()
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
