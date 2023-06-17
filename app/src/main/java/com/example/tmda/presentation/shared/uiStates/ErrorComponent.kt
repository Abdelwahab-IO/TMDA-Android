package com.example.tmda.presentation.shared.uiStates

import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import com.example.tmda.ui.theme.PineGreenMedium

@Composable
fun ErrorComponent(onTryAgain:()->Unit){
    Button(
        onClick = onTryAgain,
        modifier = Modifier.height(160.dp).fillMaxWidth(),
        shape = RoundedCornerShape(10.dp),
        colors = ButtonDefaults.buttonColors(containerColor = PineGreenMedium)
    ) {
        Text(text = "Try Again")
    }
}