package com.example.shoplistv2.view.presenter

import com.example.shoplistv2.data.model.ShopItem

interface IShopListPresenter {
    fun getAllList()
    fun getCheckedList()
    fun getUncheckedList()
    fun addItem(message: String)
    fun changeCheckItem(item: ShopItem)
    fun deleteItem(item: ShopItem)
}