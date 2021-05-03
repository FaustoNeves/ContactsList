package br.com.fausto.contactslist.ui.viewmodel

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import br.com.fausto.contactslist.database.Contact
import br.com.fausto.contactslist.repository.IContactRepository
import br.com.fausto.contactslist.utils.Event
import br.com.fausto.contactslist.utils.Resource
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.runBlocking
import javax.inject.Inject

@HiltViewModel
class RegisterVM @Inject constructor(
    private val repository: IContactRepository
) : ViewModel() {

    private val _contactStatus = MutableLiveData<Event<Resource<Contact>>>()
    val contactStatus: LiveData<Event<Resource<Contact>>> = _contactStatus

    fun addOrUpdate(contact: Contact) {
        runBlocking {
            if ((checkFieldsNullability(contact)) && (checkFieldsSize(contact))) {
                if (isUpdate(contact.id) == true) {
                    _contactStatus.postValue(Event(Resource.success("Contact updated", contact)))
                    repository.update(contact)
                } else {
                    _contactStatus.postValue(Event(Resource.success("Contact registered", contact)))
                    repository.insert(contact)
                }
            }
        }
    }

    private fun isUpdate(id: Int): Boolean? {
        var isUpdate: Boolean? = null
        try {
            val job = runBlocking {
                repository.searchById(id)
            }
            val result: Contact? = job
            if (result != null) isUpdate = true
            if (result == null) isUpdate = false
        } catch (e: Exception) {
        } finally {
            return isUpdate
        }
    }

    private fun checkFieldsNullability(contact: Contact): Boolean {
        if (contact.name.isEmpty()) {
            _contactStatus.postValue(Event(Resource.error("Name is required", null)))
            return false
        } else if ((contact.email.isEmpty()) && (contact.phoneNumber.isEmpty())) {
            _contactStatus.postValue(
                Event(Resource.error("Please add email or phone number", null))
            )
            return false
        }
        return true
    }

    private fun checkFieldsSize(contact: Contact): Boolean {
        when {
            contact.name.length > 40 -> {
                _contactStatus.postValue(
                    Event(
                        Resource.error(
                            "Name limited to 40 characters",
                            null
                        )
                    )
                )
                return false
            }
            contact.email.length > 40 -> {
                _contactStatus.postValue(
                    Event(Resource.error("Email address limited to 40 characters", null))
                )
                return false
            }
            contact.phoneNumber.length > 20 -> {
                _contactStatus.postValue(
                    Event(
                        Resource.error(
                            "Number limited to 20 characters",
                            null
                        )
                    )
                )
                return false
            }
            else -> return true
        }
    }
}
