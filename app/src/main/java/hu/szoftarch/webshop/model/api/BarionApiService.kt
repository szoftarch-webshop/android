package hu.szoftarch.webshop.model.api

import hu.szoftarch.webshop.model.data.BarionPaymentStatusResponse
import retrofit2.Response
import retrofit2.http.GET
import retrofit2.http.Header
import retrofit2.http.Path
import retrofit2.http.Query

interface BarionApiService {
    @GET("Payment/GetPaymentState/")
    suspend fun getPaymentStatus(
        @Query("POSKey") posKey: String = "d4443f71-51ed-4c9f-8bd3-b22f4e4ce4bb",
        @Query("paymentId") paymentId: String
    ): Response<BarionPaymentStatusResponse>
}