package com.example.shoplistv2.data.model

import org.junit.Test
import org.junit.jupiter.api.Assertions.*

class ShopItemTest{

    @Test
    fun testDefaultValues() {
        val shopItem = ShopItem(message = "Test Message")

        assertEquals(false, shopItem.checked)
        assertTrue(shopItem.id.isNotBlank())
        assertEquals("Test Message", shopItem.message)
    }

    @Test
    fun testCustomValues() {
        val shopItem = ShopItem(id = "123", checked = true, message = "Custom Message")

        assertEquals("123", shopItem.id)
        assertTrue(shopItem.checked)
        assertEquals("Custom Message", shopItem.message)
    }

    @Test(expected = IllegalArgumentException::class)
    fun testMessageLengthExceedsLimit() {
        ShopItem(message = "This is a very long message that exceeds the limit of 50 characters")
    }

    @Test
    fun testMessageLengthWithinLimit() {
        val shopItem = ShopItem(message = "Short Message")
        assertEquals("Short Message", shopItem.message)
    }

    @Test
    fun testToggleChecked() {
        val shopItem = ShopItem(message = "Toggle Test")

        assertFalse(shopItem.checked)
        shopItem.checked = true
        assertTrue(shopItem.checked)
        shopItem.checked = false
        assertFalse(shopItem.checked)
    }
}