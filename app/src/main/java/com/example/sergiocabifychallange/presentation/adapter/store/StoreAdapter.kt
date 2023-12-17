package com.example.sergiocabifychallange.presentation.adapter.store

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.example.sergiocabifychallange.R
import com.example.sergiocabifychallange.domain.model.Discount
import com.example.sergiocabifychallange.domain.model.Product

class StoreAdapter(
    private val mapProductDiscount: Map<Product, Discount?>,
    private val onClickListener:(Product) -> Unit
): RecyclerView.Adapter<StoreViewHolder>() {
    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): StoreViewHolder {
        val layoutInflater = LayoutInflater.from(parent.context)
        return StoreViewHolder(layoutInflater.inflate(R.layout.item_store_product, parent, false))
    }

    override fun onBindViewHolder(holder: StoreViewHolder, position: Int) {
        val product = mapProductDiscount.keys.toList()[position]
        val discount = mapProductDiscount[product]
        holder.onBind(product, discount, onClickListener)
    }

    override fun getItemCount() = mapProductDiscount.keys.toList().size
}