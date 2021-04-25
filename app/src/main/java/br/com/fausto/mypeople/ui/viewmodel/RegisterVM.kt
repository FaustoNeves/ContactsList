package br.com.fausto.mypeople.ui.viewmodel

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import br.com.fausto.mypeople.database.Subscriber
import br.com.fausto.mypeople.repository.ISubscriberRepository
import br.com.fausto.mypeople.utils.Event
import br.com.fausto.mypeople.utils.Resource
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.Job
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class RegisterVM @Inject constructor(
    private val repository: ISubscriberRepository
) : ViewModel() {

    private val statusMessage = MutableLiveData<Event<Resource<Subscriber>>>()
    val message: LiveData<Event<Resource<Subscriber>>>
        get() = statusMessage

    fun add(subscriber: Subscriber): Job =
        viewModelScope.launch {
            if (subscriber.name.isEmpty()) {
                statusMessage.value = Event(Resource.error("Name is required", null))
            } else if ((subscriber.email.isEmpty()) && (subscriber.phoneNumber.isEmpty())) {
                statusMessage.value =
                    Event(Resource.error("Please add email or phone number", null))
            } else {
                repository.insert(subscriber)
                statusMessage.value = Event(Resource.success("Contact registered", subscriber))
            }
        }

    fun update(subscriber: Subscriber): Job =
        viewModelScope.launch {
            when {
                subscriber.name.isEmpty() -> {
                    statusMessage.value = Event(Resource.error("Name is required", null))
                }
                subscriber.email.isEmpty() and subscriber.phoneNumber.isEmpty() -> {
                    statusMessage.value =
                        Event(Resource.error("Please add email or phone number", null))
                }
                else -> {
                    repository.update(subscriber)
                    statusMessage.value = Event(Resource.success("Contact updated", subscriber))
                }
            }
        }
}