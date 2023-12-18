package com.example.sergiocabifychallange.presentation.view.compose

import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.verticalScroll
import androidx.compose.material.FabPosition
import androidx.compose.material.FloatingActionButton
import androidx.compose.material.Icon
import androidx.compose.material.MaterialTheme
import androidx.compose.material.Scaffold
import androidx.compose.material.Text
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.outlined.Add
import androidx.compose.material.icons.outlined.Payment
import androidx.compose.material.icons.outlined.Remove
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.text.style.TextDecoration
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import coil.compose.AsyncImage
import com.example.sergiocabifychallange.domain.model.CartItem
import com.example.sergiocabifychallange.domain.model.Product
import com.example.sergiocabifychallange.utils.formatToEuro


@Composable
fun CartView(
    cartItems: List<CartItem>,
    removeOneItem: (String) -> Unit,
    addOneItem: (String) -> Unit
) {
    Scaffold(

        floatingActionButton = {
                FloatingActionButton(
                    onClick = { /* Handle FAB click */ },
                ) {
                    Icon(Icons.Outlined.Payment, contentDescription = "Add")
                }

        },
        floatingActionButtonPosition = FabPosition.End,
    ) { padding ->

        Column(
            Modifier
                .verticalScroll(rememberScrollState())
        ) {

            Column(
                Modifier
                    .padding(8.dp)
            ) {
                cartItems.forEach { item ->
                    CartItemView(
                        item = item,
                        removeOneItem = { removeOneItem(it)},
                        addOneItem = { addOneItem(it)}
                    )
                }
            }
        }
    }
}

@Composable
fun CartItemView(
    modifier: Modifier = Modifier,
    item: CartItem,
    removeOneItem: (String) -> Unit,
    addOneItem: (String) -> Unit
) {
    Row(
        modifier = modifier
            .fillMaxWidth()
            .padding(8.dp),
        verticalAlignment = Alignment.CenterVertically
    ) {
        AsyncImage(
            modifier = Modifier
                .clip(MaterialTheme.shapes.small)
                .size(40.dp),
            model = item.product.imageUrl,
            contentScale = ContentScale.Crop,
            contentDescription = null
        )
        Spacer(modifier = Modifier.width(16.dp))
        Icon(
            modifier = Modifier
                .size(24.dp)
                .clickable { removeOneItem(item.product.code) },
            imageVector = Icons.Outlined.Remove,
            contentDescription = null
        )
        Box(
            modifier = Modifier
                .size(32.dp)
                .clip(MaterialTheme.shapes.large)
                .background(MaterialTheme.colors.primary),
            contentAlignment = Alignment.Center
        ) {
            Text(
                text = "x${item.quantity}",
                color = MaterialTheme.colors.onPrimary,
                style = MaterialTheme.typography.body2
            )
        }
        Icon(
            modifier = Modifier
                .size(24.dp)
                .clickable { addOneItem(item.product.code) },
            imageVector = Icons.Outlined.Add,
            contentDescription = null
        )
        Spacer(modifier = Modifier.width(16.dp))
        Text(
            text = item.product.name,
            style = MaterialTheme.typography.body1
        )
        Spacer(
            modifier = Modifier
                .weight(1f, true)
        )
        Column(
            horizontalAlignment = Alignment.End,
            verticalArrangement = Arrangement.Center
        ) {

            item.withoutDiscountPrice?.let {
                Text(
                    text = formatToEuro(item.withoutDiscountPrice),
                    textDecoration = TextDecoration.LineThrough,
                    style = MaterialTheme.typography.caption
                )
            }
            Text(
                text = formatToEuro(item.finalPrice),
                style = MaterialTheme.typography.body1
            )
        }

    }
}


@Preview(showBackground = true)
@Composable
fun CartViewPreview() {
    CartView(cartItems = listOf(
        CartItem(
            Product(
                code = "VOUCHER",
                name = "Cabify Voucher",
                price = 5.0,
                imageUrl = "https://images.unsplash.com/photo-1549465220-1a8b9238cd48"
            ),
            quantity = 1,
            discount = null,
            finalPrice = 5.0,
            withoutDiscountPrice = null
        )
    ),
        removeOneItem = {},
        addOneItem = {}
    )
}