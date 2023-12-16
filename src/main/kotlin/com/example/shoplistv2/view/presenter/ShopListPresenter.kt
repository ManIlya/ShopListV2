package com.example.shoplistv2.view.presenter

import com.example.shoplistv2.data.model.FilterEnum
import com.example.shoplistv2.data.model.ShopItem
import com.example.shoplistv2.data.repository.IShopListRepository
import com.example.shoplistv2.view.view.IShopListView

class ShopListPresenter(private val view: IShopListView, private val repository: IShopListRepository) : IShopListPresenter {
    private var filterList = FilterEnum.all
    private fun getAllList() {
        val items = repository.getShopList()
        view.showAllItems(items)
    }

    private fun getCheckedList() {
        val items = repository.getShopList().toList().filter { it.checked }.toList()
        view.showAllItems(items)
    }

    private fun getUncheckedList() {
        val items = repository.getShopList().toList().filter { !it.checked }.toList()
        view.showAllItems(items)
    }

    override fun setFilter(filter: FilterEnum) {
        this.filterList = filter
        getList()
    }

    override fun getList() {
        when (filterList) {
            FilterEnum.all -> getAllList()
            FilterEnum.checked -> getCheckedList()
            FilterEnum.unchecked -> getUncheckedList()
        }
    }

    override fun addItem(message: String) {
        repository.addItem(message)
        getList()

    }

    override fun changeCheckItem(item: ShopItem) {
        repository.updateCheckStatus(item.id)
        getList()
    }

    override fun deleteItem(item: ShopItem) {
        repository.deleteItem(item)
        getList()
    }

    override fun openFile(fileName: String) {
        repository.openFile(fileName)
        getList()
    }

    override fun saveFile(fileName: String): Boolean {
        val check = repository.saveFile(fileName)
        getList()
        return check
    }

}