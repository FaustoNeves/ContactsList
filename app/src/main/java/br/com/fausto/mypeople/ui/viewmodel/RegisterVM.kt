package br.com.fausto.mypeople.ui.viewmodel

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import br.com.fausto.mypeople.database.Subscriber
import br.com.fausto.mypeople.repository.SubscriberRepository
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.Job
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class RegisterVM @Inject constructor(private val repository: SubscriberRepository) : ViewModel() {

    val subscribers = repository.subscribers
    private val statusMessage = MutableLiveData<String>()
    val message: LiveData<String>
        get() = statusMessage

    fun add(subscriber: Subscriber): Job =
        viewModelScope.launch {
            repository.insert(subscriber)
            statusMessage.value = "Contact registered"
        }

    fun update(subscriber: Subscriber): Job =
        viewModelScope.launch {
            repository.update(subscriber)
            statusMessage.value = "Contact updated"
        }
}