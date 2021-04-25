package br.com.fausto.mypeople.repository

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import br.com.fausto.mypeople.database.Subscriber

class FakeSubscriberRepository:ISubscriberRepository {

    private val subscribers = mutableListOf<Subscriber>()
    private val observableSubscribers = MutableLiveData<List<Subscriber>>(subscribers)

    fun refreshList() = observableSubscribers.postValue(subscribers)

    override fun getAllSubscribers(): LiveData<List<Subscriber>> = observableSubscribers

    override suspend fun searchById(id: Int): Subscriber? {
        var subscriberToFind: Subscriber? = null
        observableSubscribers.value?.forEach {
            if (it.id == id)
                subscriberToFind = it
        }
        return subscriberToFind
    }

    override suspend fun insert(subscriber: Subscriber) {
        subscribers.add(subscriber)
        refreshList()
    }

    override suspend fun update(subscriber: Subscriber) {
        subscribers
    }

    override suspend fun delete(subscriber: Subscriber) {
        subscribers.remove(subscriber)
        refreshList()
    }

    override suspend fun deleteAll() {
        subscribers.removeAll(subscribers)
    }

}