package br.com.fausto.contactslist.di

import android.content.Context
import androidx.room.Room
import br.com.fausto.contactslist.database.ContactsListDatabase
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.android.qualifiers.ApplicationContext
import dagger.hilt.components.SingletonComponent
import javax.inject.Named

@Module
@InstallIn(SingletonComponent::class)
object TestAppModule {

    @Provides
    @Named("test_contact_database")
    fun provideInMemoryDb(@ApplicationContext appContext: Context) =
        Room.inMemoryDatabaseBuilder(appContext, ContactsListDatabase::class.java)
            .allowMainThreadQueries().build()
}