package com.example.sergiocabifychallange.presentation.view.activity

import android.os.Bundle
import androidx.activity.viewModels
import androidx.appcompat.app.AppCompatActivity
import androidx.lifecycle.Lifecycle
import androidx.lifecycle.lifecycleScope
import androidx.lifecycle.repeatOnLifecycle
import com.example.sergiocabifychallange.databinding.ActivityMainBinding
import com.example.sergiocabifychallange.presentation.viewModel.MainActivityViewModel
import com.example.sergiocabifychallange.utils.formatToEuro
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.coroutines.launch

@AndroidEntryPoint
class MainActivity : AppCompatActivity() {

    private lateinit var binding: ActivityMainBinding

    private val mainActivityViewModel by viewModels<MainActivityViewModel>()


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)

        initView()
    }

    private fun initView(){
        lifecycleScope.launch {
            repeatOnLifecycle(Lifecycle.State.STARTED) {
                mainActivityViewModel.cartPrice.collect { price ->
                    binding.apply {
                        totalPrice.text = formatToEuro(price)
                    }
                }
            }
        }
        mainActivityViewModel.getCartListPrice()
    }


}