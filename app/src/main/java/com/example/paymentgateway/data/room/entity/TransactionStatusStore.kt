package com.example.paymentgateway.data.room.entity

import androidx.lifecycle.LiveData
import androidx.room.Dao
import androidx.room.Entity
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.PrimaryKey
import androidx.room.Query
import java.math.BigDecimal
import java.time.ZonedDateTime

@Entity
data class TransactionStatusStore (
    @PrimaryKey val reference: String,
    val internalReference: BigDecimal?,
    val status: String?,
    val reason: String?,
    val message: String?,
    val currency: String,
    val total: Float,
    val paymentMethod: String?,
    val franchiseName: String?,
    val authorization: String?,
    val receipt: String,
    val lastUpdate: ZonedDateTime
)

@Dao
interface TransactionStatusDao {
    @Insert(onConflict = OnConflictStrategy.REPLACE)
    fun save(transactionStatusStore: TransactionStatusStore)

    @Query("SELECT * FROM TransactionStatusStore WHERE reference = :reference")
    fun load(reference: String): LiveData<TransactionStatusStore>

    @Query("SELECT * FROM TransactionStatusStore")
    fun listAll(): LiveData<List<TransactionStatusStore>>
}