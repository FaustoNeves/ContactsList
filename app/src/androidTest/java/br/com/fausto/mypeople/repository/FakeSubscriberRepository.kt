package br.com.fausto.mypeople.repository

import androidx.lifecycle.LiveData
import br.com.fausto.mypeople.database.Subscriber

class FakeSubscriberRepository:ISubscriberRepository {
    override fun getAllSubscribers(): LiveData<List<Subscriber>> {
        TODO("Not yet implemented")
    }

    override suspend fun searchById(int: Int): Subscriber {
        TODO("Not yet implemented")
    }

    override suspend fun insert(subscriber: Subscriber) {
        TODO("Not yet implemented")
    }

    override suspend fun update(subscriber: Subscriber) {
        TODO("Not yet implemented")
    }

    override suspend fun delete(subscriber: Subscriber) {
        TODO("Not yet implemented")
    }

    override suspend fun deleteAll() {
        TODO("Not yet implemented")
    }

}