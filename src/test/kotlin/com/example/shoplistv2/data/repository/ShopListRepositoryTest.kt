package com.example.shoplistv2.data.repository

import org.junit.jupiter.api.Test

import org.junit.jupiter.api.Assertions.*

class ShopListRepositoryTest {

    @Test
    fun getShopList() {
        val repository = ShopListRepository()
        val items = repository.getShopList()

        assertEquals(0, items.size)
    }

    @Test
    fun addItem() {
        val repository = ShopListRepository()
        repository.addItem("Pocket Milk")

        val items = repository.getShopList()
        assertEquals(1, items.size)

        val milkItem = items[0]
        assertEquals("Pocket Milk", milkItem.message)
        assertEquals(false, milkItem.checked)
    }

    @Test
    fun updateItem() {
        val repository = ShopListRepository()
        repository.addItem("Pocket Milk")

        val shopListBeforeUpdate = repository.getShopList()
        val shopItemToUpdate = shopListBeforeUpdate[0].copy(message = "Pocket Milk", checked = true)

        repository.updateItem(shopItemToUpdate)

        val ShopListAfterUpdate = repository.getShopList()
        assertEquals(1, ShopListAfterUpdate.size)

        val updatedItem = ShopListAfterUpdate[0]
        assertEquals("Pocket Milk", updatedItem.message)
        assertEquals(true, updatedItem.checked)
    }

    @Test
    fun deleteItem() {
        val repository = ShopListRepository()
        repository.addItem("Pocket Milk")

        val shopListBeforeDelete = repository.getShopList()
        val itemToDelete = shopListBeforeDelete[0]

        repository.deleteItem(itemToDelete)

        val shopListAfterDelete = repository.getShopList()
        assertEquals(0, shopListAfterDelete.size)
    }

    @Test
    fun openFile() {

    }

    @Test
    fun saveFile() {
    }
}