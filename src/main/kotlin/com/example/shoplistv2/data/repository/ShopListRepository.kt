package com.example.shoplistv2.data.repository

import com.example.shoplistv2.data.model.ShopItem
import com.example.shoplistv2.data.service.JsonShopListService

class ShopListRepository : IShopListRepository {
    private val items = mutableListOf<ShopItem>()

    override fun getShopList(): List<ShopItem> = items

    override fun addItem(message: String) {
        val itemId = items.size + 1
        val newItem = ShopItem(id = itemId, checked = false, message = message)
        items.add(index = 0, element = newItem)
    }

    override fun updateItem(item: ShopItem) {
        val existingShopList = items.find { it.id == item.id }
        items.remove(existingShopList)
        items.add(0, item)
    }

    override fun deleteItem(item: ShopItem) {
        items.remove(item)
    }

    override fun openFile(fileName: String) {
        val service = JsonShopListService(fileName)
        val newItems = service.loadItems()
        items.clear()
        items.addAll(newItems)
    }

    override fun saveFile(fileName: String): Boolean {
        val service = JsonShopListService(fileName)
        val newItems = service.saveItems(items)
        if (newItems) {
            items.clear()
        }
        return newItems
    }
}