package hu.szoftarch.webshop.model.datasource.impl

import hu.szoftarch.webshop.model.api.ApiService
import hu.szoftarch.webshop.model.data.CategoryItem
import hu.szoftarch.webshop.model.repository.CategoryRepository
import javax.inject.Inject


class CategoryRepositoryImpl @Inject constructor(
    private val apiService: ApiService
) : CategoryRepository {

    override suspend fun getCategories(): List<CategoryItem> {
        return apiService.getCategories()
    }
}