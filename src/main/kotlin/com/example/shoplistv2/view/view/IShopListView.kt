package com.example.shoplistv2.view.view

import com.example.shoplistv2.data.model.ShopItem

interface IShopListView {
    fun showAllItems(items: List<ShopItem>)
}