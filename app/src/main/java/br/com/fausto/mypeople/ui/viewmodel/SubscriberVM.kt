package br.com.fausto.mypeople.ui.viewmodel

import androidx.databinding.Observable
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import br.com.fausto.mypeople.database.subscriber.Subscriber
import br.com.fausto.mypeople.repository.subscriber.RSubscriber
import br.com.fausto.mypeople.ui.utils.Event
import kotlinx.coroutines.Job
import kotlinx.coroutines.launch

class SubscriberVM(private val repository: RSubscriber) : ViewModel(), Observable {

    val subscribers = repository.subscribers
    private var isUpdateOrDelete = false
    private lateinit var subscriberToUpdateOrDelete: Subscriber
    var nameEdit = MutableLiveData<String>()
    var subscriberUpdated: Subscriber? = null

    private val statusMessage = MutableLiveData<Event<String>>()

    val message: LiveData<Event<String>>
        get() = statusMessage

//    fun saveOrUpdate(name: String, email: String, phoneNumber: String) {
//        if (name.isBlank()) {
//            statusMessage.value = Event("Don't forget your name")
//        } else {
//            if (isUpdateOrDelete) {
//                subscriberUpdated = subscribers.value!!.find { it.name.equals(nameEdit) }
//                subscriberUpdated!!.name = name
//                subscriberUpdated!!.email = email
//                update(subscriberUpdated!!)
//            } else {
//                insert(Subscriber(0, name, email, phoneNumber))
//            }
//        }
//    }

    fun setupUpdate(subscriber: Subscriber) {
        //subscriberEdit = subscribers.value!!.find { it.name.equals(nameEdit) }
        subscriberUpdated = subscriber
        isUpdateOrDelete = true
        nameEdit.value = subscriber.name
    }

//    fun clearAllOrDelete() {
//        if (isUpdateOrDelete) {
//            delete(subscriberToUpdateOrDelete)
//        } else {
//            subscribers.value!!.find { it.name == "oi" }
//            clearAll()
//        }
//    }
//
//    fun insert(subscriber: Subscriber): Job =
//        viewModelScope.launch {
//            repository.insert(subscriber)
//            statusMessage.value = Event("Successfully registered")
//        }
//
//    fun update(subscriber: Subscriber): Job =
//        viewModelScope.launch {
//            repository.update(subscriber)
//            isUpdateOrDelete = false
//            statusMessage.value = Event("Successfully updated")
//        }

    fun delete(subscriber: Subscriber): Job =
        viewModelScope.launch {
            repository.delete(subscriber)
            isUpdateOrDelete = false
            statusMessage.value = Event("Successfully deleted")
        }

//    fun clearAll(): Job =
//        viewModelScope.launch {
//            repository.deleteAll()
//            statusMessage.value = Event("deleted everything")
//        }

    override fun addOnPropertyChangedCallback(callback: Observable.OnPropertyChangedCallback?) {

    }

    override fun removeOnPropertyChangedCallback(callback: Observable.OnPropertyChangedCallback?) {

    }
}