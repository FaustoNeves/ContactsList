package br.com.fausto.contactslist.database

import androidx.room.Database
import androidx.room.RoomDatabase

@Database(entities = [Contact::class], version = 6, exportSchema = false)
abstract class ContactsListDatabase : RoomDatabase() {
    abstract fun ContactDAO(): ContactDAO
}