package hu.szoftarch.webshop.model.api

import Products
import retrofit2.http.GET

interface ApiService {
    @GET("products")
    suspend fun getProductsListFromApi(): Products
}