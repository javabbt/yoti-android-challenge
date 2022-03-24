package com.yoti.android.cryptocurrencychallenge.model.database

import android.content.Context
import androidx.room.Database
import androidx.room.Room
import androidx.room.RoomDatabase
import androidx.room.TypeConverters
import androidx.sqlite.db.SupportSQLiteDatabase
import com.yoti.android.cryptocurrencychallenge.model.database.dao.CoinCapDao
import com.yoti.android.cryptocurrencychallenge.model.database.entity.CoinCapEntity
import com.yoti.android.cryptocurrencychallenge.model.database.typeconverter.DateTypeConverter

@Database(
    entities = [
        CoinCapEntity::class
    ],
    version = 1,
)
@TypeConverters(
    DateTypeConverter::class
)
abstract class CoinCapDatabase : RoomDatabase() {
    abstract val coinCapDao: CoinCapDao
}