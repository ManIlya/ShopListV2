package com.example.shoplistv2.data.service

import com.example.shoplistv2.data.model.ShopItem

interface IShopListService {
    fun saveItems(shopItems: List<ShopItem>, path: String): Boolean
    fun loadItems(path: String): List<ShopItem>
}