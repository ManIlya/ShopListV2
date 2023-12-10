package com.example.shoplistv2

import com.example.shoplistv2.data.model.FilterEnum
import com.example.shoplistv2.data.model.ShopItem
import com.example.shoplistv2.data.repository.ShopListRepository
import com.example.shoplistv2.view.presenter.IShopListPresenter
import com.example.shoplistv2.view.presenter.ShopListPresenter
import com.example.shoplistv2.view.view.IShopListView
import javafx.application.Platform
import javafx.fxml.FXML
import javafx.scene.control.*
import javafx.scene.layout.HBox
import javafx.scene.text.Text
import javafx.stage.FileChooser
import javafx.stage.Stage
import javafx.util.Callback

class ShopListController : IShopListView {
    private lateinit var stage: Stage

    @FXML
    private lateinit var addShopItemTextField: TextField

    @FXML
    private lateinit var shopListView: ListView<ShopItem>

    @FXML
    private lateinit var allButton: RadioButton

    @FXML
    private lateinit var shoppedButton: RadioButton

    @FXML
    private lateinit var unshopedButton: RadioButton

    @FXML
    private lateinit var groupFilter: ToggleGroup

    private val presenter: IShopListPresenter = ShopListPresenter(this, ShopListRepository())


    @FXML
    fun initialize() {
        presenter.getList()
        shopListView.cellFactory = Callback { ShopItemCell() }
    }

    @FXML
    fun onRadioButtonSelected() {
        val selectedRadioButton = groupFilter.selectedToggle as RadioButton?

        if (selectedRadioButton != null) {
            var selectedFilter: FilterEnum = FilterEnum.all
            when (selectedRadioButton) {
                allButton -> selectedFilter = FilterEnum.all
                shoppedButton -> selectedFilter = FilterEnum.checked
                unshopedButton -> selectedFilter = FilterEnum.unchecked
            }
            presenter.setFilter(filter = selectedFilter)
        }

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

    inner class ShopItemCell : ListCell<ShopItem>() {

        override fun updateItem(item: ShopItem?, empty: Boolean) {
            super.updateItem(item, empty)

            if (empty || item == null) {
                text = null
                graphic = null
            } else {
                val checkBox = CheckBox()
                checkBox.isSelected = item.checked

                val message = item.message

                val hBox = HBox(checkBox, Text(message))
                hBox.spacing = 10.0

                checkBox.setOnAction { event ->
                    val selected = checkBox.isSelected
                    // Ваш код обработки события
                    handleCheckBoxClick(item, selected)
                }

                graphic = hBox
            }
        }
    }

    private fun handleCheckBoxClick(item: ShopItem, selected: Boolean) {
        item.checked = selected
        presenter.changeCheckItem(item)
    }


    @FXML
    fun handleOpen() {
        val fileChooser = FileChooser()
        fileChooser.title = "Open JSON File"
        fileChooser.extensionFilters.add(FileChooser.ExtensionFilter("JSON Files", "*.json"))

        val selectedFile = fileChooser.showOpenDialog(stage)
        if (selectedFile != null) {
            presenter.openFile(selectedFile.name)
        }
    }

    @FXML
    fun handleSave() {
        val fileChooser = FileChooser()
        fileChooser.title = "Open JSON File"
        fileChooser.extensionFilters.add(FileChooser.ExtensionFilter("JSON Files", "*.json"))

        val selectedFile = fileChooser.showSaveDialog(stage)
        if (selectedFile != null) {
            presenter.saveFile(selectedFile.name)
        }
    }

    @FXML
    fun handleExit() {
        Platform.exit()
    }

    fun setStage(stage: Stage) {
        this.stage = stage
    }
}