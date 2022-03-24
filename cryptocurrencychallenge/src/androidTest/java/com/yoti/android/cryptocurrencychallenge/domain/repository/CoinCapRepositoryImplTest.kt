package com.yoti.android.cryptocurrencychallenge.domain.repository

import android.util.Log
import androidx.test.ext.junit.runners.AndroidJUnit4
import com.yoti.android.cryptocurrencychallenge.ExpectedValueProvider
import com.yoti.android.cryptocurrencychallenge.model.network.remote.ApiResponse
import com.yoti.android.cryptocurrencychallenge.model.network.remote.ResponseStructure
import com.yoti.android.cryptocurrencychallenge.rule.ApiMockServerRule
import com.yoti.android.cryptocurrencychallenge.rule.CoroutineTestRule
import com.yoti.android.cryptocurrencychallenge.rule.DatabaseTestingRule
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.coroutineScope
import kotlinx.coroutines.flow.collectLatest
import kotlinx.coroutines.flow.first
import kotlinx.coroutines.flow.single
import kotlinx.coroutines.runBlocking
import okhttp3.Response
import org.junit.Assert.*
import org.junit.Before
import org.junit.Rule
import org.junit.Test
import org.junit.runner.RunWith
import org.junit.runners.JUnit4
import javax.net.ssl.HttpsURLConnection

@ExperimentalCoroutinesApi
class CoinCapRepositoryTest {

    private val coins = ExpectedValueProvider.coins

    @get:Rule
    val apiMockServer = ApiMockServerRule()

    @get:Rule
    val coroutineTestRule = CoroutineTestRule()

    @get:Rule
    val databaseTestingRule = DatabaseTestingRule()

    private lateinit var coinRepo: CoinCapRepository

    @Before
    fun setUp() {
        this.coinRepo = CoinCapRepository.createInstance(
            coroutineTestRule.dispatcherProvider,
            apiMockServer.createMockApi(),
            databaseTestingRule.getCoinDao()
        )
    }

    @Test
    fun getAssests() = runBlocking {
        apiMockServer.enqueueMockResponse {
            this.setResponseCode(HttpsURLConnection.HTTP_OK)
            this.setBody(
                apiMockServer.createGsonResponse(
                    ResponseStructure(
                        data = coins,
                        timeStamp = 12357526
                    )
                )
            )
            val data = coinRepo.getAssets()
            runBlocking {
                data.collectLatest { it ->
                    assertEquals("bitcoin" , it.assetData!![0].id)
                }
            }
        }
    }
}
