package com.xenia.testvk.presentation.main_screen

import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Delete
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import com.xenia.testvk.domain.ItemModel

@Composable
fun PasswordItem(
    modifier: Modifier = Modifier,
    password: ItemModel,
    onDeleteClick: () -> Unit,
    onItemClick: () -> Unit
) {

    Card(
        modifier = Modifier
            .fillMaxWidth()
            .padding(horizontal = 10.dp, vertical = 5.dp)
            .clickable {
                onItemClick()
            },
        elevation = CardDefaults.elevatedCardElevation(
            defaultElevation = 5.dp
        ),
        shape = RoundedCornerShape(10.dp)
    ) {
        Row(
            modifier = Modifier
                .fillMaxWidth()
                .padding(10.dp)
        ) {
            LoadImageFromUrl(url = password.imageUrl)
            Column(
                verticalArrangement = Arrangement.SpaceBetween
            ) {
                Text(text = password.websiteUrl)
                Text(text = password.login)
                Text(text = password.password)

                IconButton(onClick = onDeleteClick, modifier = Modifier.align(Alignment.End)) {
                    Icon(imageVector = Icons.Filled.Delete, contentDescription = "Delete Password")
                }
            }
        }
    }
}