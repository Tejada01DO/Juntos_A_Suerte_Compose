package com.ucne.juntos_a_suerte_compose

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.tooling.preview.Preview
import androidx.room.Room
import com.ucne.juntos_a_suerte_compose.data.local.Juntos_A_SuerteDb
import com.ucne.juntos_a_suerte_compose.data.local.entities.Teams
import com.ucne.juntos_a_suerte_compose.data.repository.PersonRepository
import com.ucne.juntos_a_suerte_compose.ui.home.Home
import com.ucne.juntos_a_suerte_compose.ui.theme.Juntos_A_Suerte_ComposeTheme
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContent {
            Juntos_A_Suerte_ComposeTheme {
                Home()
            }
        }
    }
}