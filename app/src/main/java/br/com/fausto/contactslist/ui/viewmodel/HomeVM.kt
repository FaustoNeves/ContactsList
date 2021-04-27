package br.com.fausto.contactslist.ui.viewmodel

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import br.com.fausto.contactslist.database.Contact
import br.com.fausto.contactslist.repository.IContactRepository
import br.com.fausto.contactslist.utils.Event
import br.com.fausto.contactslist.utils.Resource
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.Job
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class HomeVM @Inject constructor(
    private val repository: IContactRepository
) : ViewModel() {

    val contacts = repository.getAllContacts()
    private val _contactStatus = MutableLiveData<Event<Resource<Contact>>>()
    val contactStatus: LiveData<Event<Resource<Contact>>> = _contactStatus

    fun delete(contact: Contact): Job =
        viewModelScope.launch {
            repository.delete(contact)
            _contactStatus.value = Event(Resource.success("Successfully deleted", contact))
        }
}