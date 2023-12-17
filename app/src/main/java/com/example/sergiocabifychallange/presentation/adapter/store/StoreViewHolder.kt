package com.example.sergiocabifychallange.presentation.adapter.store

import android.view.View
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.example.sergiocabifychallange.R
import com.example.sergiocabifychallange.databinding.ItemStoreProductBinding
import com.example.sergiocabifychallange.domain.model.Discount
import com.example.sergiocabifychallange.domain.model.Product
import com.example.sergiocabifychallange.utils.formatToEuro

class StoreViewHolder(view: View) : RecyclerView.ViewHolder(view){
    private val binding = ItemStoreProductBinding.bind(view)
    fun onBind(product: Product, discount: Discount? = null, onClickListener: (Product) -> Unit){
        binding.apply {
            Glide.with(binding.productImage.context)
                .load(product.imageUrl)
                .placeholder(R.drawable.ic_launcher_foreground)  // Add a placeholder here
                .into(binding.productImage)
            productTitle.text = product.name
            productPrice.text = formatToEuro(product.price)
            if (discount != null)
                productPromotion.text = productPromotion.context.getString(R.string.promo)
            else
                productPromotion.visibility = View.GONE
            itemView.setOnClickListener  {
                onClickListener(product)
            }
        }
    }
}