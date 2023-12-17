package com.example.sergiocabifychallange.utils

import android.content.Context
import android.widget.Toast
import androidx.fragment.app.Fragment
import com.example.sergiocabifychallange.R
import com.example.sergiocabifychallange.data.network.NetworkResponse
import com.example.sergiocabifychallange.domain.model.Discount
import java.text.NumberFormat
import java.util.Locale

fun <T> Fragment.prepareNetworkErrorAction(response: NetworkResponse<T>) {
    when (response) {
        is NetworkResponse.Error -> {
            Toast.makeText(context, context?.let { response.message?.asString(it) }, Toast.LENGTH_SHORT).show()
        }

        else -> {
            Toast.makeText(context, context?.let { response.message?.asString(it) },
                Toast.LENGTH_SHORT).show()
        }
    }
}

fun formatToEuro(value: Double): String {
    val format = NumberFormat.getCurrencyInstance(Locale("es", "ES"))
    return format.format(value)
}

fun Discount.getTitle(context: Context): String {
    return when(this) {
        is Discount.DiscountByAmount -> context.getString(R.string.discount_title_discount_by_amount, this.minNumberOfProducts, (this.discountPercentage*100).toInt())
        is Discount.TakeXGetY -> context.getString(R.string.discount_title_take_x_get_y, this.numberOfPaidProducts, this.numberOfGifts)
    }
}