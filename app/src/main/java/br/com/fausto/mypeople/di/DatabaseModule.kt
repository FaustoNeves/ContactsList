package br.com.fausto.mypeople.di

import android.content.Context
import androidx.room.Room
import br.com.fausto.mypeople.database.ContactsListDatabase
import br.com.fausto.mypeople.database.SubscriberDAO
import br.com.fausto.mypeople.repository.ISubscriberRepository
import br.com.fausto.mypeople.repository.SubscriberRepository
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.android.qualifiers.ApplicationContext
import dagger.hilt.components.SingletonComponent
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
object DataModule {
    @Singleton
    @Provides
    fun provideSubscriberDAO(contactsListDatabase: ContactsListDatabase): SubscriberDAO =
        contactsListDatabase.subscriberDAO()

    @Provides
    @Singleton
    fun provideAppDatabase(@ApplicationContext appContext: Context): ContactsListDatabase =
        Room.databaseBuilder(appContext, ContactsListDatabase::class.java, "module_database")
            .fallbackToDestructiveMigration().build()

    @Singleton
    @Provides
    fun bindsSubscriberRepository(subscriberDAO: SubscriberDAO): ISubscriberRepository =
        SubscriberRepository(subscriberDAO)
}