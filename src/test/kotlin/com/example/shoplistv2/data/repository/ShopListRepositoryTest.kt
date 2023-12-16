package com.example.shoplistv2.data.repository

import com.example.shoplistv2.data.model.ShopItem
import com.example.shoplistv2.data.service.JsonShopItemService
import org.junit.Assert.*
import org.junit.Rule
import org.junit.Test
import org.junit.rules.TemporaryFolder
import org.mockito.Mockito.*
import java.lang.reflect.Field

class ShopListRepositoryTest {

    @Rule
    @JvmField
    val tempFolder = TemporaryFolder()

    private val mockShopItemService = mock(JsonShopItemService::class.java)
    private val shopListRepository = ShopListRepository(mockShopItemService)

    @Test
    fun testGetShopList() {
        val shopItems = listOf(
                ShopItem(message = "Item 1"),
                ShopItem(message = "Item 2", checked = true),
                ShopItem(message = "Item 3")
        )

        `when`(mockShopItemService.loadItems(anyString())).thenReturn(shopItems)

        shopListRepository.openFile(tempFolder.newFile("test_shop_items.json").absolutePath)

        val retrievedShopList = shopListRepository.getShopList()
        assertEquals(shopItems, retrievedShopList)

        verify(mockShopItemService).loadItems(anyString())
    }
    @Test
    fun testAddItem() {
        // Given
        val initialSize = shopListRepository.getShopList().size
        val newItemMessage = "New Item"

        // When
        shopListRepository.addItem(newItemMessage)

        // Then
        val updatedSize = shopListRepository.getShopList().size
        assertEquals(initialSize + 1, updatedSize)

        val newItemAdded = shopListRepository.getShopList()[0]
        assertEquals(newItemMessage, newItemAdded.message)

        // Verify that the method on the mock service was not called
        verify(mockShopItemService, never()).saveItems(anyList(), anyString())
    }


    @Test
    fun testUpdateItem() {
        // Given
        val initialShopList = listOf(
                ShopItem(id = "1", message = "Item 1", checked = false),
                ShopItem(id = "2", message = "Item 2", checked = false),
                ShopItem(id = "3", message = "Item 3", checked = false)
        )

        // Using reflection to set the private shopList property
        val shopListField: Field = ShopListRepository::class.java.getDeclaredField("shopList")
        shopListField.isAccessible = true
        val shopList = shopListField.get(shopListRepository) as MutableList<ShopItem>
        shopList.addAll(initialShopList)

        val updatedItem = ShopItem(id = "2", message = "Updated Item", checked = true)

        // When
        shopListRepository.updateCheckStatus(updatedItem.id)

        // Then
        // Using reflection to get the updated shopList
        val updatedList = shopListField.get(shopListRepository) as List<ShopItem>

        // Verify that the item is updated
        val updatedItemInList = updatedList.find { it.id == updatedItem.id }
        assertEquals(updatedItem.checked, updatedItemInList?.checked)

        // Verify that the updated item is moved to the beginning of the list
        assertEquals(updatedItem.id, updatedList.first().id)

        // Verify that the method on the mock service was not called
        verify(mockShopItemService, never()).saveItems(anyList(), anyString())
    }

    @Test
    fun testDeleteItem() {
        // Given
        val initialShopList = listOf(
                ShopItem(id = "1", message = "Item 1", checked = false),
                ShopItem(id = "2", message = "Item 2", checked = false),
                ShopItem(id = "3", message = "Item 3", checked = false)
        )

        val shopListField: Field = ShopListRepository::class.java.getDeclaredField("shopList")
        shopListField.isAccessible = true
        val shopList = shopListField.get(shopListRepository) as MutableList<ShopItem>
        shopList.addAll(initialShopList)

        val itemToDelete = ShopItem(id = "2", message = "Item 2", checked = false)

        // When
        shopListRepository.deleteItem(itemToDelete)

        // Then
        val updatedList = shopListRepository.getShopList()

        // Verify that the item is deleted
        assertFalse(updatedList.any { it.id == itemToDelete.id })

        // Verify that the other items are still in the list
        assertTrue(updatedList.any { it.id == "1" })
        assertTrue(updatedList.any { it.id == "3" })
    }
    @Test
    fun testSaveFile_Success() {
        // Given
        val fileName = "test_shop_items.json"
        val expectedShopList = listOf(
                ShopItem(message = "Item 1"),
                ShopItem(message = "Item 2", checked = true),
                ShopItem(message = "Item 3")
        )

        // Stubbing the behavior of the mock service
        `when`(mockShopItemService.saveItems(expectedShopList, fileName)).thenReturn(true)

        val shopListField: Field = ShopListRepository::class.java.getDeclaredField("shopList")
        shopListField.isAccessible = true
        val shopList = shopListField.get(shopListRepository) as MutableList<ShopItem>
        shopList.addAll(expectedShopList)
        // When
        val result = shopListRepository.saveFile(fileName)

        // Then
        assertEquals(true, result)

        }

    @Test(expected = RuntimeException::class)
    fun testSaveFileFailure() {
        // Given
        val fileName = "test_file.json"

        // Stubbing the behavior of the mock service to simulate an exception
        `when`(mockShopItemService.saveItems(anyList(), anyString())).thenThrow(RuntimeException("Simulated error"))

        // When (this should throw a RuntimeException)
        shopListRepository.saveFile(fileName)
    }
    @Test
    fun testOpenFile() {
        val shopItems = listOf(
                ShopItem(message = "Item 1"),
                ShopItem(message = "Item 2", checked = true),
                ShopItem(message = "Item 3")
        )

        `when`(mockShopItemService.loadItems(anyString())).thenReturn(shopItems)

        shopListRepository.openFile(tempFolder.newFile("test_shop_items.json").absolutePath)

        val retrievedShopList = shopListRepository.getShopList()
        assertEquals(shopItems, retrievedShopList)

        verify(mockShopItemService).loadItems(anyString())
    }
}
