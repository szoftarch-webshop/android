package hu.szoftarch.webshop.model.repository

import hu.szoftarch.webshop.model.data.SparePart

interface SparePartRepository {
    suspend fun getSparePartById(id: Long): SparePart?
}
