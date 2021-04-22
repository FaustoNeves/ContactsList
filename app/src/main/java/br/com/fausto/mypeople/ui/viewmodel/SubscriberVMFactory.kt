package br.com.fausto.mypeople.ui.viewmodel

import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import br.com.fausto.mypeople.repository.SubscriberRepository

class SubscriberVMFactory(private val repository: SubscriberRepository) : ViewModelProvider.Factory {
    override fun <T : ViewModel?> create(modelClass: Class<T>): T {
        if (modelClass.isAssignableFrom(SubscriberVM::class.java)) {
            return SubscriberVM(repository) as T
        }
        throw IllegalArgumentException("Unknown ViewModel Class")
    }
}