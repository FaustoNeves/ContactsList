package br.com.fausto.mypeople.ui.viewmodel

import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import br.com.fausto.mypeople.repository.SubscriberRepository

class ubscriberVMFactory(private val repository: SubscriberRepository) : ViewModelProvider.Factory {
    override fun <T : ViewModel?> create(modelClass: Class<T>): T {
        if (modelClass.isAssignableFrom(HomeVM::class.java)) {
            return HomeVM(repository) as T
        }
        throw IllegalArgumentException("Unknown ViewModel Class")
    }
}