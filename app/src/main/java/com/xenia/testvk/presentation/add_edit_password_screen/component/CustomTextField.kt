package com.xenia.testvk.presentation.add_edit_password_screen.component

import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.material3.Text
import androidx.compose.material3.TextField
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.TextStyle

@Composable
fun CustomTextField(
    modifier: Modifier = Modifier,
    text: String,
    hint: String,
    isNotes: Boolean = false,
    textStyle: TextStyle = TextStyle(),
    onTextChange: (String) -> Unit,
    leadingIcon: @Composable () -> Unit,
) {

    TextField(
        modifier = modifier.fillMaxWidth(0.8f),
        value = text,
        onValueChange = onTextChange,
        textStyle = textStyle,
        leadingIcon = leadingIcon,
        singleLine = !isNotes,
        label = {
            Text(text = hint)
        },
    )
}