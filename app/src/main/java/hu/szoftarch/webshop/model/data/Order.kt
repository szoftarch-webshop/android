package hu.szoftarch.webshop.model.data

data class Order(
    val uniqueID: String,
    val date: String,
    val status: String,
    val orderItems: List<OrderItem>,
    val shippingAddressId: String,
    val paymentMethodId: String
)

data class OrderItem(
    val uniqueID: String,
    val productId: String,
    val orderId: String,
    val amount: Int,
    val orderedPrice: Double
)