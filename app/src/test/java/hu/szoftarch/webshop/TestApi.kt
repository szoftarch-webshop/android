package hu.szoftarch.webshop

import hu.szoftarch.webshop.model.api.ApiService
import hu.szoftarch.webshop.model.data.ProductRetrievalOptions
import hu.szoftarch.webshop.model.datasource.impl.CategoryRepositoryImpl
import hu.szoftarch.webshop.model.datasource.impl.ProductRepositoryImpl
import kotlinx.coroutines.runBlocking
import okhttp3.OkHttpClient
import org.junit.Test

import org.junit.Assert.*
import org.junit.Before
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import okhttp3.logging.HttpLoggingInterceptor


class ProductRepositoryImplIntegrationTest {

    private lateinit var apiService: ApiService
    private lateinit var productRepository: ProductRepositoryImpl
    private lateinit var client: OkHttpClient

    @Before
    fun setup() {

        val logging = HttpLoggingInterceptor()
        logging.setLevel(HttpLoggingInterceptor.Level.BODY)

        client = OkHttpClient.Builder()
            .addInterceptor(logging)
            .build()

        val retrofit = Retrofit.Builder()
            .baseUrl("http://192.168.0.154:5120/api/")
            .client(client)
            .addConverterFactory(GsonConverterFactory.create())
            .build()

        apiService = retrofit.create(ApiService::class.java)

        productRepository = ProductRepositoryImpl(apiService)
    }

    @Test
    fun `test getProducts retrieves 200 OK from API`() = runBlocking {
        val options = ProductRetrievalOptions(pageNumber = 1, pageSize = 10)

        val result = productRepository.getProducts(options)
        println(result)
        assertNotNull(result)
        assertTrue(result.products.isNotEmpty())
    }

    @Test
    fun `test getProducts with sort and pagination`() = runBlocking {
        val options = ProductRetrievalOptions(
            pageNumber = 1,
            pageSize = 5,
            sortBy = "price",
            sortDirection = "desc"
        )
        val result = productRepository.getProducts(options)

        assertNotNull(result)
        assertTrue(result.products.isNotEmpty())
    }
    @Test
    fun `test getProducts with price filter`() = runBlocking {
        // Test with minPrice and maxPrice
        val options = ProductRetrievalOptions(
            minPrice = 1000,
            maxPrice = 5000
        )
        val result = productRepository.getProducts(options)

        assertNotNull(result)
        assertTrue(result.products.all { it.price in 1000..5000 }) // Verify price filter works

    }

    @Test
    fun `test getProducts with category and material filter`() = runBlocking {
        val options = ProductRetrievalOptions(
            categoryId = 2,
            material = "Aluminum"
        )
        val result = productRepository.getProducts(options)

        assertNotNull(result)
        assertTrue(result.products.all { it.categoryNames.contains("Electronics") && it.material == "Aluminum" })

    }

    @Test
    fun `test getProducts with search string`() = runBlocking {
        val options = ProductRetrievalOptions(
            searchString = "Chair"
        )
        val result = productRepository.getProducts(options)

        assertNotNull(result)
        assertTrue(result.products.all { it.name.contains("Chair", ignoreCase = true) })
    }

    @Test
    fun `test getProducts with non-existent filters`() = runBlocking {
        val options = ProductRetrievalOptions(
            minPrice = 1000000,
            maxPrice = 2000000,
            categoryId = 0,
            material = "NonExistentMaterial"
        )
        val result = productRepository.getProducts(options)

        assertNotNull(result)
        assertTrue(result.products.isEmpty())
    }

    @Test
    fun `test getProducts sorted by price descending`() = runBlocking {
        val options = ProductRetrievalOptions(
            pageNumber = 1,
            pageSize = 10,
            sortBy = "price",
            sortDirection = "desc"
        )
        val result = productRepository.getProducts(options)

        assertNotNull(result)
        assertTrue(result.products.size >= 2)

        val prices = result.products.map { it.price }
        assertEquals(prices, prices.sortedDescending())
    }

    @Test
    fun `test getProducts paging works`() = runBlocking {
        val firstPageOptions = ProductRetrievalOptions(
            pageNumber = 1,
            pageSize = 5
        )
        val firstPageResult = productRepository.getProducts(firstPageOptions)
        assertNotNull(firstPageResult)
        assertTrue(firstPageResult.products.size <= 5)
        val secondPageOptions = ProductRetrievalOptions(
            pageNumber = 2,
            pageSize = 5
        )
        val secondPageResult = productRepository.getProducts(secondPageOptions)
        assertNotNull(secondPageResult)
        assertTrue(secondPageResult.products.size <= 5)

        val firstPageProductIds = firstPageResult.products.map { it.id }
        assertTrue(secondPageResult.products.none { it.id in firstPageProductIds })
    }

    @Test
    fun `test getProductById returns correct product`() = runBlocking {
        val productId = 1
        val product = productRepository.getProductById(productId)

        assertNotNull(product)
        assertEquals(productId, product.id)
    }

    @Test
    fun `test getProductBySerialNumber returns correct product`() = runBlocking {
        val serialNumber = "SN123"
        val product = productRepository.getProductBySerialNumber(serialNumber)

        assertNotNull(product)
        assertEquals(serialNumber, product.serialNumber)
    }
}

class CategoryRepositoryImplIntegrationTest {

    private lateinit var apiService: ApiService
    private lateinit var CategoryRepository: CategoryRepositoryImpl
    private lateinit var client: OkHttpClient

    @Before
    fun setup() {

        val logging = HttpLoggingInterceptor()
        logging.setLevel(HttpLoggingInterceptor.Level.BODY)

        client = OkHttpClient.Builder()
            .addInterceptor(logging)
            .build()

        val retrofit = Retrofit.Builder()
            .baseUrl("http://192.168.0.154:5120/api/")
            .client(client)
            .addConverterFactory(GsonConverterFactory.create())
            .build()

        apiService = retrofit.create(ApiService::class.java)

        CategoryRepository = CategoryRepositoryImpl(apiService)
    }

    @Test
    fun `test getProducts retrieves 200 OK from API`() = runBlocking {
        val options = ProductRetrievalOptions(pageNumber = 1, pageSize = 10)

        val result = CategoryRepository.getCategories()
        assertNotNull(result)
        assertTrue(result.isNotEmpty())
    }
}