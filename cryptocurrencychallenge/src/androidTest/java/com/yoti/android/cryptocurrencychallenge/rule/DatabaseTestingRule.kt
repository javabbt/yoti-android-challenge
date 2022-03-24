package com.yoti.android.cryptocurrencychallenge.rule
import androidx.room.Room
import androidx.test.platform.app.InstrumentationRegistry
import com.yoti.android.cryptocurrencychallenge.model.database.CoinCapDatabase
import org.junit.rules.TestWatcher
import org.junit.runner.Description

class DatabaseTestingRule : TestWatcher() {

    private val database = Room.inMemoryDatabaseBuilder(
        InstrumentationRegistry.getInstrumentation().context,
        CoinCapDatabase::class.java
    ).build()

    override fun finished(description: Description?) {
        database.clearAllTables()
        super.finished(description)
    }

    fun getCoinDao() = database.coinCapDao

}
