package com.erazero1.qrcodeapp.presentation.navigation

import androidx.compose.runtime.Composable
import androidx.navigation.NavHostController
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import com.erazero1.qrcodeapp.MainScreen
import com.erazero1.qrcodeapp.presentation.generate_qr_code.GenerateQRCodeScreen
import com.erazero1.qrcodeapp.presentation.scan_qr_code.ScanQRCodeScreen
import kotlinx.serialization.Serializable

interface Screen

@Serializable
object Main: Screen

@Serializable
object ScanQRCode: Screen

@Serializable
object GenerateQRCode: Screen

@Composable
fun NavGraph(
    navController: NavHostController,
) {
    NavHost(
        navController = navController,
        startDestination = Main,
    ) {

        composable<Main> {
            MainScreen(navController = navController)
        }

        composable<ScanQRCode> {
            ScanQRCodeScreen (onClick={
                navigateUp(navController)
            })
        }

        composable<GenerateQRCode> {
            GenerateQRCodeScreen {
                navigateUp(navController)
            }
        }

    }
}

private fun navigateUp(
    navController: NavHostController
) {
    navController.navigateUp()
}