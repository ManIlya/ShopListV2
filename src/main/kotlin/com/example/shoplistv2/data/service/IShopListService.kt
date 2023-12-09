package com.example.shoplistv2.data.service

import com.example.shoplistv2.data.model.ShopItem

interface IShopListService {
    fun saveItems(items: List<ShopItem>): Boolean
    fun loadItems(): List<ShopItem>
}