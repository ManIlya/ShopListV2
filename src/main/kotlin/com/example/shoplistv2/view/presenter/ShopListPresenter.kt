package com.example.shoplistv2.view.presenter

import com.example.shoplistv2.data.model.ShopItem
import com.example.shoplistv2.data.repository.IShopListRepository
import com.example.shoplistv2.view.view.IShopListView

class ShopListPresenter(private val view: IShopListView, private val repository: IShopListRepository) : IShopListPresenter{
    override fun getAllList() {
        val items = repository.getShopList()
        view.showAllItems(items)
    }

    override fun getCheckedList() {
        val items = repository.getShopList().toList().filter {  it.checked }.toList()
        view.showAllItems(items)
    }

    override fun getUncheckedList() {
        val items = repository.getShopList().toList().filter {  !it.checked }.toList()
        view.showAllItems(items)
    }

    override fun addItem(message: String) {
        repository.addItem(message)
        getAllList()

    }

    override fun changeCheckItem(item: ShopItem) {
        repository.updateItem(item)
        getAllList()
    }

    override fun deleteItem(item: ShopItem) {
        repository.deleteItem(item)
        getAllList()
    }

}