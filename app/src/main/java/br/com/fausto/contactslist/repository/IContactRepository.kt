package br.com.fausto.contactslist.repository

import androidx.lifecycle.LiveData
import br.com.fausto.contactslist.database.Contact

interface IContactRepository {
    fun getAllContacts(): LiveData<List<Contact>>
    suspend fun searchById(id: Int): Contact?
    suspend fun insert(contact: Contact)
    suspend fun update(contact: Contact)
    suspend fun delete(contact: Contact)
    suspend fun deleteAll()
}