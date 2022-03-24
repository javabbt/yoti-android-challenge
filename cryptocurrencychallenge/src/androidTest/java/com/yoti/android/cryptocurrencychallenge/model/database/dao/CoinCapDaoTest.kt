package com.yoti.android.cryptocurrencychallenge.model.database.dao

import com.yoti.android.cryptocurrencychallenge.ExpectedValueProvider
import com.yoti.android.cryptocurrencychallenge.rule.DatabaseTestingRule
import kotlinx.coroutines.flow.single
import kotlinx.coroutines.flow.take
import kotlinx.coroutines.runBlocking
import org.junit.Assert.*
import org.junit.Before
import org.junit.Rule
import org.junit.Test

class CoinCapDaoTest{

    @get:Rule
    val databaseTestingRule = DatabaseTestingRule()

    private lateinit var coinDao: CoinCapDao

    private val coins = ExpectedValueProvider.coinsEntity

    @Before
    fun setUp() {
        coinDao = databaseTestingRule.getCoinDao()
    }

    @Test
    fun insertCoins() = runBlocking {
        coinDao.insertCoins(coins)
        val retrieved = coinDao.getCoinsFromLocal().take(1).single()
        assertEquals(retrieved[0].id, "bitcoin")
    }

}