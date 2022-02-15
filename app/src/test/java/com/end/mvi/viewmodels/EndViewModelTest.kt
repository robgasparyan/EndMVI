package com.end.mvi.viewmodels

import app.cash.turbine.test
import com.end.mvi.models.ClothesShoesModel
import com.end.mvi.repos.EndRepository
import com.end.mvi.rules.CoroutineTestRule
import com.end.mvi.rules.runBlockingTest
import com.end.mvi.utils.EndUIState
import com.end.mvi.utils.SuccessState
import io.mockk.MockKAnnotations
import io.mockk.coEvery
import io.mockk.mockk
import kotlinx.coroutines.flow.flow
import org.junit.Before
import org.junit.Rule
import org.junit.Test
import kotlin.time.ExperimentalTime


class EndViewModelTest {

    @get:Rule
    val coroutinesTestRule = CoroutineTestRule()

    @Before
    fun setUp() {
        MockKAnnotations.init(this)
    }

    @OptIn(ExperimentalTime::class)
    @Test
    fun `check getting clothes and shoes from BE`() = coroutinesTestRule.runBlockingTest {
        val dummyClothesShoesModel = ClothesShoesModel(
            product_count = 2,
            products = arrayListOf(ClothesShoesModel.Product("1", "dummyLink", "Test", "$100")),
            title = "dummy"
        )
        val endRepository: EndRepository = mockk(relaxed = true)
        coEvery { endRepository.getClothesAndShoes() } returns flow {
            emit(SuccessState(dummyClothesShoesModel))
        }
        val viewModel = EndViewModel(endRepository)
        viewModel.container.state.test {
            val item = awaitItem()
            if (item is EndUIState.Data) {
                assert(item.data.products.isNotEmpty())
            }
        }
    }

    @OptIn(ExperimentalTime::class)
    @Test
    fun `check click functional for `() = coroutinesTestRule.runBlockingTest {
        val dummyClothesShoesModel = ClothesShoesModel(
            product_count = 2,
            products = arrayListOf(ClothesShoesModel.Product("1", "dummyLink", "Test", "$100")),
            title = "dummy"
        )
        val endRepository: EndRepository = mockk(relaxed = true)
        coEvery { endRepository.getClothesAndShoes() } returns flow {
            emit(SuccessState(dummyClothesShoesModel))
        }
        val viewModel = EndViewModel(endRepository)
        viewModel.container.state.test {
            val item = awaitItem()
            if (item is EndUIState.Data) {
                assert(item.data.products.isNotEmpty())
            }
        }
    }
}