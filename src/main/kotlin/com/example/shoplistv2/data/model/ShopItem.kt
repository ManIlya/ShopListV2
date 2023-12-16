package com.example.shoplistv2.data.model

import java.util.*


data class ShopItem(
        val id: String = UUID.randomUUID().toString(),
        var checked: Boolean = false,
        val message: String
){
    init {
        if (message.length > 50) {
            throw IllegalArgumentException("Message length must not exceed 50 characters")
        }
    }
}
