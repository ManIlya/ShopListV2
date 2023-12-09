package com.example.shoplistv2

import com.example.shoplistv2.data.model.ShopItem
import com.example.shoplistv2.data.repository.ShopListRepository
import com.example.shoplistv2.view.presenter.IShopListPresenter
import com.example.shoplistv2.view.presenter.ShopListPresenter
import com.example.shoplistv2.view.view.IShopListView
import javafx.fxml.FXML
import javafx.scene.control.ListView
import javafx.scene.control.TextField

class ShopListController : IShopListView{

    @FXML
    private lateinit var addShopItemTextField: TextField

    @FXML
    private lateinit var shopListView: ListView<ShopItem>

    private val presenter: IShopListPresenter = ShopListPresenter(this, ShopListRepository())


    @FXML
    fun initialize() {
        presenter.getAllList()
    }

    @FXML
    fun addItem() {
        val description = addShopItemTextField.text
        if (description.isNotEmpty()) {
            presenter.addItem(description)
            addShopItemTextField.text = ""
        }
    }
    @FXML
    fun deleteItem() {
        val selectedTask = shopListView.selectionModel.selectedItem
        if (selectedTask != null) {
            presenter.deleteItem(selectedTask)
        }
    }
    override fun showAllItems(items: List<ShopItem>) {
        shopListView.items.clear()
        shopListView.items.addAll(items)
    }
}