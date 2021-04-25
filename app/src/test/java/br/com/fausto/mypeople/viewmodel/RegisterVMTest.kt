package br.com.fausto.mypeople.viewmodel

import br.com.fausto.mypeople.repository.FakeSubscriberRepository
import br.com.fausto.mypeople.ui.viewmodel.RegisterVM
import org.junit.Before

class RegisterVMTest {
    private lateinit var viewModel: RegisterVM

    @Before
    fun setup() {
        viewModel = RegisterVM(FakeSubscriberRepository())
    }

//    @Test
//    fun `add subscriber with empty name`() {
//        val subscriber = Subscriber(30, "", "email@gmail.com", "123456")
//        viewModel.add(subscriber)
//        val value = viewModel.subscriberStatus.getOrAwaitValueTest()
//        assertThat(value.getContentIfNotHandled()?.status).isEqualTo(Status.ERROR)
//    }
}