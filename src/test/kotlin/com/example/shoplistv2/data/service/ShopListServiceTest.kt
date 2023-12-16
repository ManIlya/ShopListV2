package com.example.shoplistv2.data.service

import com.example.shoplistv2.data.model.ShopItem
import com.fasterxml.jackson.databind.ObjectMapper
import com.fasterxml.jackson.module.kotlin.registerKotlinModule
import org.junit.Assert.assertEquals
import org.junit.Assert.assertTrue
import org.junit.Rule
import org.junit.Test
import org.junit.rules.TemporaryFolder
import java.io.File

class ShopItemServiceTest {

    @Rule
    @JvmField
    val tempFolder = TemporaryFolder()

    private val objectMapper = ObjectMapper().registerKotlinModule()
    private val shopItemService = JsonShopItemService(objectMapper)

    @Test
    fun testSaveAndReadShopItemList() {
        val shopItems = listOf(
                ShopItem(message = "Item 1"),
                ShopItem(message = "Item 2", checked = true),
                ShopItem(message = "Item 3")
        )

        val fileName = tempFolder.newFile("test_shop_items.json").absolutePath

        // Save shopItems to file
        shopItemService.saveItems(shopItems, fileName)

        // Read shopItems from file
        val readShopItems = shopItemService.loadItems(fileName)

        // Verify the equality
        assertEquals(shopItems, readShopItems)
    }

    @Test
    fun testReadEmptyShopItemListFromFile() {
        val fileName = tempFolder.newFile("empty_shop_items.json").absolutePath

        // Read shopItems from an empty file
        val readShopItems = shopItemService.loadItems(fileName)

        // Verify that the list is empty
        assertTrue(readShopItems.isEmpty())
    }

    @Test(expected = IllegalArgumentException::class)
    fun testSaveShopItemListToFileWithEmptyFileName() {
        val shopItems = listOf(
                ShopItem(message = "Item 1"),
                ShopItem(message = "Item 2")
        )

        // Try to save shopItems to an empty file name
        shopItemService.saveItems(shopItems, "")
    }

    @Test(expected = java.io.IOException::class)
    fun testReadShopItemListFromNonexistentFile() {
        val fileName = tempFolder.root.resolve("nonexistent_file.json").absolutePath

        // Try to read shopItems from a nonexistent file
        shopItemService.loadItems(fileName)
    }

    @Test(expected = java.io.IOException::class)
    fun testReadShopItemListFromInvalidJsonFile() {
        val fileName = tempFolder.newFile("invalid_json_file.json").absolutePath

        // Write invalid JSON content to the file
        File(fileName).writeText("{invalid_json}")

        // Try to read shopItems from an invalid JSON file
        shopItemService.loadItems(fileName)
    }
}
