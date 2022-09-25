package com.syafei.groceryapp.ui

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.syafei.groceryapp.R
import com.syafei.groceryapp.data.local.GroceryItems

class GroceryAdapter(var list: List<GroceryItems>, val groceryItemClick: GroceryItemClick) :
    RecyclerView.Adapter<GroceryAdapter.GroceryViewHolder>() {


    interface GroceryItemClick {
        fun onItemClicked(groceryItems: GroceryItems)
    }

    inner class GroceryViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
        val tvName = itemView.findViewById<TextView>(R.id.idTvItemName)
        val tvQuantity = itemView.findViewById<TextView>(R.id.idTvQuantity)
        val tvRate = itemView.findViewById<TextView>(R.id.idTvRate)
        val tvAmount = itemView.findViewById<TextView>(R.id.idTVTotalAmnt)

        val ivDelete = itemView.findViewById<ImageView>(R.id.idIVDelete)

    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): GroceryViewHolder {
        val view = LayoutInflater.from(parent.context).inflate(R.layout.grocery_item, parent, false)
        return GroceryViewHolder(view)
    }

    override fun onBindViewHolder(holder: GroceryViewHolder, position: Int) {
        holder.tvName.text = list[position].itemName
        holder.tvQuantity.text = list[position].itemQuantity.toString()
        holder.tvRate.text = "RP. " + list[position].itemPrice.toString()

        val itemTotal: Int = list[position].itemQuantity * list[position].itemPrice

        holder.tvAmount.text = "Rp." + itemTotal.toString()

        holder.ivDelete.setOnClickListener {
            groceryItemClick.onItemClicked(list[position])
        }

    }

    override fun getItemCount(): Int = list.size
}
