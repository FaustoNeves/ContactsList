package br.com.fausto.contactslist.repository

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import br.com.fausto.contactslist.database.Contact

class FakeContactRepositoryAndroidTest: IContactRepository {

    private val contacts = mutableListOf<Contact>()
    private val observableContacts = MutableLiveData<List<Contact>>(contacts)

    fun refreshList() = observableContacts.postValue(contacts)

    override fun getAllContacts(): LiveData<List<Contact>> = observableContacts

    override suspend fun searchById(id: Int): Contact? {
        var contactToFind: Contact? = null
        observableContacts.value?.forEach {
            if (it.id == id)
                contactToFind = it
        }
        return contactToFind
    }

    override suspend fun insert(contact: Contact) {
        contacts.add(contact)
        refreshList()
    }

    override suspend fun update(contact: Contact) {
        contacts
    }

    override suspend fun delete(contact: Contact) {
        contacts.remove(contact)
        refreshList()
    }

    override suspend fun deleteAll() {
        contacts.removeAll(contacts)
    }

}