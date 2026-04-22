package com.theamazinghunt

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.material3.Surface
import androidx.compose.runtime.CompositionLocalProvider
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalLayoutDirection
import androidx.compose.ui.res.booleanResource
import androidx.compose.ui.unit.LayoutDirection
import androidx.navigation.compose.rememberNavController
import com.theamazinghunt.presentation.navigation.AppNavGraph
import com.theamazinghunt.presentation.theme.AmazingHuntTheme

class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContent {
            val isRtl = booleanResource(R.bool.app_is_rtl)
            CompositionLocalProvider(
                LocalLayoutDirection provides if (isRtl) LayoutDirection.Rtl else LayoutDirection.Ltr
            ) {
                AmazingHuntTheme {
                    AmazingHuntApp()
                }
            }
        }
    }
}

@Composable
private fun AmazingHuntApp() {
    val navController = rememberNavController()

    Surface(modifier = Modifier.fillMaxSize()) {
        AppNavGraph(navController = navController)
    }
}
