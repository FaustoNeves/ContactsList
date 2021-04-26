package br.com.fausto.mypeople.viewmodel

import androidx.arch.core.executor.testing.InstantTaskExecutorRule
import br.com.fausto.mypeople.MainCoroutineRule
import br.com.fausto.mypeople.database.Subscriber
import br.com.fausto.mypeople.getOrAwaitValueTest
import br.com.fausto.mypeople.repository.FakeSubscriberRepository
import br.com.fausto.mypeople.ui.viewmodel.RegisterVM
import br.com.fausto.mypeople.utils.Status
import com.google.common.truth.Truth.assertThat
import kotlinx.coroutines.ExperimentalCoroutinesApi
import org.junit.Before
import org.junit.Rule
import org.junit.Test

@ExperimentalCoroutinesApi
class RegisterVMTest {
    private lateinit var viewModel: RegisterVM

    @get:Rule
    var instantTaskExecutorRule = InstantTaskExecutorRule()

    @get:Rule
    var mainCoroutineRule = MainCoroutineRule()

    @Before
    fun setup() {
        viewModel = RegisterVM(FakeSubscriberRepository())
    }

    @Test
    fun `add subscriber with empty name`() {
        val subscriber = Subscriber(30, "", "email@gmail.com", "123456")
        viewModel.add(subscriber)
        val value = viewModel.subscriberStatus.getOrAwaitValueTest()
        assertThat(value.getContentIfNotHandled()?.status).isEqualTo(Status.ERROR)
    }

    @Test
    fun `add subscriber with empty email and phone number`() {
        val subscriber = Subscriber(30, "Poporing", "", "")
        viewModel.add(subscriber)
        val value = viewModel.subscriberStatus.getOrAwaitValueTest()
        assertThat(value.getContentIfNotHandled()?.status).isEqualTo(Status.ERROR)
    }

    @Test
    fun `add subscriber with valid inputs`() {
        val subscriber = Subscriber(30, "Deviruchi", "deviruchi@gmail.com", "123456")
        viewModel.add(subscriber)
        val value = viewModel.subscriberStatus.getOrAwaitValueTest()
        assertThat(value.getContentIfNotHandled()?.status).isEqualTo(Status.SUCCESS)
    }
}