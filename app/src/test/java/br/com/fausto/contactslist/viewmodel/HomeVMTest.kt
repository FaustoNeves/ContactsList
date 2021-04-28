package br.com.fausto.contactslist.viewmodel

import androidx.arch.core.executor.testing.InstantTaskExecutorRule
import br.com.fausto.contactslist.MainCoroutineRule
import br.com.fausto.contactslist.database.Contact
import br.com.fausto.contactslist.getOrAwaitValueTest
import br.com.fausto.contactslist.repository.FakeContactRepository
import br.com.fausto.contactslist.ui.viewmodel.HomeVM
import br.com.fausto.contactslist.utils.Status
import com.google.common.truth.Truth
import kotlinx.coroutines.ExperimentalCoroutinesApi
import org.junit.Before
import org.junit.Rule
import org.junit.Test

@ExperimentalCoroutinesApi
class HomeVMTest {
    private lateinit var viewModel: HomeVM

    @get:Rule
    var instantTaskExecutorRule = InstantTaskExecutorRule()

    @get:Rule
    var mainCoroutineRule = MainCoroutineRule()

    @Before
    fun setup() {
        viewModel = HomeVM(FakeContactRepository())
    }

    @Test
    fun `delete contact`() {
        val contact = Contact(0, "Belmonte", "belmonte@yahoo.com", "997777")
        viewModel.delete(contact)
        val value = viewModel.contactStatus.getOrAwaitValueTest()
        Truth.assertThat(value.getContentIfNotHandled()?.status).isEqualTo(Status.SUCCESS)
    }
}