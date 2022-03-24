package com.yoti.android.cryptocurrencychallenge.model.database.dao

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import com.yoti.android.cryptocurrencychallenge.model.database.entity.CoinCapEntity
import kotlinx.coroutines.flow.Flow


@Dao
abstract class CoinCapDao {

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    abstract fun insertCoins(coins : List<CoinCapEntity>)

    @Query("SELECT * FROM coin")
    abstract fun getCoinsFromLocal(): Flow<List<CoinCapEntity>>
}