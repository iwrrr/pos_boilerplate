package com.example.pos_boilerplate.core.domain.model

sealed class PaymentMethod(
    val id: String,
    val displayName: String
) {
    data object Cash : PaymentMethod(id = "cash", displayName = "Tunai")
    data object Transfer : PaymentMethod(id = "transfer", displayName = "Transfer")
    data object EWallet : PaymentMethod(id = "e_wallet", displayName = "E-Wallet")
}
