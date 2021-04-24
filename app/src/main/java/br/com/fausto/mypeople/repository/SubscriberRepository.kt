package br.com.fausto.mypeople.repository

import androidx.lifecycle.LiveData
import br.com.fausto.mypeople.database.Subscriber
import br.com.fausto.mypeople.database.SubscriberDAO
import javax.inject.Inject

class SubscriberRepository @Inject constructor(private val dao: SubscriberDAO) :
    ISubscriberRepository {

    override suspend fun searchById(int: Int) = dao.searchById(int)

    override fun getAllSubscribers(): LiveData<List<Subscriber>> = dao.getAllSubscribers()

    override suspend fun insert(subscriber: Subscriber) = dao.insertSubscriber(subscriber)

    override suspend fun update(subscriber: Subscriber) = dao.updateSubscriber(subscriber)

    override suspend fun delete(subscriber: Subscriber) = dao.deleteSubscriber(subscriber)

    override suspend fun deleteAll() = dao.deleteAllSubscribers()
}