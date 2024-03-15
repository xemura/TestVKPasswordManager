package com.xenia.testvk.presentation.main_screen

import android.content.Context
import android.graphics.Bitmap
import android.graphics.Canvas
import android.util.Log
import androidx.compose.foundation.Image
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxHeight
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Delete
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.asImageBitmap
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.unit.dp
import androidx.core.content.ContextCompat
import com.squareup.picasso.Picasso
import com.xenia.testvk.R
import com.xenia.testvk.domain.ItemModel
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.withContext

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
                .fillMaxSize()
                .padding(10.dp)
        ) {
            Box(
                modifier = Modifier.fillMaxHeight()
            ) {
                LoadImageFromUrl(context = LocalContext.current , url = password.imageUrl)
            }

            Column(
                modifier = Modifier.padding(start = 10.dp).fillMaxWidth(),
                verticalArrangement = Arrangement.Center
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

@Composable
fun LoadImageFromUrl(context: Context, url: String) {

    var image by remember { mutableStateOf<Bitmap?>(null) }

    LaunchedEffect(url) {
        image = try {
            val bitmap = withContext(Dispatchers.IO) {
                Picasso.get().load(url).get()
            }
            bitmap
        } catch (e: Exception) {
            Log.d("TAG", "bitmap null")
            val db = ContextCompat.getDrawable(context, R.drawable.ic_empty_content_5)
            val bit = Bitmap.createBitmap(
                db!!.intrinsicWidth, db.intrinsicHeight, Bitmap.Config.ARGB_8888
            )

            val canvas = Canvas(bit)

            db.setBounds(0, 0, canvas.width, canvas.height)

            db.draw(canvas)

            bit
        }
    }

    image?.let {
        Image(
            bitmap = it.asImageBitmap(),
            contentDescription = null,
            modifier = Modifier
                .fillMaxHeight().size(50.dp),
            alignment = Alignment.BottomCenter
        )
    }
}