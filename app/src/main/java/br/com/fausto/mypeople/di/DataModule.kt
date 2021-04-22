package br.com.fausto.mypeople.di

import android.content.Context
import androidx.room.Room
import br.com.fausto.mypeople.database.SubscriberDAO
import br.com.fausto.mypeople.database.SubscriberDatabase
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
    fun provideSubscriberDAO(subscriberDatabase: SubscriberDatabase): SubscriberDAO {
        return subscriberDatabase.subscriberDAO
    }

    @Module
    @InstallIn(SingletonComponent::class)
    object AppDatabase {
        @Provides
        @Singleton
        fun provideAppDatabase(@ApplicationContext appContext: Context): SubscriberDatabase {
            return Room.databaseBuilder(
                appContext,
                SubscriberDatabase::class.java,
                "subscriber_database"
            ).build()
        }
    }
}