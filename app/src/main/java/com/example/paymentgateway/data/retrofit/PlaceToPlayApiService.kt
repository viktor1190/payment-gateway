package com.example.paymentgateway.data.retrofit

import com.example.paymentgateway.data.retrofit.model.CheckoutRequest
import com.example.paymentgateway.data.retrofit.model.CheckoutResponse
import com.example.paymentgateway.data.retrofit.model.StatusRequest
import com.example.paymentgateway.data.retrofit.model.StatusResponse
import retrofit2.http.Body
import retrofit2.http.POST

/**
 * This interface was designed according to the PlaceToPay API service definition
 */
interface PlaceToPlayApiService {

    @POST("gateway/process")
    fun processTransaction(
        @Body request: CheckoutRequest
    ): CheckoutResponse

    @POST(" gateway/query")
    fun getTransactionStatus(
        @Body request: StatusRequest
    ): StatusResponse

}

/* this works with the class at BlueJ to generate credentials. Just need to update the tranKey and seed values

POSTMAN CODE:

curl --location --request POST 'https://dev.placetopay.com/rest/gateway/process' \
--header 'Content-Type: application/json' \
--data-raw '{
  "auth": {
    "login": "6dd490faf9cb87a9862245da41170ff2",
    "tranKey": "TXYKn+R1QnrsgD2y8EuIB4CsAqHZFkQ2JuIRpkS1tjM=",
    "nonce": "V21FeXZ1dDlHZ3ZjTVdyVg==",
    "seed": "2020-04-23T01:42:30-05:00"
  },
  "payment": {
    "reference": "TEST_20171108_144400",
    "amount": {
      "currency": "USD",
      "total": 56.4
    }
  },
  "instrument": {
    "card": {
      "number": "36545400000008",
      "expirationMonth": "12",
      "expirationYear": "21",
      "cvv": "123"
    }
  },
  "payer": {
    "name": "Miss Delia Schamberger Sr.",
    "email": "tesst@gmail.com",
    "mobile": "3006108300"
  }
}'
 */