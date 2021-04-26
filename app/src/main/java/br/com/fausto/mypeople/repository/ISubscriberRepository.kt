package br.com.fausto.mypeople.repository

import androidx.lifecycle.LiveData
import br.com.fausto.mypeople.database.Subscriber

interface ISubscriberRepository {
    fun getAllSubscribers(): LiveData<List<Subscriber>>
    suspend fun searchById(id: Int): Subscriber?
    suspend fun insert(subscriber: Subscriber)
    suspend fun update(subscriber: Subscriber)
    suspend fun delete(subscriber: Subscriber)
    suspend fun deleteAll()
}