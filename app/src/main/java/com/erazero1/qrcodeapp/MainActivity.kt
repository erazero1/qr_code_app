package com.erazero1.qrcodeapp

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Button
import androidx.compose.material3.HorizontalDivider
import androidx.compose.material3.Icon
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.Stable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.navigation.NavHostController
import androidx.navigation.compose.rememberNavController
import com.erazero1.qrcodeapp.presentation.navigation.GenerateQRCode
import com.erazero1.qrcodeapp.presentation.navigation.NavGraph
import com.erazero1.qrcodeapp.presentation.navigation.ScanQRCode
import com.erazero1.qrcodeapp.presentation.theme.QRCodeAppTheme
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContent {
            QRCodeAppTheme {
                Surface {
                    QRCodeApp()
                }
            }
        }
    }

}

@Composable
fun QRCodeApp() {
    val navController = rememberNavController()
    NavGraph(
        navController = navController,
    )
}

@Composable
fun MainScreen(
    navController: NavHostController
) {

    Scaffold(
        modifier = Modifier
            .fillMaxSize(),
        containerColor = Color.White
    ) { innerPadding ->

        Column(
            modifier = Modifier
                .padding(innerPadding)
                .fillMaxSize(),
        ) {

            QRIcon(
                modifier = Modifier
                    .align(Alignment.CenterHorizontally)
                    .padding(top = 200.dp)
                    .size(200.dp)
            )

            Text(
                text = "Welcome To",
                color = Color.Black,
                fontSize = 32.sp,
                fontWeight = FontWeight.W700,
                modifier = Modifier.align(Alignment.CenterHorizontally)
            )
            Text(
                text = "QR Code Application",
                fontSize = 36.sp,
                color = Color.Black,
                fontWeight = FontWeight.W700,
                modifier = Modifier.align(Alignment.CenterHorizontally)
            )

            Button(
                onClick = {
                    navController.navigate(ScanQRCode)
                },
                shape = RoundedCornerShape(12.dp),
                modifier = Modifier
                    .padding(vertical = 48.dp)
                    .align(Alignment.CenterHorizontally)
                    .height(60.dp)
                    .width(250.dp)
            ) {
                Text(
                    "Scan QR Code",
                    fontSize = 20.sp
                )
            }

            CustomDivider()

            Button(
                onClick = {
                    navController.navigate(GenerateQRCode)
                },
                shape = RoundedCornerShape(12.dp),
                modifier = Modifier
                    .padding(vertical = 32.dp)
                    .align(Alignment.CenterHorizontally)
                    .height(60.dp)
                    .width(250.dp)
            ) {
                Text(
                    "Generate QR Code",
                    fontSize = 20.sp
                )
            }


        }
    }
}

@Stable
@Composable
fun CustomDivider() {
    Row(
        horizontalArrangement = Arrangement.SpaceAround,
        modifier = Modifier.fillMaxWidth(),
        verticalAlignment = Alignment.CenterVertically
    ) {
        HorizontalDivider(
            thickness = 2.dp,
            modifier = Modifier
                .weight(1f)
        )
        Text(
            text = "OR",
            fontWeight = FontWeight.W400,
            fontSize = 14.sp,
            color = Color.Black,
            modifier = Modifier.padding(horizontal = 8.dp)
        )
        HorizontalDivider(
            thickness = 2.dp,
            modifier = Modifier
                .weight(1f)
        )
    }
}

@Composable
fun QRIcon(
    modifier: Modifier = Modifier
) {
    Icon(
        painter = painterResource(id = R.drawable.baseline_qr_code_scanner_24),
        contentDescription = "QR code scanner image",
        modifier = modifier,
        tint = Color.Black
    )
}