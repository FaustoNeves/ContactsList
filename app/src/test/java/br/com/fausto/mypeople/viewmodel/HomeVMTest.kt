package br.com.fausto.mypeople.viewmodel

import br.com.fausto.mypeople.repository.FakeSubscriberRepository
import br.com.fausto.mypeople.ui.viewmodel.HomeVM
import org.junit.Before

class HomeVMTest {
    private lateinit var viewModel: HomeVM

    @Before
    fun setup() {
        viewModel = HomeVM(FakeSubscriberRepository())
    }
}