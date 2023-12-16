package com.example.shoplistv2.data.repository

import com.example.shoplistv2.data.model.ShopItem
import com.example.shoplistv2.data.service.IShopListService
import com.example.shoplistv2.data.service.JsonShopItemService


class ShopListRepository(private val shopItemService: IShopListService = JsonShopItemService()) : IShopListRepository {

    private var shopList: MutableList<ShopItem> = mutableListOf()

    override fun getShopList(): List<ShopItem> {
        return shopList.toList()
    }

    override fun addItem(message: String) {
        val newItem = ShopItem(message = message)
        shopList.add(0, newItem)
    }

    override fun updateCheckStatus(id: String) {
        val existingItem = shopList.find { it.id == id }
        existingItem?.let {
            shopList.remove(it)
            shopList.add(0, it)
        }
    }

    override fun deleteItem(item: ShopItem) {
        shopList.removeIf { it.id == item.id }
    }

    override fun openFile(fileName: String) {
        shopList = shopItemService.loadItems(fileName).toMutableList()
    }

    override fun saveFile(fileName: String): Boolean {
        val newItems = shopItemService.saveItems(shopList, fileName, )
        if (newItems) {
            shopList.clear()
        }
        return newItems
    }
}