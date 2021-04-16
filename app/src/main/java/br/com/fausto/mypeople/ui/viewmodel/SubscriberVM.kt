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

    private val statusMessage = MutableLiveData<Event<String>>()

    val message: LiveData<Event<String>>
        get() = statusMessage

    fun delete(subscriber: Subscriber): Job =
        viewModelScope.launch {
            repository.delete(subscriber)
            isUpdateOrDelete = false
            statusMessage.value = Event("Successfully deleted")
        }

    override fun addOnPropertyChangedCallback(callback: Observable.OnPropertyChangedCallback?) {

    }

    override fun removeOnPropertyChangedCallback(callback: Observable.OnPropertyChangedCallback?) {

    }
}