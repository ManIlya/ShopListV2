package com.example.shoplistv2.data.repository

import com.example.shoplistv2.data.model.ShopItem

class ShopListRepository : IShopListRepository {
    private val items = mutableListOf<ShopItem>()

    constructor(list: List<ShopItem>){
        items.addAll(list)
    }
    constructor(){
    }


    override fun getShopList(): List<ShopItem> = items

    override fun addItem(message: String) {
        val itemId = items.size + 1
        val newItem = ShopItem(id = itemId, checked = false, message = message)
        items.add(index = 0, element =  newItem)
    }

    override fun updateItem(item: ShopItem) {
        val existingShopList = items.find { it.id == item.id }
        items.remove(existingShopList)
        items.add(0, item)
    }

    override fun deleteItem(item: ShopItem) {
        items.remove(item)
    }
}