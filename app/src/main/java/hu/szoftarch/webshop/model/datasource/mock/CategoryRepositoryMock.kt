package hu.szoftarch.webshop.model.datasource.mock

import hu.szoftarch.webshop.model.data.CategoryItem
import hu.szoftarch.webshop.model.repository.CategoryRepository

object CategoryRepositoryMock : CategoryRepository {
    private val categoryItems = mutableListOf(
        CategoryItem(id = 1, name = "Category 1"),
        CategoryItem(id = 2, name = "Category 2")
    )

    override suspend fun getCategories() = categoryItems.toList()
}
