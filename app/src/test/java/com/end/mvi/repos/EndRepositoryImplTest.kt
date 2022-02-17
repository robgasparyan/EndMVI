package com.end.mvi.repos

import app.cash.turbine.test
import com.end.mvi.api.EndService
import com.end.mvi.mappers.EndMapper
import com.end.mvi.models.ClothesShoesModelDTO
import com.end.mvi.rules.CoroutineTestRule
import com.end.mvi.rules.runBlockingTest
import com.end.mvi.utils.ErrorState
import com.end.mvi.utils.SuccessState
import com.google.gson.JsonParseException
import io.mockk.MockKAnnotations
import io.mockk.coEvery
import io.mockk.impl.annotations.RelaxedMockK
import org.junit.Before
import org.junit.Rule
import org.junit.Test
import kotlin.time.ExperimentalTime

class EndRepositoryImplTest {

    @RelaxedMockK
    lateinit var endService: EndService

    private val endMapper = EndMapper()
    private lateinit var endRepositoryImpl: EndRepositoryImpl

    @get:Rule
    val coroutinesTestRule = CoroutineTestRule()

    @Before
    fun setUp() {
        MockKAnnotations.init(this)
        endRepositoryImpl = EndRepositoryImpl(endService, endMapper)
    }

    @OptIn(ExperimentalTime::class)
    @Test
    fun `check Source one success`() = coroutinesTestRule.runBlockingTest {
        coEvery { endService.getDataFromSource1() } returns ClothesShoesModelDTO(
            product_count = 1,
            products = emptyList(),
            title = "Dummy title"
        )
        endRepositoryImpl.getFromSource1().test {
            assert(awaitItem() is SuccessState)
            awaitComplete()
        }
    }

    @OptIn(ExperimentalTime::class)
    @Test
    fun `check Source one failure`() = coroutinesTestRule.runBlockingTest {
        coEvery { endService.getDataFromSource1() } throws JsonParseException("Fake Exception")
        endRepositoryImpl.getFromSource1().test {
            assert(awaitItem() is ErrorState)
            awaitComplete()
        }
    }

    @OptIn(ExperimentalTime::class)
    @Test
    fun `check Source two success`() = coroutinesTestRule.runBlockingTest {
        coEvery { endService.getDataFromSource2() } returns ClothesShoesModelDTO(
            product_count = 1,
            products = emptyList(),
            title = "Dummy title"
        )
        endRepositoryImpl.getFromSource1().test {
            assert(awaitItem() is SuccessState)
            awaitComplete()
        }
    }

    @OptIn(ExperimentalTime::class)
    @Test
    fun `check Source second failure`() = coroutinesTestRule.runBlockingTest {
        coEvery { endService.getDataFromSource2() } throws JsonParseException("Fake Exception")
        endRepositoryImpl.getFromSource2().test {
            assert(awaitItem() is ErrorState)
            awaitComplete()
        }
    }


    @OptIn(ExperimentalTime::class)
    @Test
    fun `check both sources`() = coroutinesTestRule.runBlockingTest {
        coEvery { endService.getDataFromSource1() } returns ClothesShoesModelDTO(
            product_count = 1,
            products = listOf(
                ClothesShoesModelDTO.Product(
                    id = "1",
                    image = "image",
                    name = "Test",
                    price = "$100"
                )
            ),
            title = "Dummy title"
        )
        coEvery { endService.getDataFromSource2() } returns ClothesShoesModelDTO(
            product_count = 1,
            products = listOf(
                ClothesShoesModelDTO.Product(
                    id = "1",
                    image = "image",
                    name = "Test",
                    price = "$100"
                )
            ),
            title = "Dummy title"
        )
        endRepositoryImpl.getClothesAndShoes().test {
            val item = awaitItem()
            assert(item is SuccessState)
            assert((item as SuccessState).data.products.size == 2)
            awaitComplete()
        }
    }


}