package com.example.shoplistv2.data.service

import com.example.shoplistv2.data.model.ShopItem
import com.fasterxml.jackson.core.JsonParseException
import com.fasterxml.jackson.databind.JsonMappingException
import com.fasterxml.jackson.databind.ObjectMapper
import com.fasterxml.jackson.module.kotlin.readValue
import com.fasterxml.jackson.module.kotlin.registerKotlinModule
import java.io.File
import java.io.IOException

class JsonShopItemService(private val objectMapper: ObjectMapper = ObjectMapper().registerKotlinModule()) : IShopListService {

    override fun saveItems(shopItems: List<ShopItem>, path: String): Boolean {
        if (path.isBlank()) {
            throw IllegalArgumentException("File name must not be empty")
        }

        val json = objectMapper.writeValueAsString(shopItems)
        File(path).writeText(json)
        return true
    }

    override fun loadItems(path: String): List<ShopItem> {
        try {
            val json = File(path).readText()
            return if (json.isNotEmpty()) {
                objectMapper.readValue(json)
            } else {
                emptyList()
            }
        } catch (e: JsonParseException) {
            throw IOException("Error parsing JSON from file: $path", e)
        } catch (e: JsonMappingException) {
            throw IOException("Error mapping JSON to ShopItem list from file: $path", e)
        } catch (e: IOException) {
            throw IOException("Error reading file: $path", e)
        }
    }
}
