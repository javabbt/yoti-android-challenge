package com.yoti.android.cryptocurrencychallenge.di

import android.content.Context
import android.util.Log
import androidx.room.Room
import com.google.gson.Gson
import com.yoti.android.cryptocurrencychallenge.crosscuting.connectivity.ConnectivityInterceptor
import com.yoti.android.cryptocurrencychallenge.crosscuting.coroutine.DispatcherProvider
import com.yoti.android.cryptocurrencychallenge.domain.repository.CoinCapRepository
import com.yoti.android.cryptocurrencychallenge.domain.repository.CoinCapRepositoryImpl
import com.yoti.android.cryptocurrencychallenge.model.database.CoinCapDatabase
import com.yoti.android.cryptocurrencychallenge.model.database.dao.CoinCapDao
import com.yoti.android.cryptocurrencychallenge.model.network.api.CAPCOIN_ENDPOINT_HOST
import com.yoti.android.cryptocurrencychallenge.model.network.api.CoincapService
import com.yoti.android.cryptocurrencychallenge.ui.assets.AssetsAdapter
import dagger.Binds
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.android.qualifiers.ApplicationContext
import dagger.hilt.components.SingletonComponent
import okhttp3.Cache
import okhttp3.OkHttpClient
import okhttp3.logging.HttpLoggingInterceptor
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import javax.inject.Singleton

@InstallIn(SingletonComponent::class)
@Module
object DataModule {

    @Provides
    @Singleton
    fun provideDatabase(@ApplicationContext context: Context) =
        Room.databaseBuilder(
            context,
            CoinCapDatabase::class.java,
            "coin-db"
        ).build()

    @Provides
    fun provideTaskDao(db: CoinCapDatabase) = db.coinCapDao

    @Provides
    @Singleton
    fun provideDispatcherProvider(): DispatcherProvider {
        return DispatcherProvider.createInstance()
    }

    @Provides
    fun provideAuthInterceptorOkHttpClient(
        @ApplicationContext context: Context
    ): OkHttpClient {
        return OkHttpClient
            .Builder()
            .apply {
                this.addInterceptor(
                    HttpLoggingInterceptor().setLevel(
                        HttpLoggingInterceptor.Level.BODY
                    )
                )
                this.addInterceptor(ConnectivityInterceptor(context))
            }.build()
    }


    @Provides
    @Singleton
    fun provideCoincapService(client: OkHttpClient): CoincapService {
        return Retrofit.Builder()
            .baseUrl(CAPCOIN_ENDPOINT_HOST)
            .client(client)
            .addConverterFactory(GsonConverterFactory.create())
            .build()
            .create(CoincapService::class.java)
    }

    @Provides
    @Singleton
    fun provideCoincapRepository(
        dispatcherProvider: DispatcherProvider,
        coincapService: CoincapService,
        coinCapDao: CoinCapDao,
    ): CoinCapRepository {
        return CoinCapRepository.createInstance(dispatcherProvider, coincapService, coinCapDao)
    }


}