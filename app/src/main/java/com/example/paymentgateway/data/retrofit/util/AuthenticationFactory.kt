package com.example.paymentgateway.data.retrofit.util

import android.util.Base64
import com.example.paymentgateway.data.retrofit.model.Authentication
import com.example.paymentgateway.domain.entity.LoggedInUser
import timber.log.Timber
import java.math.BigInteger
import java.security.MessageDigest
import java.security.NoSuchAlgorithmException
import java.security.SecureRandom
import java.time.ZonedDateTime
import java.time.format.DateTimeFormatter
import java.time.temporal.ChronoUnit

class AuthenticationFactory(private val user: LoggedInUser) {

    fun getAuthentication(): Authentication {
        val nonce = BigInteger(13, SecureRandom()).toString()
        val seed = ZonedDateTime.now().truncatedTo(ChronoUnit.SECONDS).format( DateTimeFormatter.ISO_OFFSET_DATE_TIME)
        val tranKey = try {
            val shaEncoded: ByteArray = sha1(nonce + seed + user.password)
            base64(shaEncoded)
        } catch (e: NoSuchAlgorithmException) {
            Timber.e(e)
            ""
        }
        return Authentication(user.username, tranKey, base64(nonce.toByteArray()), seed)
    }

    private fun sha1(input: String): ByteArray {
        val digest: MessageDigest = MessageDigest.getInstance("SHA-256")
        return digest.digest(input.toByteArray())
    }

    private fun base64(input: ByteArray?): String {
        val encodedBytes: ByteArray = Base64.encode(input, Base64.NO_WRAP)
        return String(encodedBytes)
    }
}