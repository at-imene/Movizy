package com.example.movizy.navigation

import androidx.compose.foundation.BorderStroke
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.draw.drawWithCache
import androidx.compose.ui.graphics.BlendMode
import androidx.compose.ui.graphics.Brush
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.tooling.preview.Devices
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.navigation.NavController
import com.example.movizy.R
import com.example.movizy.Utils.Util.BROWSE_MOVIES_SCREEN
import com.example.movizy.ui.theme.readstoreFamily
import com.example.movizy.ui.theme.robotoFamily


@Composable
fun SplashScreen(navController: NavController) {
    SplashLayout({ navController.navigate(BROWSE_MOVIES_SCREEN) })
}

@Composable
fun SplashLayout(onNavigate: () -> Unit) {
    Box(
        modifier = Modifier
            .fillMaxSize()
            .background(color = Color.Blue)
    ) {
        Image(painter =
        painterResource(id = R.drawable.splash_background),
            contentDescription = "splash background",
            contentScale = ContentScale.Crop,
            modifier = Modifier
                .fillMaxSize()
                .drawWithCache {
                    val gradient = Brush.verticalGradient(
                        colors = listOf(Color.Transparent, Color.Black),
                        startY = size.height / 7,
                        endY = size.height
                    )
                    onDrawWithContent {
                        drawContent()
                        drawRect(gradient, blendMode = BlendMode.Multiply)
                    }
                }
        )

        Column(
            modifier = Modifier.align(Alignment.Center).fillMaxWidth(.9f),
            horizontalAlignment = Alignment.CenterHorizontally
        ) {
            Text(
                text = "Movizy",
                style = TextStyle(
                    fontSize = 38.sp,
                    fontWeight = FontWeight.Bold,
                    fontFamily = readstoreFamily
                ),
                color = Color.White.copy(alpha = 0.9f),
                textAlign = TextAlign.Center,
            )
            Text(
                text = "Discover trending movies and explore detailed information about your favorite films.\n",
                style = TextStyle(
                    fontSize = 18.sp,
                    fontWeight = FontWeight.Medium,
                    fontFamily = robotoFamily

                ),
                color = Color.White.copy(alpha = 0.65f),
                textAlign = TextAlign.Center,
                modifier = Modifier.padding(top = 12.dp)
            )
            Button(
                onClick = onNavigate,
                modifier = Modifier
                    .fillMaxWidth()
                    .clip(shape = RoundedCornerShape(8.dp))
                    .background(Color.White)
                    .border(
                        border = BorderStroke(width = 2.dp, color = Color.White),
                        shape = RoundedCornerShape(8.dp)
                    ),
                colors = ButtonDefaults.outlinedButtonColors(contentColor = Color.White)

            ) {
                Text(
                    text = "Get started",
                    style = TextStyle(fontWeight = FontWeight.Bold, fontSize = 16.sp),
                    color = Color.Black,
                    fontFamily = robotoFamily
                )

            }

        }

    }
}

@Preview
@Composable
fun preview() {
    SplashLayout({})
}

@Preview(device = Devices.AUTOMOTIVE_1024p, widthDp = 720, heightDp = 360)
@Composable
fun preview2() {
    SplashLayout({})
}