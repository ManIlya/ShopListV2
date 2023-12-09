package com.example.shoplistv2.data.service

import com.example.shoplistv2.data.model.ShopItem
import org.junit.jupiter.api.Test

import org.junit.jupiter.api.Assertions.*
import java.io.File

class ShopListServiceTest {

    @Test
    fun saveItems() {
        val items = listOf(
                ShopItem(id = 1, checked = true, message = "Buy milk"),
                ShopItem(id = 2, checked = false, message = "Buy eggs")
        )
        val mockFilePath = "test.json"

        val service = JsonShopListService(mockFilePath)
        val result = service.saveItems(items)

        assertEquals(true, result)

        val fileContent = File(mockFilePath).readText()
        val expectedJson = """[{"id":1,"checked":true,"message":"Buy milk"},{"id":2,"checked":false,"message":"Buy eggs"}]"""
        assertEquals(expectedJson, fileContent)
    }

    @Test
    fun loadItems() {
        val items = listOf(
                ShopItem(id = 1, checked = true, message = "Buy milk"),
                ShopItem(id = 1, checked = false, message = "Buy eggs")
        )
        val mockFilePath = "test.json"

        val service = JsonShopListService(mockFilePath)
        val result = service.saveItems(items)

        val newItems = service.loadItems()

        assertEquals(items, newItems)
    }
}