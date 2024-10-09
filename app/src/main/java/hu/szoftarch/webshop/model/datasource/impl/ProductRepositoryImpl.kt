package hu.szoftarch.webshop.model.datasource.impl

import hu.szoftarch.webshop.model.api.ApiService
import hu.szoftarch.webshop.model.data.PaginatedProducts
import hu.szoftarch.webshop.model.data.ProductItem
import hu.szoftarch.webshop.model.data.ProductRetrievalOptions
import hu.szoftarch.webshop.model.repository.ProductRepository
import javax.inject.Inject

class ProductRepositoryImpl @Inject constructor(
    private val apiService: ApiService
) : ProductRepository {
    override suspend fun getProductById(id: Int): ProductItem {
        return apiService.getProductById(id)
    }

    override suspend fun getProductBySerialNumber(serialNumber: String): ProductItem {
        return apiService.getProductBySerialNumber(serialNumber)
    }

    override suspend fun getProducts(options: ProductRetrievalOptions): PaginatedProducts {

        return apiService.getAllProducts(
            pageNumber = options.pageNumber,
            pageSize = options.pageSize,
            sortBy = options.sortBy,
            sortDirection = options.sortDirection,
            minPrice = options.minPrice,
            maxPrice = options.maxPrice,
            category = options.categoryId,
            material = options.material,
            searchString = options.searchString
        )
    }

}