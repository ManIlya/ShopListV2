package com.example.shoplistv2.view.presenter

import com.example.shoplistv2.data.model.FilterEnum
import com.example.shoplistv2.data.model.ShopItem

interface IShopListPresenter {
    fun setFilter(filter: FilterEnum)
    fun getList()
    fun addItem(message: String)
    fun changeCheckItem(item: ShopItem)
    fun deleteItem(item: ShopItem)
    fun openFile(fileName: String)
    fun saveFile(fileName: String): Boolean
}