package br.com.fausto.contactslist.repository

import androidx.lifecycle.LiveData
import br.com.fausto.contactslist.database.Contact
import br.com.fausto.contactslist.database.ContactDAO
import javax.inject.Inject

class ContactRepository @Inject constructor(
    private val dao: ContactDAO
) : IContactRepository {

    override suspend fun searchById(id: Int) = dao.searchById(id)

    override fun getAllContacts(): LiveData<List<Contact>> = dao.getAllContacts()

    override suspend fun insert(contact: Contact) = dao.insertContact(contact)

    override suspend fun update(contact: Contact) = dao.updateContact(contact)

    override suspend fun delete(contact: Contact) = dao.deleteContact(contact)

    override suspend fun deleteAll() = dao.deleteAllContacts()
}