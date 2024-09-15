package hu.szoftarch.webshop.model.repository

import hu.szoftarch.webshop.model.data.CategoryItem

interface CategoryRepository {
    suspend fun getCategories(): List<CategoryItem>
}
