package com.syafei.groceryapp.ui

import android.app.Dialog
import android.os.Bundle
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import androidx.lifecycle.ViewModelProvider
import androidx.recyclerview.widget.LinearLayoutManager
import com.syafei.groceryapp.data.local.GroceryDataBase
import com.syafei.groceryapp.data.local.GroceryItems
import com.syafei.groceryapp.data.repository.GroceryRepository
import com.syafei.groceryapp.databinding.ActivityMainBinding
import com.syafei.groceryapp.databinding.GroceryAddDialogBinding

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

        val dialogBinding = GroceryAddDialogBinding.inflate(layoutInflater)
        dialog.setContentView(dialogBinding.root)

        dialogBinding.btnCencel.setOnClickListener {
            dialog.dismiss()
        }
        dialogBinding.btnAdd.setOnClickListener {
            val itemName: String = dialogBinding.idEtitemName.text.toString()
            val itemPrice: String = dialogBinding.idEtitemPrice.text.toString().trim()
            val itemQuantity: String = dialogBinding.idEtitemQuantity.text.toString().trim()
            /*val itPrice: Int = itemPrice.toInt()
            val itQuanty: Int = itemQuantity.toInt()*/
            /* if (itemName.isNotEmpty() && itemPrice.trim().isNotEmpty() && itemQuantity.trim()
                     .isNotEmpty()
             ) {
                 val items = GroceryItems(itemName, itQuanty, itPrice)
                 groceryViewModel.insert(items)
                 groceryAdapter.notifyDataSetChanged()
                 Toast.makeText(applicationContext, "data inserted", Toast.LENGTH_SHORT).show()
                 dialog.dismiss()
             } else {
                 dialogBinding.idEtitemName.error = "field with name item"
                 dialogBinding.idEtitemQuantity.error = "fill the item quantity"
                 dialogBinding.idEtitemPrice.error = "fill the price item"
                 Toast.makeText(applicationContext, "please enter valid - valid", Toast.LENGTH_SHORT)
                     .show()
             }*/
            dialogBinding.run {
                var isEmptyField = false
                if (itemName.isEmpty()) {
                    isEmptyField = true
                    dialogBinding.idEtitemName.error = "field with name item"
                } else if (itemQuantity.isEmpty()) {
                    isEmptyField = true
                    dialogBinding.idEtitemQuantity.error = "fill the item quantity"
                } else if (itemPrice.isEmpty()) {
                    isEmptyField = true
                    dialogBinding.idEtitemPrice.error = "fill the price item"
                } else {
                    if (!isEmptyField) {
                        val itPrice: Int = itemPrice.toInt()
                        val itQuanty: Int = itemQuantity.toInt()
                        val items = GroceryItems(itemName, itQuanty, itPrice)
                        groceryViewModel.insert(items)
                        groceryAdapter.notifyDataSetChanged()
                        Toast.makeText(applicationContext, "data inserted", Toast.LENGTH_SHORT)
                            .show()
                        dialog.dismiss()
                    }
                }
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