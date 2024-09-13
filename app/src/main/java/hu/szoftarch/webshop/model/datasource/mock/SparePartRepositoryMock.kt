package hu.szoftarch.webshop.model.datasource.mock

import hu.szoftarch.webshop.model.data.SparePart
import hu.szoftarch.webshop.model.repository.SparePartRepository

class SparePartRepositoryMock : SparePartRepository {
    override suspend fun getSparePartById(id: Long) = SparePart(
        id = 1,
        name = "Mock part",
        price = 1.0,
        weight = 1.0,
        imageUrl = "https://picsum.photos/700/400"
    )
}
