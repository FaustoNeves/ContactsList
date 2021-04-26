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

    private val _subscriberStatus = MutableLiveData<Event<Resource<Subscriber>>>()
    val subscriberStatus: LiveData<Event<Resource<Subscriber>>> = _subscriberStatus

    fun add(subscriber: Subscriber): Job =
        viewModelScope.launch {
            if (subscriber.name.isEmpty()) {
                _subscriberStatus.value = Event(Resource.error("Name is required", null))
            } else if ((subscriber.email.isEmpty()) && (subscriber.phoneNumber.isEmpty())) {
                _subscriberStatus.value =
                    Event(Resource.error("Please add email or phone number", null))
            } else {
                repository.insert(subscriber)
                _subscriberStatus.value = Event(Resource.success("Contact registered", subscriber))
            }
        }

    fun update(subscriber: Subscriber): Job =
        viewModelScope.launch {
            if (subscriber.name.isEmpty()) {
                _subscriberStatus.value = Event(Resource.error("Name is required", null))
            } else if ((subscriber.email.isEmpty()) && (subscriber.phoneNumber.isEmpty())) {
                _subscriberStatus.value =
                    Event(Resource.error("Please add email or phone number", null))
            } else {
                repository.update(subscriber)
                _subscriberStatus.value = Event(Resource.success("Contact updated", subscriber))
            }
        }
}