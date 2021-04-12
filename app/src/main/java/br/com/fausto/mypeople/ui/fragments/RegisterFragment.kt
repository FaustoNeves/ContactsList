package br.com.fausto.mypeople.ui.fragments

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Button
import androidx.fragment.app.Fragment
import androidx.lifecycle.ViewModelProvider
import br.com.fausto.mypeople.R
import br.com.fausto.mypeople.database.subscriber.SubscriberDatabase
import br.com.fausto.mypeople.repository.subscriber.RSubscriber
import br.com.fausto.mypeople.ui.viewmodel.SubscriberVM
import br.com.fausto.mypeople.ui.viewmodel.SubscriberVMFactory
import com.google.android.material.textfield.TextInputEditText

class RegisterFragment : Fragment() {

    private lateinit var subscriberViewModel: SubscriberVM
    private lateinit var inputName: TextInputEditText
    private lateinit var inputEmail: TextInputEditText
    private lateinit var inputCel: TextInputEditText

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
        val subscriberDAO =
            SubscriberDatabase.getInstance(activity?.applicationContext!!).subscriberDAO
        val repository = RSubscriber(subscriberDAO)
        val factory = SubscriberVMFactory(repository)
        subscriberViewModel =
            ViewModelProvider(this@RegisterFragment, factory).get(SubscriberVM::class.java)

        var confirmButton = requireView().findViewById<Button>(R.id.save_update_button)
        var cancelButton = requireView().findViewById<Button>(R.id.clear_delete_button)

        confirmButton.setOnClickListener {
            registerContact(inputName.text.toString(), inputEmail.text.toString())
        }
    }

    private fun registerContact(name: String, email: String) {
        subscriberViewModel.saveOrUpdate(name, email)
    }
}
