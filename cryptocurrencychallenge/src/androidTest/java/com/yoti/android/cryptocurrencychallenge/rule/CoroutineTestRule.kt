package com.yoti.android.cryptocurrencychallenge.rule
import com.yoti.android.cryptocurrencychallenge.crosscuting.coroutine.DispatcherProvider
import kotlinx.coroutines.CoroutineDispatcher
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.test.TestCoroutineDispatcher
import kotlinx.coroutines.test.resetMain
import kotlinx.coroutines.test.setMain

import org.junit.rules.TestWatcher
import org.junit.runner.Description

@ExperimentalCoroutinesApi
class CoroutineTestRule(
    val testDispatcher: TestCoroutineDispatcher = TestCoroutineDispatcher(),
) : TestWatcher() {

    val dispatcherProvider = object : DispatcherProvider {

        override fun main(): CoroutineDispatcher = testDispatcher

        override fun io(): CoroutineDispatcher = testDispatcher

        override fun default(): CoroutineDispatcher = testDispatcher

        override fun unconfined(): CoroutineDispatcher = testDispatcher

    }

    override fun starting(description: Description?) {
        super.starting(description)
        Dispatchers.setMain(testDispatcher)
    }

    override fun finished(description: Description?) {
        super.finished(description)
        Dispatchers.resetMain()
        testDispatcher.cleanupTestCoroutines()
    }
}

