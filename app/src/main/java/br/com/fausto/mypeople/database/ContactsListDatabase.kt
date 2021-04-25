package br.com.fausto.mypeople.database

import androidx.room.Database
import androidx.room.RoomDatabase

@Database(entities = [Subscriber::class], version = 5, exportSchema = false)
abstract class ContactsListDatabase : RoomDatabase() {
    abstract fun subscriberDAO(): SubscriberDAO
}