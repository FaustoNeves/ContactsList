package br.com.fausto.contactslist.viewmodel

import androidx.arch.core.executor.testing.InstantTaskExecutorRule
import br.com.fausto.contactslist.MainCoroutineRule
import br.com.fausto.contactslist.database.Contact
import br.com.fausto.contactslist.getOrAwaitValueTest
import br.com.fausto.contactslist.repository.FakeContactRepository
import br.com.fausto.contactslist.ui.viewmodel.RegisterVM
import br.com.fausto.contactslist.utils.Status
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
        viewModel = RegisterVM(FakeContactRepository())
    }

    @Test
    fun `add contact with empty name`() {
        val contact = Contact(30, "", "email@gmail.com", "123456")
        viewModel.addOrUpdate(contact)
        val value = viewModel.contactStatus.getOrAwaitValueTest()
        assertThat(value.getContentIfNotHandled()?.status).isEqualTo(Status.ERROR)
    }

    @Test
    fun `add contact with empty email and phone number`() {
        val contact = Contact(30, "Poporing", "", "")
        viewModel.addOrUpdate(contact)
        val value = viewModel.contactStatus.getOrAwaitValueTest()
        assertThat(value.getContentIfNotHandled()?.status).isEqualTo(Status.ERROR)
    }

    @Test
    fun `add contact with valid inputs`() {
        val contact = Contact(30, "Deviruchi", "deviruchi@gmail.com", "123456")
        viewModel.addOrUpdate(contact)
        val value = viewModel.contactStatus.getOrAwaitValueTest()
        assertThat(value.getContentIfNotHandled()?.status).isEqualTo(Status.SUCCESS)
    }

    @Test
    fun `update contact with valid inputs`() {
        val contact = Contact(30, "Metaller", "metaller@gmail.com", "123456")
        viewModel.addOrUpdate(contact)
        contact.name = "Garm"
        contact.email = "Garm@hotmail.com"
        contact.phoneNumber = "999"
        viewModel.addOrUpdate(contact)
        val value = viewModel.contactStatus.getOrAwaitValueTest()
        assertThat(value.getContentIfNotHandled()?.message).isEqualTo("Contact updated")
    }

    @Test
    fun `update contact with invalid inputs`() {
        val contact = Contact(30, "Metaller", "metaller@gmail.com", "123456")
        viewModel.addOrUpdate(contact)
        contact.name = "Garm"
        contact.email = ""
        contact.phoneNumber = ""
        viewModel.addOrUpdate(contact)
        val value = viewModel.contactStatus.getOrAwaitValueTest()
        assertThat(value.getContentIfNotHandled()?.status).isEqualTo(Status.ERROR)
    }
}