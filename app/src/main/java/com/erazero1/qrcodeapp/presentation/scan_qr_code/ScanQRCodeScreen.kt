package com.erazero1.qrcodeapp.presentation.scan_qr_code

import android.content.Context
import android.content.Intent
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.automirrored.filled.ArrowBack
import androidx.compose.material3.Button
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.HorizontalDivider
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.material3.TopAppBar
import androidx.compose.material3.TopAppBarDefaults
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.ClipboardManager
import androidx.compose.ui.platform.LocalClipboardManager
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.text.AnnotatedString
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.core.content.ContextCompat.startActivity
import androidx.hilt.navigation.compose.hiltViewModel
import com.erazero1.qrcodeapp.QRIcon
import com.erazero1.qrcodeapp.presentation.theme.PrimaryBlue


@Composable
fun ScanQRCodeScreen(
    onClick: () -> Unit,
) {
    val viewModel = hiltViewModel<ScanQRCodeViewModel>()
    val state = viewModel.uiState.collectAsState()
    val clipboardManager: ClipboardManager = LocalClipboardManager.current
    var copied by remember { mutableStateOf(false) }

    Scaffold(
        topBar = { CustomTopAppBar(title = "Scan QR Code", onClick = onClick) },
        containerColor = Color.White
    )
    { innerPadding ->

        Column(
            modifier = Modifier
                .fillMaxSize()
                .padding(innerPadding)
        ) {
            QRIcon(
                modifier = Modifier
                    .align(Alignment.CenterHorizontally)
                    .padding(top = 100.dp)
                    .size(200.dp)
            )

            Text(
                text = state.value.details,
                color = Color.Black,
                fontSize = 18.sp,
                modifier = Modifier
                    .align(Alignment.CenterHorizontally)
                    .padding(32.dp)
                    .clickable {
                        clipboardManager.setText(AnnotatedString(state.value.details))
                        copied = true
                    }
            )

            HorizontalDivider(
                thickness = 1.dp,
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(top = 48.dp)
            )
            Button(
                onClick = {
                    viewModel.startScanning()
                },
                shape = RoundedCornerShape(12.dp),
                modifier = Modifier
                    .padding(vertical = 32.dp)
                    .align(Alignment.CenterHorizontally)
                    .height(60.dp)
                    .width(250.dp)
            ) {
                Text(
                    text = "Scan QR Code",
                    fontSize = 20.sp
                )
            }

            Row(modifier = Modifier.fillMaxWidth()) {

                val sendIntent = Intent(Intent.ACTION_SEND).apply {
                    putExtra(Intent.EXTRA_TEXT, state.value.details)
                    type = "text/plain"
                }

                Share(
                    sendIntent = sendIntent,
                    modifier = Modifier
                        .padding(vertical = 32.dp, horizontal = 16.dp)
                        .height(60.dp)
                        .width(128.dp)
                        .weight(1f),
                    context = LocalContext.current,
                )

            }


        }
    }

}

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun CustomTopAppBar(
    title: String, onClick: () -> Unit
) {
    TopAppBar(
        title = { Text(text = title, color = Color.White) },
        navigationIcon = {
            IconButton(onClick = onClick) {
                Icon(
                    Icons.AutoMirrored.Filled.ArrowBack,
                    contentDescription = "Back",
                    tint = Color.White
                )
            }
        },
        colors = TopAppBarDefaults.topAppBarColors(containerColor = PrimaryBlue),
    )
}

@Composable
fun CustomButton(
    buttonText: String,
    modifier: Modifier,
    onClick: () -> Unit,
) {
    Button(
        onClick = onClick,
        shape = RoundedCornerShape(12.dp),
        modifier = modifier
    ) {
        Text(
            text = buttonText,
            fontSize = 20.sp
        )
    }
}

@Composable
fun Share(
    sendIntent: Intent,
    modifier: Modifier,
    context: Context,
) {


    val shareIntent = Intent.createChooser(sendIntent, null)

    CustomButton(
        buttonText = "Share",
        modifier = modifier,
    ) {
        startActivity(context, shareIntent, null)
    }

}



