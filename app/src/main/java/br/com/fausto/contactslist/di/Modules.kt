package br.com.fausto.contactslist.di

import android.content.Context
import androidx.room.Room
import br.com.fausto.contactslist.database.ContactsListDatabase
import br.com.fausto.contactslist.database.ContactDAO
import br.com.fausto.contactslist.repository.IContactRepository
import br.com.fausto.contactslist.repository.ContactRepository
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
    fun provideContactDAO(contactsListDatabase: ContactsListDatabase): ContactDAO =
        contactsListDatabase.ContactDAO()

    @Provides
    @Singleton
    fun provideAppDatabase(@ApplicationContext appContext: Context): ContactsListDatabase =
        Room.databaseBuilder(appContext, ContactsListDatabase::class.java, "module_database")
            .fallbackToDestructiveMigration().build()

    @Singleton
    @Provides
    fun provideContactRepository(contactDAO: ContactDAO): IContactRepository =
        ContactRepository(contactDAO)
}