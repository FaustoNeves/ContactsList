package br.com.fausto.mypeople.database

import android.content.Context
import androidx.room.Database
import androidx.room.Room
import androidx.room.RoomDatabase

@Database(entities = [Subscriber::class], version = 4, exportSchema = false)
abstract class ContactsListDatabase : RoomDatabase() {

    abstract val subscriberDAO: SubscriberDAO

    companion object {
        @Volatile
        private var INSTANCE: ContactsListDatabase? = null
        fun getInstance(context: Context): ContactsListDatabase {
            //synchronized means that will have only 1 instance at time
            //others threads can't instante this but the current thread
            synchronized(this) {
                var instance: ContactsListDatabase? = INSTANCE
                if (instance == null) {
                    instance = Room.databaseBuilder(
                        context.applicationContext,
                        ContactsListDatabase::class.java,
                        "subscriber_data_base"
                    )
                        .fallbackToDestructiveMigration()
                        .build()
                }
                return instance
            }
        }
    }
}