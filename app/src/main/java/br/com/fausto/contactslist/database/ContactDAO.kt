package br.com.fausto.contactslist.database

import androidx.lifecycle.LiveData
import androidx.room.*

@Dao
interface ContactDAO {
    @Insert
    suspend fun insertContact(contact: Contact)

    @Update
    suspend fun updateContact(contact: Contact)

    @Delete
    suspend fun deleteContact(contact: Contact)

    @Query("SELECT * FROM contact_data_table where contact_id = :id")
    suspend fun searchById(id: Int): Contact

    @Query("DELETE FROM contact_data_table")
    suspend fun deleteAllContacts()

    @Query("SELECT * FROM contact_data_table")
    fun getAllContacts(): LiveData<List<Contact>>
}