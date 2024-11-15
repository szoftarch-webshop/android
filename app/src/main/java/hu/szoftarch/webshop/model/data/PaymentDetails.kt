package hu.szoftarch.webshop.model.data

data class PaymentDetails(
    val customerInfo: CustomerInfo,
    val cartItems: List<CartItem>,
    val totalAmount: Int
)

data class CartItem(
    val productId: Int,
    val quantity: Int
)