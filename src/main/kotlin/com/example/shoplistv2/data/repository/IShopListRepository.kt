package com.example.shoplistv2.data.repository

import com.example.shoplistv2.data.model.ShopItem

interface IShopListRepository {
    fun getShopList(): List<ShopItem>
    fun addItem(message: String)
    fun updateItem(item: ShopItem)
    fun deleteItem(item: ShopItem)
}