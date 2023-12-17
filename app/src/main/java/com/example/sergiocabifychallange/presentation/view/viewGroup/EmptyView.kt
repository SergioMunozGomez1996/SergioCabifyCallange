package com.example.sergiocabifychallange.presentation.view.viewGroup

import android.content.Context
import android.util.AttributeSet
import android.view.LayoutInflater
import androidx.constraintlayout.widget.ConstraintLayout
import com.example.sergiocabifychallange.databinding.ViewEmptyDataBinding

class EmptyView @JvmOverloads constructor(
    context: Context, attrs: AttributeSet? = null
) : ConstraintLayout(context, attrs) {

    private var binding: ViewEmptyDataBinding

    init {
        binding = ViewEmptyDataBinding.inflate(LayoutInflater.from(context), this, true)
    }

    fun setMessage(title: String, subTitle: String? = null){
        binding.emptyViewTitle.text = title
        binding.emptyViewSubtitle.text = subTitle ?: ""
    }
}