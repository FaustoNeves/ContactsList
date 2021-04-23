package br.com.fausto.mypeople.di

import android.content.Context
import br.com.fausto.mypeople.database.SubscriberDAO
import br.com.fausto.mypeople.database.ContactsListDatabase
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.android.qualifiers.ApplicationContext
import dagger.hilt.components.SingletonComponent
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
class DataModule {
    @Singleton
    @Provides
    fun provideSubscriberDAO(contactsListDatabase: ContactsListDatabase): SubscriberDAO {
        return contactsListDatabase.subscriberDAO
    }

    @Module
    @InstallIn(SingletonComponent::class)
    object AppDatabase {
        @Provides
        @Singleton
        fun provideAppDatabase(@ApplicationContext appContext: Context): ContactsListDatabase {
            return ContactsListDatabase.getInstance(appContext)
//            return Room.databaseBuilder(
//                appContext,
//                SubscriberDatabase::class.java,
//                "subscriber_database"
//            ).build()
        }
    }
}