package com.syafei.groceryapp.ui

import android.os.Bundle
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import androidx.lifecycle.ViewModelProvider
import androidx.recyclerview.widget.LinearLayoutManager
import com.syafei.groceryapp.data.local.GroceryDataBase
import com.syafei.groceryapp.data.local.GroceryItems
import com.syafei.groceryapp.data.repository.GroceryRepository
import com.syafei.groceryapp.databinding.ActivityMainBinding

class MainActivity : AppCompatActivity(), GroceryAdapter.GroceryItemClick {

    private val binding by lazy {
        ActivityMainBinding.inflate(layoutInflater)
    }

    private lateinit var list: List<GroceryItems>
    private lateinit var groceryAdapter: GroceryAdapter
    private lateinit var groceryViewModel: GroceryViewModel

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(binding.root)

        list = ArrayList<GroceryItems>()
        groceryAdapter = GroceryAdapter(list, this)
        binding.rvItem.layoutManager = LinearLayoutManager(this)
        binding.rvItem.adapter = groceryAdapter

        val groceryRepository = GroceryRepository(GroceryDataBase(this))
        val factoryGrocery = GroceryViewModelFactory(groceryRepository)

        groceryViewModel = ViewModelProvider(this, factoryGrocery).get(GroceryViewModel::class.java)
        groceryViewModel.getAllGroceryItem(list).observe(this) {
            groceryAdapter.list = it
            groceryAdapter.notifyDataSetChanged()
        }

        binding.fabAdd.setOnClickListener {
            openDilog()
        }

    }

    private fun openDilog() {
        TODO("Not yet implemented")
    }

    override fun onItemClicked(groceryItems: GroceryItems) {
        groceryViewModel.delete(groceryItems)
        groceryAdapter.notifyDataSetChanged()
        Toast.makeText(applicationContext, "item di deleted", Toast.LENGTH_SHORT).show()
    }
}