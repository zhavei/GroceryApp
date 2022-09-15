package com.syafei.groceryapp.data.repository

import com.syafei.groceryapp.data.local.GroceryDataBase
import com.syafei.groceryapp.data.local.GroceryItems

class GroceryRepository(private val db: GroceryDataBase) {

    suspend fun insert(item: GroceryItems) = db.getGroceryDao().insert(item)
    suspend fun delete(item: GroceryItems) = db.getGroceryDao().delete(item)

    fun getAllItems() = db.getGroceryDao().getAllGroceryItem()
}