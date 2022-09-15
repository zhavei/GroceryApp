package com.syafei.groceryapp.ui

import androidx.lifecycle.ViewModel
import com.syafei.groceryapp.data.local.GroceryItems
import com.syafei.groceryapp.data.repository.GroceryRepository
import kotlinx.coroutines.GlobalScope
import kotlinx.coroutines.launch

class GroceryViewModel(private val repository: GroceryRepository) : ViewModel() {

    fun insert(item: GroceryItems) = GlobalScope.launch {
        repository.insert(item)
    }

    fun delete(item: GroceryItems) = GlobalScope.launch {
        repository.delete(item)
    }

    fun getAllGroceryItem(items: GroceryItems) = repository.getAllItems()

}