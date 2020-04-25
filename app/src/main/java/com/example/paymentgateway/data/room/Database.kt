package com.example.paymentgateway.data.room

import androidx.room.Database
import androidx.room.RoomDatabase
import androidx.room.TypeConverter
import androidx.room.TypeConverters
import com.example.paymentgateway.data.core.toIsoDateString
import com.example.paymentgateway.data.core.toZonedDateTime
import com.example.paymentgateway.data.room.entity.TransactionStatusDao
import com.example.paymentgateway.data.room.entity.TransactionStatusStore
import java.math.BigDecimal
import java.time.ZonedDateTime

@Database(entities = [TransactionStatusStore::class], version = 1)
@TypeConverters(Converters::class)
abstract class Database: RoomDatabase() {

    abstract fun transactionStatusDao(): TransactionStatusDao
}

class Converters {

    @TypeConverter
    fun toZonedDateTime(dateString: String?): ZonedDateTime? {
        return dateString?.toZonedDateTime()
    }

    @TypeConverter
    fun toDateString(date: ZonedDateTime?): String? {
        return date?.toIsoDateString()
    }

    @TypeConverter
    fun fromLong(value: Long?): BigDecimal? {
            return if(value != null) BigDecimal(value).divide(BigDecimal(100)) else null
        }

    @TypeConverter
    fun toLong(bigDecimal: BigDecimal?): Long? {
        return bigDecimal?.multiply(BigDecimal(100))?.toLong()
    }

}
