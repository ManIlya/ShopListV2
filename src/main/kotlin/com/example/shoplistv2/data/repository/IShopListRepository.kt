package com.example.shoplistv2.data.repository

import com.example.shoplistv2.data.model.ShopItem

interface IShopListRepository {
    fun getShopList(): List<ShopItem>
    fun addItem(message: String)
    fun updateCheckStatus(id: String)
    fun deleteItem(item: ShopItem)
    fun openFile(fileName: String)
    fun saveFile(fileName: String): Boolean
}