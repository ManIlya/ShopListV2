package com.example.shoplistv2.data.service

import com.example.shoplistv2.data.model.ShopItem
import com.fasterxml.jackson.module.kotlin.jacksonObjectMapper
import com.fasterxml.jackson.module.kotlin.jacksonTypeRef
import java.io.File

class JsonShopListService(private val filePath: String) : IShopListService {

    override fun saveItems(items: List<ShopItem>): Boolean {
        return try {
            val json = jacksonObjectMapper().writeValueAsString(items)
            File(filePath).writeText(json)
            true
        } catch (e: Exception) {
            //e.printStackTrace()
            println(e.message)
            false
        }
    }

    override fun loadItems(): List<ShopItem> {
        return try {
            val json = File(filePath).readText()
            jacksonObjectMapper().readValue(json, jacksonTypeRef<List<ShopItem>>())
        } catch (e: Exception) {
            println(e.message)
            emptyList()
        }
    }
}