package com.erazero1.qrcodeapp.presentation.generate_qr_code

import android.widget.Toast
import androidx.compose.foundation.Image
import androidx.compose.foundation.gestures.Orientation
import androidx.compose.foundation.gestures.scrollable
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.verticalScroll
import androidx.compose.material3.Button
import androidx.compose.material3.HorizontalDivider
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.material3.TextField
import androidx.compose.material3.TextFieldDefaults
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.hilt.navigation.compose.hiltViewModel
import coil3.compose.rememberAsyncImagePainter
import com.erazero1.qrcodeapp.R
import com.erazero1.qrcodeapp.presentation.scan_qr_code.CustomButton
import com.erazero1.qrcodeapp.presentation.scan_qr_code.CustomTopAppBar

@Composable
fun GenerateQRCodeScreen(
    onClick: () -> Unit
) {
    val viewModel = hiltViewModel<GenerateQRCodeViewModel>()
    val state = viewModel.uiState.collectAsState()
    var inputText by remember { mutableStateOf("") }
    val context = LocalContext.current


    Scaffold(
        topBar = {
            CustomTopAppBar(title = "Generate QR Code", onClick = onClick)
        },
        containerColor = Color.White
    ) { innerPadding ->
        Column(
            modifier = Modifier
                .padding(innerPadding)
                .verticalScroll(rememberScrollState())
        ) {
            Text(
                "Enter your prompt",
                color = Color.Black,
                fontSize = 20.sp,
                modifier = Modifier.padding(start = 32.dp, top = 24.dp, bottom = 16.dp)
            )
            val maxCharactersInTextField = 256
            TextField(
                value = inputText,
                colors = TextFieldDefaults.colors(
                    unfocusedContainerColor = Color.LightGray
                ),
                shape = RoundedCornerShape(12.dp),
                placeholder = {
                    Text(
                        "e.g Create a QR code for www.google.com",
                        fontSize = 16.sp,
                        color = Color.Black
                    )
                },
                modifier = Modifier
                    .align(Alignment.CenterHorizontally)
                    .fillMaxWidth()
                    .padding(horizontal = 32.dp),
                onValueChange = {
                    if(it.length <= maxCharactersInTextField) inputText = it
                },
            )

            Button(
                onClick = {
                    viewModel.generateQRCode(inputText)
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

            HorizontalDivider(
                thickness = 1.dp,
                modifier = Modifier.fillMaxWidth()
            )

            Text(
                "Your QR Code",
                fontSize = 20.sp,
                fontWeight = FontWeight.W700,
                color = Color.Black,
                modifier = Modifier
                    .align(Alignment.CenterHorizontally)
                    .padding(16.dp)
            )

            Image(
                painter = if (state.value.qrBitmap == null) {
                    painterResource(id = R.drawable.baseline_qr_code_scanner_24)
                } else {
                    rememberAsyncImagePainter(state.value.qrBitmap)
                },
                contentDescription = "QR code scanner image",
                modifier = Modifier
                    .align(Alignment.CenterHorizontally)
                    .padding(top = 16.dp)
                    .size(200.dp)
            )
            Row(modifier = Modifier.fillMaxWidth()) {

                CustomButton(
                    buttonText = "Save",
                    modifier = Modifier
                        .padding(vertical = 32.dp, horizontal = 16.dp)
                        .height(60.dp)
                        .width(128.dp)
                        .weight(1f),
                ) {
                    viewModel.updateImage()
                    Toast.makeText(
                        context,
                        "QR Code successfully saved into gallery",
                        Toast.LENGTH_LONG
                    ).show()
                }
            }

        }
    }
}



