package com.syafei.groceryapp.ui

import android.app.Dialog
import android.os.Bundle
import android.widget.Button
import android.widget.EditText
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import androidx.lifecycle.ViewModelProvider
import androidx.recyclerview.widget.LinearLayoutManager
import com.syafei.groceryapp.R
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
        val dialog = Dialog(this)
        dialog.setContentView(R.layout.grocery_add_dialog)
        val cancelBtn = dialog.findViewById<Button>(R.id.btnCencel)
        val addBtn = dialog.findViewById<Button>(R.id.btnAdd)
        val itemEt = dialog.findViewById<EditText>(R.id.idTvItemName)
        val itemPriceET = dialog.findViewById<EditText>(R.id.idEtitemPrice)
        val itemQUantity = dialog.findViewById<EditText>(R.id.idEtitemQuantity)

        cancelBtn.setOnClickListener {
            dialog.dismiss()
        }
        addBtn.setOnClickListener {
            val itemName: String = itemEt.text.toString()
            val itemPrice: String = itemPriceET.text.toString()
            val itemQuantity: String = itemQUantity.text.toString()
            val itPrice: Int = itemPrice.toInt()
            val itQuanty: Int = itemQuantity.toInt()
            if (itemName.isNotEmpty() && itemPrice.isNotEmpty() && itemQuantity.isNotEmpty()) {
                val items = GroceryItems(itemName, itPrice, itQuanty)
                groceryViewModel.insert(items)
                Toast.makeText(applicationContext, "data inserted", Toast.LENGTH_SHORT).show()
                groceryAdapter.notifyDataSetChanged()
                dialog.dismiss()
            } else {
                Toast.makeText(applicationContext, "please enter valid - valid", Toast.LENGTH_SHORT)
                    .show()
            }
        }
        dialog.show()
    }

    override fun onItemClicked(groceryItems: GroceryItems) {
        groceryViewModel.delete(groceryItems)
        groceryAdapter.notifyDataSetChanged()
        Toast.makeText(applicationContext, "item di deleted", Toast.LENGTH_SHORT).show()
    }
}