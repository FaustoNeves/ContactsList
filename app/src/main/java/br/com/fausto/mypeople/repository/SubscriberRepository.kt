package br.com.fausto.mypeople.repository

import br.com.fausto.mypeople.database.Subscriber
import br.com.fausto.mypeople.database.SubscriberDAO
import javax.inject.Inject

class SubscriberRepository @Inject constructor(private val dao: SubscriberDAO) {

    val subscribers = dao.getAllSubscribers()

    suspend fun searchById(id: Int) = dao.searchById(id)

    suspend fun insert(subscriber: Subscriber) = dao.insertSubscriber(subscriber)

    suspend fun update(subscriber: Subscriber) = dao.updateSubscriber(subscriber)

    suspend fun delete(subscriber: Subscriber) = dao.deleteSubscriber(subscriber)

    suspend fun deleteAll() = dao.deleteAllSubscribers()
}