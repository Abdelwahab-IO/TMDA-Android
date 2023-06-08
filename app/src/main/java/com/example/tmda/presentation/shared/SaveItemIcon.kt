package com.example.tmda.presentation.shared

import androidx.compose.material3.Icon
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.painterResource
import com.example.tmda.R
import com.example.tmda.ui.theme.PineGreen

@Composable
fun SavedItemIcon(modifier: Modifier,
    isSaved:Boolean){
    if (isSaved)
    Icon(
        painterResource(id = R.drawable.ic_bookmark_filled),
        contentDescription = null,
        tint = PineGreen,
        modifier = modifier
    )
    else
    Icon(
        painterResource(id = R.drawable.ic_bookmark),
        contentDescription = null,
        tint = PineGreen,
        modifier = modifier
    )
}