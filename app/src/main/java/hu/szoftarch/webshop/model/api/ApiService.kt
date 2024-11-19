package hu.szoftarch.webshop.model.api

import hu.szoftarch.webshop.model.data.CategoryItem
import hu.szoftarch.webshop.model.data.PaginatedProducts
import hu.szoftarch.webshop.model.data.PaymentDetails
import hu.szoftarch.webshop.model.data.PaymentResponse
import hu.szoftarch.webshop.model.data.ProductItem
import retrofit2.Response
import retrofit2.http.Body
import retrofit2.http.GET
import retrofit2.http.POST
import retrofit2.http.Path
import retrofit2.http.Query

interface ApiService {

    @GET("product")
    suspend fun getAllProducts(
        @Query("pageNumber") pageNumber: Int?,
        @Query("pageSize") pageSize: Int?,
        @Query("sortBy") sortBy: String?,
        @Query("sortDirection") sortDirection: String?,
        @Query("minPrice") minPrice: Int? = null,
        @Query("maxPrice") maxPrice: Int? = null,
        @Query("category") category: Int? = null,
        @Query("material") material: String? = null,
        @Query("searchString") searchString: String? = null
    ): PaginatedProducts

    @GET("product/{id}")
    suspend fun getProductById(@Path("id") id: Int): ProductItem

    @GET("product/serial/{serialNumber}")
    suspend fun getProductBySerialNumber(@Path("serialNumber") serialNumber: String): ProductItem

    @GET("Category")
    suspend fun getCategories(): List<CategoryItem>

    @POST("Order/initiate-payment")
    suspend fun initiatePayment(@Body paymentDetails: PaymentDetails): Response<PaymentResponse>
}
