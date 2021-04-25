package br.com.fausto.mypeople.ui.viewmodel

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import br.com.fausto.mypeople.database.Subscriber
import br.com.fausto.mypeople.repository.ISubscriberRepository
import br.com.fausto.mypeople.utils.Event
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.Job
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class HomeVM @Inject constructor(
    private val repository: ISubscriberRepository
) : ViewModel() {

    val subscribers = repository.getAllSubscribers()
    private val statusMessage = MutableLiveData<Event<String>>()
    val message: LiveData<Event<String>>
        get() = statusMessage

    fun delete(subscriber: Subscriber): Job =
        viewModelScope.launch {
            repository.delete(subscriber)
            statusMessage.value = Event("Successfully deleted")
        }
}