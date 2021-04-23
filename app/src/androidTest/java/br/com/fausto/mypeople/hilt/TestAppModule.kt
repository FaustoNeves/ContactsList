package br.com.fausto.mypeople.hilt

import android.content.Context
import androidx.room.Room
import br.com.fausto.mypeople.database.ContactsListDatabase
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
    @Named("test_db")
    fun provideInMemoryDb(@ApplicationContext appContext: Context) =
        Room.inMemoryDatabaseBuilder(appContext, ContactsListDatabase::class.java)
            .allowMainThreadQueries().build()
}