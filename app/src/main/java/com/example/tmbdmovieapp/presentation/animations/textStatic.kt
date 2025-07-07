package com.example.tmbdmovieapp.presentation.animations

import androidx.compose.animation.animateColor
import androidx.compose.animation.core.LinearOutSlowInEasing
import androidx.compose.animation.core.RepeatMode
import androidx.compose.animation.core.animateFloat
import androidx.compose.animation.core.infiniteRepeatable
import androidx.compose.animation.core.rememberInfiniteTransition
import androidx.compose.animation.core.tween
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.wrapContentWidth
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.graphicsLayer
import androidx.compose.ui.text.font.FontFamily
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp

val colors = listOf(
    Color(0xFFF3C623),
    Color(0xFFF2AAAA),
    Color(0xFFF37121),
    Color(0xFFF2AAAA),
    Color(0xFF8FC0A9),
    Color(0xFF84A9AC),
    Color(0xFFD54062),
    Color(0xFF8FC0A9)
)


@Composable
fun TextToAnimate(textToA: String){
    val colors = listOf(
        Color(0xFFF3C623),
        Color(0xFFF2AAAA),
        Color(0xFFF37121),
        Color(0xFFF2AAAA),
        Color(0xFF8FC0A9),
        Color(0xFF84A9AC),
        Color(0xFFD54062),
        Color(0xFF8FC0A9)
    )
    val infiniteTransition = rememberInfiniteTransition(label = "")

    Row(
        horizontalArrangement = Arrangement.Center,
        verticalAlignment = Alignment.CenterVertically,
        modifier = Modifier
            .wrapContentWidth()

    ) {
        textToA.forEachIndexed { index, char ->

            val delay = index * 25
            val yOffset by infiniteTransition.animateFloat(
                initialValue = 10f,
                targetValue = 10f,
                animationSpec = infiniteRepeatable(
                    animation = tween(
                        durationMillis = 2000,
                        easing = LinearOutSlowInEasing,
                        delayMillis = delay
                    ),
                    repeatMode = RepeatMode.Reverse
                ), label = ""
            )


            val color by infiniteTransition.animateColor(
                initialValue = colors[index % colors.size],
                targetValue = colors[(index + 4) % colors.size],
                animationSpec = infiniteRepeatable(
                    animation = tween(
                        durationMillis = 2000,
                        easing = LinearOutSlowInEasing,
                        delayMillis = delay
                    ),
                    repeatMode = RepeatMode.Reverse
                ), label = ""
            )

            val alpha by infiniteTransition.animateFloat(
                initialValue = 1f,
                targetValue = 0f,
                animationSpec = infiniteRepeatable(
                    animation = tween(
                        durationMillis = 2000,
                        easing = LinearOutSlowInEasing,
                        delayMillis = delay
                    ),
                    repeatMode = RepeatMode.Reverse
                ), label = ""
            )

            Text(
                text = char.toString(),
                fontSize = 30.sp,
                fontWeight = FontWeight.Bold,
                fontFamily = FontFamily.Monospace,
                color = color.copy(alpha = alpha),
                textAlign = TextAlign.Center,
                maxLines = 2,
                modifier = Modifier
                    .weight(1f)
                    .graphicsLayer {
                        translationY = yOffset * 2
                        translationX = 5.dp.toPx()
                    }
            )
            Text(
                text = char.toString(),
                fontSize = 30.sp,
                fontWeight = FontWeight.Bold,
                fontFamily = FontFamily.Monospace,
                color = Color.White,
                textAlign = TextAlign.Center,
                maxLines = 2,
                modifier = Modifier.weight(1f)
            )
        }
    }
}
